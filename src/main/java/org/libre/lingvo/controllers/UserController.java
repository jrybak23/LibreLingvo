package org.libre.lingvo.controllers;

import org.libre.lingvo.dto.*;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.services.UserService;
import org.libre.lingvo.utils.EmailSender;
import org.libre.lingvo.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

import static org.libre.lingvo.utils.ReadOnlyAccountUtil.isReadOnly;

/**
 * Created by igorek2312 on 08.09.16.
 */

@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleHierarchyImpl roleHierarchy;

    @Autowired
    private EmailSender emailSender;

    @RequestMapping(value = "/users/me", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public UserDetailsDto getMyInfo(@AuthenticationPrincipal User user) {
        return userService.getUserDetails(user.getId());
    }

    @RequestMapping(value = "/users/me", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMyInfo(
            @AuthenticationPrincipal User user,
            @RequestBody @Validated UserUpdatingDto dto
    ) {
        userService.updateUser(user.getId(), dto);
    }

    @RequestMapping(value = "/users/me/password", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(
            @AuthenticationPrincipal User user,
            @RequestBody ChangePasswordDto dto
    ) {
        userService.changePassword(user.getId(), dto);
    }

    @RequestMapping(value = "/users/me/authorities", method = RequestMethod.GET)
    public UserAuthoritiesDto getUserAuthorities() {
        Set<String> authorities = AuthorityUtils.authorityListToSet(
                roleHierarchy.getReachableGrantedAuthorities(
                        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                )
        );
        UserAuthoritiesDto dto = new UserAuthoritiesDto();
        dto.setAuthorities(authorities);
        return dto;
    }

    @RequestMapping(value = "/oauth/revoke-token", method = RequestMethod.DELETE)
    public void logout(HttpServletRequest request) {
        if (!isReadOnly())
            RequestUtil.getAccessTokenValue(request).ifPresent(userService::revokeToken);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResourceDto registerUser(
            HttpServletRequest request,
            @RequestBody @Validated UserRegistrationDto dto
    ) {
        String originUrl = request.getHeader("Origin");

        User user = userService.createUser(dto);
        emailSender.sendEmailActivationMessage(user, originUrl);
        return new CreatedResourceDto(user.getId());
    }

    @RequestMapping(value = "/users/enable", method = RequestMethod.PUT)
    public void enableUser(@RequestParam(name = "activation-key") String activationKey) {
        userService.activateUser(activationKey);
    }

    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public void cancelUserEnabling(@RequestParam(name = "activation-key") String activationKey) {
        userService.cancelActivation(activationKey);
    }

    @RequestMapping(value = "/users/set-reset-key", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setResetKey(@RequestParam String email, HttpServletRequest request) {
        String originUrl = request.getHeader("Origin");
        String resetKey = userService.generateResetKey(email);
        emailSender.sendEmailResetPasswordMessage(email, resetKey, originUrl);
    }

    @RequestMapping(value = "/users/reset-password", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(
            @RequestParam(name = "reset-key") String resetKey,
            @Validated @RequestBody NewPasswordDto dto
    ) {
        userService.resetPassword(resetKey, dto.getPassword());
    }
}