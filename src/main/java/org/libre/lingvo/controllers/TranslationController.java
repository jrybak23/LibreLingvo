package org.libre.lingvo.controllers;

import org.libre.lingvo.dto.*;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.reference.PartOfSpeech;
import org.libre.lingvo.reference.SortingOptions;
import org.libre.lingvo.reference.TranslationSortFieldOptions;
import org.libre.lingvo.services.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by igorek2312 on 29.10.16.
 */
@RestController
@RequestMapping(value = "/api/v1")
public class TranslationController {
    @Autowired
    private TranslationService translationService;

    @RequestMapping(value = "/users/me/translations", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResourceDto addTranslation(
            @AuthenticationPrincipal User user,
            @RequestBody @Validated InputTranslationDto dto
    ) {
        return translationService.addUserTranslation(user.getId(), dto);
    }

    @RequestMapping(value = "/users/me/translations", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public TranslationsDto viewUserTranslations(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "page-index", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(name = "max-records", required = false, defaultValue = "20") Integer maxRecords,
            @RequestParam(name = "search-substring", required = false, defaultValue = "") String searchSubstring,
            @RequestParam(name = "part-of-speech", required = false) PartOfSpeech partOfSpeech,
            @RequestParam(name = "sort-field", required = false) TranslationSortFieldOptions sortField,
            @RequestParam(name = "sort-order", required = false) SortingOptions sortOrder,
            @RequestParam(name = "learned", required = false) Boolean learned,
            @RequestParam(name = "tag-ids", required = false) List<Long> tagIds,

            @RequestParam(name = "source-text", required = false) String sourceText,
            @RequestParam(name = "source-lang-code", required = false) String sourceLangCode,
            @RequestParam(name = "result-lang-code", required = false) String resultLangCode
    ) {
        if (sourceText != null && sourceLangCode != null && resultLangCode != null)
            return translationService.checkForUserTranslations(
                    user.getId(),
                    sourceText,
                    sourceLangCode,
                    resultLangCode
            );

        return translationService.getUserTranslations(
                user.getId(),
                pageIndex,
                maxRecords,
                searchSubstring,
                partOfSpeech,
                sourceLangCode,
                resultLangCode,
                learned,
                tagIds,
                sortOrder, sortField);
    }

    @RequestMapping(value = "/users/me/translations/{translationId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public TranslationDetailDto viewUserTranslationDetail(
            @AuthenticationPrincipal User user,
            @PathVariable Long translationId
    ) {
        return translationService.getUserTranslationDetailDto(user.getId(), translationId);
    }

    @RequestMapping(value = "/users/me/translations/{translationId}/note", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public TranslationNoteDto viewTranslationNote(
            @AuthenticationPrincipal User user,
            @PathVariable Long translationId
    ) {
        return translationService.getUserTranslationNote(user.getId(), translationId);
    }

    @RequestMapping(value = "/users/me/translations/{translationId}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editTranslation(
            @AuthenticationPrincipal User user,
            @RequestBody @Validated InputTranslationDto dto,
            @PathVariable Long translationId
    ) {
        translationService.updateTranslation(
                user,
                translationId,
                dto
        );
    }

    @RequestMapping(value = "/users/me/translations/{translationId}/note", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editTranslationNote(
            @AuthenticationPrincipal User user,
            @RequestBody @Validated TranslationNoteDto dto,
            @PathVariable Long translationId
    ) {
        translationService.updateTranslationNote(
                user,
                translationId,
                dto
        );
    }

    @RequestMapping(value = "/users/me/translations/{translationId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserTranslation(
            @AuthenticationPrincipal User user,
            @PathVariable Long translationId
    ) {
        translationService.deleteUserTranslation(user.getId(), translationId);
    }

    @RequestMapping(value = "/users/me/translations", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserTranslations(
            @AuthenticationPrincipal User user,
            @RequestParam List<Long> ids
    ) {
        translationService.deleteUserTranslations(user, ids);
    }

    @RequestMapping(value = "/users/me/translations/{translationId}/tags", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<TagDto> getTransaltionTags(
            @AuthenticationPrincipal User user,
            @PathVariable long translationId
    ) {
        return translationService.getTranslationTags(user.getId(), translationId);
    }
}
