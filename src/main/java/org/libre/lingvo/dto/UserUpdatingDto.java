package org.libre.lingvo.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;

/**
 * Created by igorek2312 on 08.11.16.
 */
public class UserUpdatingDto {
    @Size(max = 15, message = "{Size.userRegistrationDto.name}")
    private String name;

    @Range(min = 5, max = 40)
    private Integer translationsInOneLesson;

    @Range(min = 2, max = 1000)
    private Integer lessonPartsCount;

    @Range(min = 1, max = 120)
    private Integer minutesBetweenLessonParts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTranslationsInOneLesson() {
        return translationsInOneLesson;
    }

    public void setTranslationsInOneLesson(Integer translationsInOneLesson) {
        this.translationsInOneLesson = translationsInOneLesson;
    }

    public Integer getLessonPartsCount() {
        return lessonPartsCount;
    }

    public void setLessonPartsCount(Integer lessonPartsCount) {
        this.lessonPartsCount = lessonPartsCount;
    }

    public Integer getMinutesBetweenLessonParts() {
        return minutesBetweenLessonParts;
    }

    public void setMinutesBetweenLessonParts(Integer minutesBetweenLessonParts) {
        this.minutesBetweenLessonParts = minutesBetweenLessonParts;
    }
}
