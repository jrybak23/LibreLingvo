package org.libre.lingvo.dto;

/**
 * Created by igorek2312 on 11.10.16.
 */
public class ErrorInfoDto {
    private String message;

    private String description;

    public ErrorInfoDto(String message, String description) {
        this.message = message;
        this.description = description;
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
