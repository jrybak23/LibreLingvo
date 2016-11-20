package org.libre.lingvo.utils;

import org.libre.lingvo.entities.User;

/**
 * Created by igorek2312 on 20.11.16.
 */
public interface EmailSender {
    void sendEmailResetPasswordMessage(String email, String resetKey, String originUrl);

    void sendEmailActivationMessage(User user, String originUrl);
}
