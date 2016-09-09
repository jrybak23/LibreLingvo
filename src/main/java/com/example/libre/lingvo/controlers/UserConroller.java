package com.example.libre.lingvo.controlers;

import com.example.libre.lingvo.dto.UserDetailsDto;
import com.example.libre.lingvo.entities.User;
import com.example.libre.lingvo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by igorek2312 on 08.09.16.
 */

@RestController
public class UserConroller {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users/me")
    UserDetailsDto getMyInfo(@AuthenticationPrincipal User user){
        return userService.getUserDetails(user);
    }
}
