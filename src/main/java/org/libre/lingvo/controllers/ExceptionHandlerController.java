package org.libre.lingvo.controllers;

import org.libre.lingvo.dto.exception.CustomError;
import org.libre.lingvo.dto.exception.CustomErrorException;
import org.libre.lingvo.dto.exception.ErrorInfoDto;
import org.libre.lingvo.dto.exception.ValidationErrorInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by igorek2312 on 11.10.16.
 */

@ControllerAdvice
public class ExceptionHandlerController {

    @Autowired
    @Qualifier("backendMessageSource")
    private MessageSource ms;

    @ExceptionHandler(CustomErrorException.class)
    @ResponseBody
    public ResponseEntity<ErrorInfoDto> handleErrorCodeException(CustomErrorException e) {
        CustomError error = e.getError();
        String message = ms.getMessage(error.getMessageKey(), error.getMessageArgs(), LocaleContextHolder.getLocale());

        ErrorInfoDto dto = new ErrorInfoDto(error.getCode(),message, error.getDescription());
        return new ResponseEntity<>(dto, error.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ValidationErrorInfoDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorInfoDto dto = new ValidationErrorInfoDto();
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors()
                .forEach(error -> dto.addFieldError(error.getField(), error.getDefaultMessage()));
        return dto;
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorInfoDto> handleException(AccessDeniedException e) {
        CustomError error=CustomError.ACCESS_DENIED;
        String message = ms.getMessage(error.getMessageKey(), null, LocaleContextHolder.getLocale());
        ErrorInfoDto dto = new ErrorInfoDto(error.getCode(), message, e.getMessage());
        return new ResponseEntity<>(dto,error.getHttpStatus());
    }
}
