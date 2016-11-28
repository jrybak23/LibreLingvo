package org.libre.lingvo.dto.exception;

import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * Created by igorek2312 on 21.10.16.
 */
public enum CustomError {
    USER_WITH_SUCH_EMAIL_ALREADY_EXISTS(
            1,
            HttpStatus.CONFLICT,
            "User with such email already exists",
            "error.user.with.such.email.exists"
    ),
    NO_USER_WITH_SUCH_ACTIVATION_KEY(
            2,
            HttpStatus.NOT_FOUND,
            "No user with such activation key",
            "error.failed.account.enabling"
    ),
    USER_HAS_ALREADY_SUCH_TRANSLATION(
            3,
            HttpStatus.CONFLICT,
            "User has already such translation",
            "error.user.has.already.such.translation"
    ),
    NO_USER_WITH_SUCH_EMAIL(
            4,
            HttpStatus.NOT_FOUND,
            "No user with such email",
            "error.no.user.with.such.email"
    ),
    NO_USER_WITH_SUCH_RESET_KEY(
            5,
            HttpStatus.NOT_FOUND,
            "No user with such reset key",
            "error.password.reset.link.is.not.working"
    ),
    WRONG_OLD_PASSWORD(
            6,
            HttpStatus.BAD_REQUEST,
            "Wrong old password",
            "error.wrong.old.password"
    ),
    LESSON_WAIT_DATE_IS_FUTURE(7,HttpStatus.BAD_REQUEST,"The opening time of lesson is future"),
    ACCESS_DENIED(401, HttpStatus.UNAUTHORIZED, null, "error.access.denied"),
    FORBIDDEN(403, HttpStatus.FORBIDDEN, null, "error.forbidden"),
    NO_ENTITY_WITH_SUCH_ID(404, HttpStatus.NOT_FOUND, "No entity %s with id %s");

    private final int code;
    private final HttpStatus httpStatus;
    private String description;
    private String messageKey = null;
    private Object[] descriptionArgs = null;
    private Object[] messageArgs = null;

    CustomError(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    CustomError(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
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
        return String.format(description, descriptionArgs);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Optional<String> getMessageKey() {
        return Optional.ofNullable(messageKey);
    }

    public void setDescriptionArgs(Object... descriptionArgs) {
        this.descriptionArgs = descriptionArgs;
    }

    public Object[] getMessageArgs() {
        return messageArgs;
    }

    public void setMessageArgs(Object... messageArgs) {
        this.messageArgs = messageArgs;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
