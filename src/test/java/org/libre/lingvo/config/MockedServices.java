package org.libre.lingvo.config;

import org.libre.lingvo.services.TranslationService;
import org.libre.lingvo.services.TranslationServiceImpl;
import org.libre.lingvo.services.UserService;
import org.libre.lingvo.services.UserServiceImpl;
import org.libre.lingvo.utils.EmailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

/**
 * Created by igorek2312 on 17.12.16.
 */
@Configuration
public class MockedServices {
    @Bean
    @Primary
    public UserService userServiceMock(){
        return mock(UserServiceImpl.class);
    }

    @Bean
    @Primary
    public TranslationService translationServiceMock(){
        return mock(TranslationServiceImpl.class);
    }

    @Bean
    @Primary
    public EmailSender emailSenderMock(){
        return mock(EmailSender.class);
    }
}
