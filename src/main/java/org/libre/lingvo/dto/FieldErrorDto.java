package org.libre.lingvo.dto;

/**
 * Created by igorek2312 on 20.10.16.
 */
public class FieldErrorDto {
    private String field;

    private String message;

    public FieldErrorDto() {
    }

    public FieldErrorDto(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
