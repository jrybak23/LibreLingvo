package org.libre.lingvo.services;

import org.libre.lingvo.dao.RoleDao;
import org.libre.lingvo.dao.UserDao;
import org.libre.lingvo.dto.FullUserDetailsDto;
import org.libre.lingvo.dto.UserDetailsDto;
import org.libre.lingvo.dto.UserRegistrationDto;
import org.libre.lingvo.entities.Role;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.entities.UserRole;
import org.libre.lingvo.utils.dto.converters.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by igorek2312 on 08.09.16.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    private UserDtoConverter userDtoConverter;

    @Override
    public UserDetailsDto getUserDetails(User user) {
        return userDtoConverter.convertToUserDetailsDto(user);
    }

    @Override
    public List<FullUserDetailsDto> getAllFullUserDetail() {
        List<User> users = userDao.findAll();
        List<FullUserDetailsDto> dtos = users.stream().map(user -> userDtoConverter.convertToFullUserDetailsDto(user))
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    public void registerUser(UserRegistrationDto dto) {
        User user = userDtoConverter.convertFromUserRegistrationDto(dto);
        user.setEnabled(false);
        Role role = roleDao.findByName("ROLE_USER");
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        user.getUserRoles().add(userRole);
        userDao.create(user);

        verificationTokenService.create(user);
    }

}
