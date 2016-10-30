package org.libre.lingvo.controllers;

import org.libre.lingvo.dto.AddedTranslationDto;
import org.libre.lingvo.dto.TranslationsDto;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.services.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by igorek2312 on 29.10.16.
 */
@RestController
@RequestMapping(value = "/api")
public class TranslationController {
    @Autowired
    private TranslationService translationService;

    @RequestMapping(value = "/users/me/translations", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void addTranslation(@AuthenticationPrincipal User user, @RequestBody @Validated AddedTranslationDto dto) {
        translationService.addUserTranslation(user.getId(), dto);
    }

    @RequestMapping(value = "/users/me/translations", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public TranslationsDto viewUserTranslations(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "20") Integer maxRecords,

            @RequestParam(required = false) String sourceText,
            @RequestParam(required = false) String sourceLangKey,
            @RequestParam(required = false) String resultLangKey
    ) {
        if (sourceText != null && sourceLangKey != null && resultLangKey != null)
            return translationService.checkForUserTranslations(
                    user.getId(),
                    sourceText,
                    sourceLangKey,
                    resultLangKey
            );

        return translationService.getUserTranslations(
                user.getId(),
                pageIndex,
                maxRecords,
                ""
        );
    }
}
