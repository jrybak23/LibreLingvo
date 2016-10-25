package org.libre.lingvo.dto.exception;

import org.libre.lingvo.dto.FieldErrorDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igorek2312 on 20.10.16.
 */
public class ValidationErrorInfoDto {
    private List<FieldErrorDto> fieldErrors = new ArrayList<>();

    public void addFieldError(String field, String message) {
        fieldErrors.add(new FieldErrorDto(field, message));
    }

    public List<FieldErrorDto> getFieldErrors() {
        return fieldErrors;
    }
}
