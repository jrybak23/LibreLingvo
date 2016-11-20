package org.libre.lingvo.utils;

import freemarker.template.Configuration;
import org.libre.lingvo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by igorek2312 on 20.11.16.
 */
@Component
public class EmailSenderImpl implements EmailSender {
    @Value("${email.username}")
    private String EMAIL_USERNAME;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freeMarkerConfigurer;

    @Override
    public void sendEmailResetPasswordMessage(String email, String resetKey, String originUrl) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(email);
            message.setFrom(EMAIL_USERNAME);
            Map model = new HashMap();

            model.put("resetPasswordUrl", originUrl + "/#/reset-password?reset-key=" + resetKey);

            String text = FreeMarkerTemplateUtils.processTemplateIntoString(
                    freeMarkerConfigurer.getTemplate("email_password_reset_en.ftl", "UTF-8"),
                    model
            );

            message.setText(text, true);
        };

        mailSender.send(preparator);
    }

    @Override
    public void sendEmailActivationMessage(User user, String originUrl) {
        String activationKey = user.getActivationKey();

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(user.getEmail());
            message.setFrom(EMAIL_USERNAME);
            Map model = new HashMap();

            model.put("name", user.getName());
            model.put("originUrl", originUrl);
            model.put("enableUserUrl", originUrl + "/#/activate-user?activation-key=" + activationKey);
            model.put("cancelUserEnablingUrl", originUrl + "/#/cancel-activation?activation-key=" + activationKey);

            String text = FreeMarkerTemplateUtils.processTemplateIntoString(
                    freeMarkerConfigurer.getTemplate("email_activation_en.ftl", "UTF-8"),
                    model
            );

            message.setText(text, true);
        };

        mailSender.send(preparator);
    }

}
