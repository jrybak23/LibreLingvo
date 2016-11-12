package org.libre.lingvo.controllers;

import org.libre.lingvo.dto.CreatedResourceDto;
import org.libre.lingvo.dto.LessonCreationDto;
import org.libre.lingvo.dto.LessonDto;
import org.libre.lingvo.dto.LessonItemDto;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by igorek2312 on 07.11.16.
 */
@RestController
@RequestMapping(value = "/api/v1")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @RequestMapping(value = "/users/me/lessons", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<LessonItemDto> getUserLessons(@AuthenticationPrincipal User user) {
        return lessonService.getUserLessons(user.getId());
    }

    @RequestMapping(value = "/users/me/lessons/{lessonId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public LessonDto getUserLesson(
            @AuthenticationPrincipal User user,
            @PathVariable Long lessonId
    ) {
        return lessonService.getLesson(user.getId(), lessonId);
    }

    @RequestMapping(value = "/users/me/lessons", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResourceDto createLesson(
            @AuthenticationPrincipal User user,
            @RequestBody LessonCreationDto lessonCreationDto
    ) {
        return lessonService.createLesson(user.getId(), lessonCreationDto.getTranslationIds());
    }

    @RequestMapping(value = "/users/me/lessons/{lessonId}/lesson-part", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void increaseLessonPart(
            @AuthenticationPrincipal User user,
            @PathVariable Long lessonId
    ) {
        lessonService.goNextPartOfLesson(user.getId(), lessonId);
    }

    @RequestMapping(value = "/users/me/lessons/{lessonId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLessons(
            @AuthenticationPrincipal User user,
            @PathVariable Long lessonId
    ) {
        lessonService.deleteLesson(user.getId(), lessonId);
    }
}
