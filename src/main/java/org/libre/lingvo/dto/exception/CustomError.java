package org.libre.lingvo.dto.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by igorek2312 on 21.10.16.
 */
public enum CustomError {
    USER_WITH_SUCH_EMAIL_ALREADY_EXISTS(0,"User with such email already exists", "error.user.with.such.email.exists"),
    NO_VERIFICATION_TOKEN_WITH_SUCH_UUID(1,"No verification token with such uuid", "error.failed.account.enabling"),
    ACCESS_DENIED(401,HttpStatus.UNAUTHORIZED,null,"error.access.denied");

    private final int code;
    private HttpStatus httpStatus=HttpStatus.BAD_GATEWAY;
    private String description;
    private String messageKey = null;
    private Object[] messageArgs = null;

    CustomError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    CustomError(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    CustomError(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }

    CustomError(int code, String description, String messageKey) {
        this.code = code;
        this.description = description;
        this.messageKey = messageKey;
    }

    CustomError(int code, HttpStatus httpStatus, String description, String messageKey) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
        this.messageKey = messageKey;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public Object[] getMessageArgs() {
        return messageArgs;
    }

    public void setMessageArgs(Object[] messageArgs) {
        this.messageArgs = messageArgs;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
