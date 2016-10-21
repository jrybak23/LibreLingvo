package org.libre.lingvo.controllers;

import org.libre.lingvo.dto.ErrorInfoDto;
import org.libre.lingvo.dto.ValidationErrorInfoDto;
import org.libre.lingvo.exception.CustomError;
import org.libre.lingvo.exception.CustomErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
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

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomErrorException.class)
    @ResponseBody
    public ErrorInfoDto handleErrorCodeException(CustomErrorException e) {
        CustomError err = e.getError();
        String message = ms.getMessage(err.getMessageKey(), err.getMessageArgs(), LocaleContextHolder.getLocale());
        return new ErrorInfoDto(message, err.getDescription());
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

}
