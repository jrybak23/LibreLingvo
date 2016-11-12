package org.libre.lingvo.utils.dto.converters;

import org.libre.lingvo.dto.LessonDto;
import org.libre.lingvo.entities.Lesson;
import org.springframework.stereotype.Component;

/**
 * Created by igorek2312 on 12.11.16.
 */
@Component
public class LessonDotConverterImpl extends AbstractDtoConverter implements LessonDotConverter {
    @Override
    public LessonDto convertToLessonDto(Lesson lesson) {

        return modelMapper.map(lesson, LessonDto.class);
    }
}
