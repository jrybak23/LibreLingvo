package org.libre.lingvo.dto.exception;

/**
 * Created by igorek2312 on 11.10.16.
 */
public class ErrorInfoDto {
    private int errorCode;

    private String message;

    private String description;

    public ErrorInfoDto(int errorCode, String message, String description) {
        this.errorCode = errorCode;
        this.message = message;
        this.description = description;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
