package org.libre.lingvo.exception;

/**
 * Created by igorek2312 on 21.10.16.
 */
public enum CustomError {
    USER_WITH_SUCH_EMAIL_ALREADY_EXISTS("User with such email already exists", "error.user.with.such.email.exists"),
    NO_VERIFICATION_TOKEN_WITH_SUCH_UUID("No verification token with such uuid", "error.failed.account.enabling");

    private String description;
    private final String messageKey;
    private Object[] messageArgs = null;

    CustomError(String description, String messageKey) {
        this.description = description;
        this.messageKey = messageKey;
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

}
