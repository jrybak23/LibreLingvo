package org.libre.lingvo.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by igorek2312 on 17.12.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public abstract class AbstractControllerTest {
    public static final String API_VERSION = "/api/v1";
    public static final Long USER_ID = 1L;
    public static final String USER_EMAIL = "testemail@example.com";
    public static final String ADMIN_EMAIL = "admin@example.com";
    public static final String PASSWORD = "password";
    public static final String USER_NAME = "TestUser";

    protected MockMvc mvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected FilterChainProxy springSecurityFilterChain;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    protected String getAccessToken(String username, String password) throws Exception {
        String authorization = "Basic "
                + new String(Base64Utils.encode("webapp:123456".getBytes()));

        // @formatter:off
        String content = mvc
                .perform(
                        post("/oauth/token")
                                .header("Authorization", authorization)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("username", username)
                                .param("password", password)
                                .param("grant_type", "password")
                                /*.param("scope", "use")
                                .param("client_id", "webapp")
                                .param("client_secret", "123456")*/)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"))
                .andExpect(jsonPath("$.access_token", is(notNullValue())))
                .andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
                .andExpect(jsonPath("$.scope", is(equalTo("use"))))
                .andReturn().getResponse().getContentAsString();

        // @formatter:on

        return content.substring(17, 53);
    }
}
