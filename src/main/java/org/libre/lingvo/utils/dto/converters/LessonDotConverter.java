package org.libre.lingvo.utils.dto.converters;

import org.libre.lingvo.dto.LessonDto;
import org.libre.lingvo.entities.Lesson;

/**
 * Created by igorek2312 on 12.11.16.
 */
public interface LessonDotConverter {
    LessonDto convertToLessonDto(Lesson lesson);
}
