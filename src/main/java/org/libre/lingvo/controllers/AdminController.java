package org.libre.lingvo.controllers;

import org.libre.lingvo.dto.FullUserDetailsDto;
import org.libre.lingvo.dto.UserItemDto;
import org.libre.lingvo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by igorek2312 on 18.11.16.
 */
@RestController
@RequestMapping(value = "/api/v1")
public class AdminController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserItemDto> getUserItems(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            @RequestParam(required = false, defaultValue = "30") int maxRecords
    ) {
        return userService.getUserItems(pageIndex, maxRecords);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updateUser(@PathVariable Long userId, @RequestBody FullUserDetailsDto dto) {
        userService.updateFullUserInfo(userId, dto);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
