package org.libre.lingvo.dto;

/**
 * Created by igorek2312 on 11.10.16.
 */
public class ExceptionInfoDto {
    private String message;

    private String description;

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
