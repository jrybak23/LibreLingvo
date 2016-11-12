package org.libre.lingvo.services;

import org.libre.lingvo.dto.CreatedResourceDto;
import org.libre.lingvo.dto.LessonDto;
import org.libre.lingvo.dto.LessonItemDto;

import java.util.List;

/**
 * Created by igorek2312 on 09.11.16.
 */
public interface LessonService {
    CreatedResourceDto createLesson(Long userId, List<Long> translationsIds);

    List<LessonItemDto> getUserLessons(Long userId);

    LessonDto getLesson(Long userId, Long lessonId);

    void goNextPartOfLesson(Long userId, Long lessonId);

    void deleteLesson(Long userId, Long lessonId);
}
