package org.libre.lingvo.services;

import org.libre.lingvo.config.aspects.annotaions.NotForReadOnly;
import org.libre.lingvo.dao.RoleDao;
import org.libre.lingvo.dao.UserDao;
import org.libre.lingvo.dto.*;
import org.libre.lingvo.dto.exception.CustomError;
import org.libre.lingvo.dto.exception.CustomErrorException;
import org.libre.lingvo.entities.Role;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.entities.UserRole;
import org.libre.lingvo.utils.dto.converters.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.libre.lingvo.utils.EntityUtil.findOrThrowNotFound;

/**
 * Created by igorek2312 on 08.09.16.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    private RoleDao roleDao;

    private TokenStore tokenStore;

    @Autowired
    private UserDtoConverter userDtoConverter;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, TokenStore tokenStore, UserDtoConverter userDtoConverter) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.tokenStore = tokenStore;
        this.userDtoConverter=userDtoConverter;
    }

    private void deleteAccessTokens(String username) {
        tokenStore.findTokensByClientIdAndUserName("webapp", username)
                .forEach(tokenStore::removeAccessToken);
    }

    @Override
    public UserDetailsDto getUserDetails(long userId) {
        User user = findOrThrowNotFound(userDao, userId);
        return userDtoConverter.convertToUserDetailsDto(user);
    }

    @Override
    public List<UserItemDto> getUserItems(int pageIndex, int maxRecords) {
        return userDao.findUsers(pageIndex, maxRecords)
                .stream()
                .map(user -> userDtoConverter.convertToUserItemDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public User createUser(UserRegistrationDto dto) {
        if (userDao.existsWithEmail(dto.getEmail()))
            throw new CustomErrorException(CustomError.USER_WITH_SUCH_EMAIL_ALREADY_EXISTS);

        Role role = roleDao.findByName("ROLE_USER").get();
        User user = userDtoConverter.convertFromUserRegistrationDto(dto);
        user.setEnabled(false);

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        user.getUserRoles().add(userRole);
        user.setActivationKey(UUID.randomUUID().toString());
        user.setRegistrationDate(new Date());

        userDao.create(user);
        return user;
    }


    @Override
    public void activateUser(String activationKey) {
        User user = userDao.findByActivationKey(activationKey).orElseThrow(
                () -> new CustomErrorException(CustomError.NO_USER_WITH_SUCH_ACTIVATION_KEY)
        );

        user.setEnabled(true);
        userDao.update(user);
    }

    @Override
    public void cancelActivation(String activationKey) {
        userDao.findByActivationKey(activationKey).ifPresent(user -> {
            if (!user.isEnabled())
                userDao.delete(user);
        });
    }

    @NotForReadOnly
    @Override
    public void updateUser(long userId, UserUpdatingDto dto) {
        User user = findOrThrowNotFound(userDao, userId);

        user.setName(dto.getName());
        user.setTranslationsInOneLesson(dto.getTranslationsInOneLesson());
        user.setLessonPartsCount(dto.getLessonPartsCount());
        user.setMinutesBetweenLessonParts(dto.getMinutesBetweenLessonParts());
        user.setAutoPlayDuringLesson(dto.isAutoPlayDuringLesson());
        userDao.update(user);
    }

    @Override
    public void updateFullUserInfo(long userId, FullUserDetailsDto dto) {
        User user = findOrThrowNotFound(userDao, userId);
        user.setEnabled(dto.isEnabled());
        user.setNonLocked(dto.isNonLocked());
        userDao.update(user);
        if (!dto.isNonLocked())
            deleteAccessTokens(user.getEmail());
    }

    @NotForReadOnly
    @Override
    public void changePassword(long userId, ChangePasswordDto dto) {
        User user = findOrThrowNotFound(userDao, userId);
        if (!user.getPassword().equals(dto.getOldPassword()))
            throw new CustomErrorException(CustomError.WRONG_OLD_PASSWORD);

        user.setPassword(dto.getPassword());
        userDao.update(user);
    }

    @Override
    public void deleteUser(long userId) {
        User user = findOrThrowNotFound(userDao, userId);
        userDao.delete(user);
        deleteAccessTokens(user.getEmail());
    }

    @Override
    public void deleteExpiredNotActivatedUsers() {
        userDao.deleteExpiredNotActivatedUsers();
    }

    @Override
    public void revokeToken(String tokenValue) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        tokenStore.removeAccessToken(accessToken);
    }

    public String generateResetKey(String email) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new CustomErrorException(CustomError.NO_USER_WITH_SUCH_EMAIL));
        user.setResetKey(UUID.randomUUID().toString());
        userDao.update(user);
        return user.getResetKey();
    }

    @Override
    public void resetPassword(String resetKey, String newPassword) {
        User user = userDao.findByResetKey(resetKey)
                .orElseThrow(() -> new CustomErrorException(CustomError.NO_USER_WITH_SUCH_RESET_KEY));

        user.setPassword(newPassword);
        user.setResetKey(null);
        userDao.update(user);
    }
}