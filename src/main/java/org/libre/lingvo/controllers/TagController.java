package org.libre.lingvo.controllers;

import org.libre.lingvo.dto.CreatedResourceDto;
import org.libre.lingvo.dto.TagDto;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by igorek2312 on 03.12.16.
 */
@RestController
@RequestMapping(value = "/api/v1")
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/users/me/tags", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<TagDto> getUserTags(@AuthenticationPrincipal User user) {
        return tagService.getUserTags(user.getId());
    }

    @RequestMapping(value = "/users/me/tags", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public CreatedResourceDto createTag(@AuthenticationPrincipal User user, @RequestBody @Validated TagDto dto) {
        return tagService.createTag(user.getId(), dto);
    }

    @RequestMapping(value = "/users/me/tags/{tagId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@AuthenticationPrincipal User user, @PathVariable long tagId) {
        tagService.deleteTag(user.getId(), tagId);
    }

    @RequestMapping(value = "/users/me/tags", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePosition(
            @AuthenticationPrincipal User user,
            @RequestBody List<TagDto> dtos
    ) {
        tagService.updateTagsPosition(user.getId(), dtos);
    }

    @RequestMapping(value = "/users/me/tags/{tagId}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void renameTag(
            @AuthenticationPrincipal User user,
            @PathVariable long tagId,
            @RequestBody @Validated TagDto dto
    ) {
        tagService.renameTag(tagId, dto.getName());
    }

    @RequestMapping(value = "/users/me/tags/{tagId}/translations", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addTranslations(
            @AuthenticationPrincipal User user,
            @PathVariable long tagId,
            @RequestBody List<Long> translationIds
    ) {
        tagService.tagTranslations(tagId, translationIds);
    }

    @RequestMapping(value = "/users/me/tags/{tagId}/translations/{translationId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTranslation(
            @AuthenticationPrincipal User user,
            @PathVariable long tagId,
            @PathVariable long translationId
    ) {
        tagService.removeTranslation(tagId, translationId);
    }
}
