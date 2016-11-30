package org.libre.lingvo.utils;

import org.libre.lingvo.dto.exception.CustomError;
import org.libre.lingvo.dto.exception.CustomErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by igorek2312 on 19.11.16.
 */
public class RequestUtil {

    public static Optional<String> getAccessTokenValue(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) Optional.empty();
        String tokenValue = authHeader.replace("Bearer", "").trim();
        return Optional.of(tokenValue);
    }

}
