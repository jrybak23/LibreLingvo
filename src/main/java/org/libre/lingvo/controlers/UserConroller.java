package org.libre.lingvo.controlers;

import org.libre.lingvo.dto.FullUserDetailsDto;
import org.libre.lingvo.dto.UserDetailsDto;
import org.libre.lingvo.dto.UserRegistrationDto;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by igorek2312 on 08.09.16.
 */

@RestController
public class UserConroller {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users/me", method = RequestMethod.GET)
    public UserDetailsDto getMyInfo(@AuthenticationPrincipal User user){
        return userService.getUserDetails(user);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<FullUserDetailsDto> getAllFullUserDetails(){
        return userService.getAllFullUserDetail();
    }

    @RequestMapping(value = "/users",method = RequestMethod.POST)
    public void registerUser(@RequestBody UserRegistrationDto dto){
        userService.registerUser(dto);
    }

}
