package org.libre.lingvo.service;

import org.junit.Before;
import org.junit.Test;
import org.libre.lingvo.dao.RoleDaoImpl;
import org.libre.lingvo.dao.UserDaoImpl;
import org.libre.lingvo.dto.UserDetailsDto;
import org.libre.lingvo.dto.UserRegistrationDto;
import org.libre.lingvo.dto.UserUpdatingDto;
import org.libre.lingvo.dto.exception.CustomErrorException;
import org.libre.lingvo.entities.Role;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.services.UserService;
import org.libre.lingvo.services.UserServiceImpl;
import org.libre.lingvo.utils.dto.converters.UserDtoConverter;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by igorek2312 on 11.12.16.
 */
public class UserServiceTest extends AbstractServiceTest {
    public static final String TEST_EMAIL = "testemail@example.com";
    public static final long USER_ID = 1L;

    @Mock
    private UserDaoImpl userDao;

    @Mock
    private RoleDaoImpl roleDao;

    @Mock
    private TokenStore tokenStore;

    @Autowired
    private UserDtoConverter userDtoConverter;

    private UserService userService;

    public static final String ACTIVATION_KEY = "foo-activation-key";

    @Before
    public void init() {
        userService = new UserServiceImpl(userDao, roleDao, tokenStore, userDtoConverter);
    }

    @Test
    public void successCreateUser() {
        when(userDao.existsWithEmail(TEST_EMAIL)).thenReturn(false);

        Role role = new Role();
        role.setName("ROLE_USER");
        when(roleDao.findByName("ROLE_USER")).thenReturn(Optional.of(role));

        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setEmail(TEST_EMAIL);
        userService.createUser(dto);
        verify(userDao).create(any(User.class));
    }

    @Test(expected = CustomErrorException.class)
    public void userWithSuchEmailAlreadyExists() {
        when(userDao.existsWithEmail(TEST_EMAIL)).thenReturn(true);

        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setEmail(TEST_EMAIL);
        userService.createUser(dto);
    }

    @Test
    public void getProfileOfExistingUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setName("FooName");
        when(userDao.find(USER_ID)).thenReturn(Optional.of(user));
        UserDetailsDto dto = userService.getUserDetails(USER_ID);
        assertEquals(dto.getId(), user.getId());
        assertEquals(dto.getName(), user.getName());
    }

    @Test(expected = CustomErrorException.class)
    public void getProfileOfNotExistingUser() {
        when(userDao.find(USER_ID)).thenReturn(Optional.empty());
        when(userDao.getDaoType()).thenReturn(User.class);
        userService.getUserDetails(USER_ID);
    }

    @Test
    public void successActivateUser() {
        User user = new User();
        when(userDao.findByActivationKey(ACTIVATION_KEY)).thenReturn(Optional.of(user));
        userService.activateUser(ACTIVATION_KEY);
        verify(userDao).update(user);
        assertTrue(user.isEnabled());
    }

    @Test(expected = CustomErrorException.class)
    public void noUserWithSuchActivationKey() {
        when(userDao.findByActivationKey(ACTIVATION_KEY)).thenReturn(Optional.empty());
        userService.activateUser(ACTIVATION_KEY);
    }

    @Test
    public void cancelActivationOfNotEnabledUser() {
        User user = new User();
        when(userDao.findByActivationKey(ACTIVATION_KEY)).thenReturn(Optional.of(user));
        userService.cancelActivation(ACTIVATION_KEY);
        verify(userDao).delete(user);
    }

    @Test
    public void cancelActivationOfEnabledUser() {
        User user = new User();
        user.setEnabled(true);
        when(userDao.findByActivationKey(ACTIVATION_KEY)).thenReturn(Optional.of(user));
        userService.cancelActivation(ACTIVATION_KEY);
        verify(userDao, never()).delete(user);
    }

    @Test
    public void cancelActivationOfNotExistingUser() {
        when(userDao.findByActivationKey(ACTIVATION_KEY)).thenReturn(Optional.empty());
        userService.cancelActivation(ACTIVATION_KEY);
        verify(userDao, never()).delete(any(User.class));
    }

    @Test
    public void updateProfileOfExistingUser() {
        UserUpdatingDto dto = new UserUpdatingDto();
        dto.setName("FooName");
        dto.setAutoPlayDuringLesson(false);
        dto.setLessonPartsCount(7);
        dto.setMinutesBetweenLessonParts(12);
        dto.setTranslationsInOneLesson(17);

        User user = new User();
        when(userDao.find(USER_ID)).thenReturn(Optional.of(user));
        userService.updateUser(USER_ID, dto);
        verify(userDao).update(user);
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.isAutoPlayDuringLesson(), dto.isAutoPlayDuringLesson());
        assertEquals(user.getLessonPartsCount(), dto.getLessonPartsCount());
        assertEquals(user.getMinutesBetweenLessonParts(), dto.getMinutesBetweenLessonParts());
        assertEquals(user.getTranslationsInOneLesson(), dto.getTranslationsInOneLesson());
    }

    @Test(expected = CustomErrorException.class)
    public void updateProfileOfNotExistingUser() {
        when(userDao.find(USER_ID)).thenReturn(Optional.empty());
        when(userDao.getDaoType()).thenReturn(User.class);
        userService.updateUser(USER_ID, new UserUpdatingDto());
    }

    @Test
    public void deleteExistingUser() {
        User user = new User();
        when(userDao.find(USER_ID)).thenReturn(Optional.of(user));
        when(tokenStore.findTokensByClientIdAndUserName(anyString(), anyString())).thenReturn(Collections.emptyList());
        userService.deleteUser(USER_ID);
        verify(userDao).delete(user);
    }

    @Test(expected = CustomErrorException.class)
    public void deleteNotExistingUser() {
        when(userDao.find(USER_ID)).thenReturn(Optional.empty());
        when(userDao.getDaoType()).thenReturn(User.class);
        userService.deleteUser(USER_ID);
    }
}
