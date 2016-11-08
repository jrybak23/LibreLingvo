package org.libre.lingvo.controllers;

import org.libre.lingvo.dto.CreatedResourceDto;
import org.libre.lingvo.dto.LessonCreationDto;
import org.libre.lingvo.dto.LessonItemDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by igorek2312 on 07.11.16.
 */
@RestController
@RequestMapping(value = "/api/v1")
public class LessonController {
    @RequestMapping(value = "/user/me/lessons", method = RequestMethod.GET)
    public List<LessonItemDto> getUserExams(@AuthenticationPrincipal User user) {
        return null;
    }

    @RequestMapping(value = "/user/me/lessons", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResourceDto createExam(
            @AuthenticationPrincipal User user,
            @RequestBody LessonCreationDto lessonCreationDto
    ) {
        return null;
    }

    @RequestMapping(value = "/user/me/lessons/{lessonId}/exam-part", method = RequestMethod.PUT)
    public void increaseExamPart(
            @AuthenticationPrincipal User user,
            @PathVariable Long lessonId
    ) {

    }

    @RequestMapping(value = "/user/me/lessons/{lessonId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExam(
            @AuthenticationPrincipal User user,
            @PathVariable Long lessonId
    ) {

    }
}
