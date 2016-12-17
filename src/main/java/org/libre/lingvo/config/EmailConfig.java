package org.libre.lingvo.config;

import freemarker.template.TemplateException;
import org.libre.lingvo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * Created by igorek2312 on 07.10.16.
 */
@Configuration
@ComponentScan
public class EmailConfig {
    @Value("${email.host}")
    private String EMAIL_HOST;

    @Value("${email.username}")
    private String EMAIL_USERNAME;

    @Value("${email.password}")
    private String EMAIL_PASSWORD;

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setTemplateLoaderPath("classpath:templates/");
        factory.setDefaultEncoding("UTF-8");
        FreeMarkerConfigurer result = new FreeMarkerConfigurer();
        result.setConfiguration(factory.createConfiguration());
        return result;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(EMAIL_HOST);
        javaMailSender.setPort(587);
        javaMailSender.setUsername(EMAIL_USERNAME);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        javaMailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
        return javaMailSender;
    }

    @Profile({"init", "dev", "cloud"})
    @Configuration
    public static class SchedulerConfig {
        @Autowired
        private UserService userService;

        @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
        public void deleteUsersWithExpiredTokens() {
            userService.deleteExpiredNotActivatedUsers();
        }
    }

}
