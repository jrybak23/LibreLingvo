package org.libre.lingvo.controllers;

import org.libre.lingvo.dto.ExceptionInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by igorek2312 on 11.10.16.
 */

@ControllerAdvice
public class ExceptionHandlerController {

    @Autowired
    private MessageSource ms;


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ExceptionInfoDto handleException(HttpServletRequest req, IllegalArgumentException e){
        ExceptionInfoDto dto = new ExceptionInfoDto();
        dto.setDescription(e.getMessage());
        Locale locale = req.getLocale();

        switch (e.getMessage()){
            case "No verification token with such uuid":
                dto.setMessage(ms.getMessage("error.failed.account.enabling",null,e.getMessage(),locale));
                break;
            case "User with such email already exists":
                dto.setMessage(ms.getMessage("error.user.with.such.email.exists",null,e.getMessage(),locale));
                break;
            default:
                dto.setMessage(e.getMessage());
        }

        return dto;
    }

}
