package org.libre.lingvo.utils;

import org.libre.lingvo.dto.exception.CustomError;
import org.libre.lingvo.dto.exception.CustomErrorException;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by igorek2312 on 29.11.16.
 */
public class ReadOnlyAccountUtil {
    private final static String readOnlyUserEmail = "readonly@example.com";

    private static AtomicBoolean enabled = new AtomicBoolean(true);

    public static void throwIfReadOnly(String email) {
        if (isReadOnly(email))
            throw new CustomErrorException(CustomError.FORBIDDEN_FOR_READONLY_ACCOUNT);
    }

    public static boolean isReadOnly(String email) {
        return readOnlyUserEmail.equals(email) && enabled.get();
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
