package org.libre.lingvo.services;

import freemarker.template.Configuration;
import org.libre.lingvo.dao.UserDao;
import org.libre.lingvo.dao.VerificationTokenDao;
import org.libre.lingvo.dto.EmailVerificationDto;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.entities.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Created by igorek2312 on 27.09.16.
 */
@Service
@Transactional
public class VerificationTokenServiceImpl implements VerificationTokenService {
    @Autowired
    @Qualifier("originUrl")
    private URL originUrl;

    @Autowired
    @Qualifier("originEnableUserUrl")
    private URL originEnableUserUrl;

    @Autowired
    @Qualifier("originCancelUserEnablingUrl")
    private URL originCancelUserEnablingUrl;

    @Value("${email.username}")
    private String EMAIL_USERNAME;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freeMarkerConfigurer;

    @Autowired
    private VerificationTokenDao verificationTokenDao;

    @Autowired
    private UserDao userDao;


    @Autowired
    private ConfigurableApplicationContext applicationContext;


    public void sendEmailMessage(String email, EmailVerificationDto dto) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(email);
            message.setFrom(EMAIL_USERNAME);
            Map model = new HashMap();
            model.put("dto", dto);

            String text = FreeMarkerTemplateUtils.processTemplateIntoString(
                    freeMarkerConfigurer.getTemplate("email-verfication.ftl", "UTF-8"),
                    model
            );

            message.setText(text, true);
        };
        mailSender.send(preparator);
    }

    @Override
    public void enableUser(String tokenUuid) {
        Optional<VerificationToken> verificationToken = verificationTokenDao.find(tokenUuid);
        VerificationToken token = verificationToken.orElseThrow(
                () -> new IllegalArgumentException("No verification token with such uuid")
        );

        token.getUser().setEnabled(true);
    }

    @Override
    public void cancelUserEnabling(String tokenUuid) {
        verificationTokenDao.find(tokenUuid).ifPresent(token -> {
            userDao.delete(token.getUser());
        });
    }

    @Override
    public void create(User user) {
        VerificationToken token = new VerificationToken(user);
        verificationTokenDao.create(token);
        EmailVerificationDto dto = new EmailVerificationDto();
        String uuid = token.getId();
        dto.setName(user.getName());
        dto.setOriginUrl(originUrl.toString());
        dto.setEnableUserUrl(originEnableUserUrl.toString() + uuid);
        dto.setCancelUserEnablingUrl(originCancelUserEnablingUrl.toString() + uuid);
        sendEmailMessage(user.getEmail(), dto);
    }

}
