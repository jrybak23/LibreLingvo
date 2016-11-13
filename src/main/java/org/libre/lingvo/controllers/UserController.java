package org.libre.lingvo.controllers;

import org.libre.lingvo.dto.*;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.services.UserService;
import org.libre.lingvo.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * Created by igorek2312 on 08.09.16.
 */

@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private RoleHierarchyImpl roleHierarchy;

    @Autowired
    private TokenStore tokenStore;

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
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<FullUserDetailsDto> getAllFullUserDetails() {
        return userService.getAllFullUserDetail();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResourceDto registerUser(
            HttpServletRequest request,
            @RequestBody @Validated UserRegistrationDto dto
    ) {
        String originUrl = request.getHeader("Origin");

        User user = userService.registerUser(dto);
        verificationTokenService.create(user, originUrl);
        return new CreatedResourceDto(user.getId());
    }
}
