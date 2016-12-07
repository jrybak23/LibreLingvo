package org.libre.lingvo.utils;

import org.libre.lingvo.dto.exception.CustomError;
import org.libre.lingvo.dto.exception.CustomErrorException;
import org.libre.lingvo.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by igorek2312 on 29.11.16.
 */
public class ReadOnlyAccountUtil {
    private final static String readOnlyUserEmail = "readonly@example.com";

    private static AtomicBoolean enabled = new AtomicBoolean(true);

    public static void throwIfReadOnly() {
        Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(User.class::cast)
                .ifPresent(user -> {
                    if (isReadOnly(user.getEmail()))
                        throw new CustomErrorException(CustomError.FORBIDDEN_FOR_READONLY_ACCOUNT);
                });
    }

    private static boolean isReadOnly(String email) {
        return readOnlyUserEmail.equals(email) && enabled.get();
    }

    public static boolean isReadOnly() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(User.class::cast)
                .map(User::getEmail)
                .map(ReadOnlyAccountUtil::isReadOnly)
                .orElse(false);
    }

    public static String getReadOnlyUserEmail() {
        return readOnlyUserEmail;
    }

    public static void setEnabled(boolean enb) {
        enabled.set(enb);
    }

    public static boolean isEnabled() {
        return enabled.get();
    }
}
