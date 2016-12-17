package org.libre.lingvo.controller;

import org.junit.Test;
import org.libre.lingvo.dto.UserDetailsDto;
import org.libre.lingvo.dto.UserRegistrationDto;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.services.UserService;
import org.libre.lingvo.utils.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by igorek2312 on 15.12.16.
 */

public class UserControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userServiceMock;

    @Autowired
    private EmailSender emailSenderMock;

    @Test
    public void successRegisterUser() throws Exception {
        User user = new User();
        user.setId(USER_ID);
        user.setEmail(USER_EMAIL);

        when(userServiceMock.createUser(any(UserRegistrationDto.class))).thenReturn(user);

        mvc.perform(
                post(API_VERSION + "/users")
                        .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                        .content(
                                "{\n" +
                                        "  \"email\" : \"" + USER_EMAIL + "\",\n" +
                                        "  \"password\" : \"" + PASSWORD + "\",\n" +
                                        "  \"name\" : \"" + USER_NAME + "\"\n" +
                                        "}")
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(equalTo(USER_ID.intValue()))));
    }

    @Test
    public void registerUserWithNotValidFields() throws Exception {
        mvc.perform(
                post(API_VERSION + "/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\n" +
                                        "  \"email\" : \"" + "testemailexample.com" + "\",\n" +
                                        "  \"password\" : \"" + "йцукен" + "\",\n" +
                                        "  \"name\" : \"" + "0123456789123456" + "\"\n" +
                                        "}")
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors", hasSize(3)))
                .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("email", "password", "name")));
    }

    @Test
    public void getAnonymousAuthorities() throws Exception {
        mvc.perform(get(API_VERSION + "/users/me/authorities"))
                .andExpect(jsonPath("$[0]", is("ROLE_ANONYMOUS")));
    }

    @Test
    public void getUserAuthorities() throws Exception {
        String accessToken = getAccessToken(USER_EMAIL, PASSWORD);

        mvc.perform(get(API_VERSION + "/users/me/authorities")
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(jsonPath("$[0]", is("ROLE_USER")));
    }

    @Test
    public void getAdminAuthorities() throws Exception {
        String accessToken = getAccessToken(ADMIN_EMAIL, PASSWORD);

        mvc.perform(get(API_VERSION + "/users/me/authorities")
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(jsonPath("$[*]", containsInAnyOrder("ROLE_USER", "ROLE_ADMIN")));
    }

    @Test
    public void getCurrentUserInfo() throws Exception {
        String accessToken = getAccessToken(USER_EMAIL, PASSWORD);

        UserDetailsDto dto = new UserDetailsDto();
        dto.setEmail(USER_EMAIL);
        dto.setName(USER_NAME);

        when(userServiceMock.getUserDetails(USER_ID)).thenReturn(dto);

        mvc.perform(get(API_VERSION + "/users/me")
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(jsonPath("$.email", is(USER_EMAIL)))
                .andExpect(jsonPath("$.name", is(USER_NAME)));
    }
}
