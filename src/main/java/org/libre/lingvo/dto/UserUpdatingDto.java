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
    private int translationsInOneLesson;

    @Range(min = 2, max = 1000)
    private int lessonPartsCount;

    @Range(min = 1, max = 120)
    private int minutesBetweenLessonParts;

    private boolean autoPlayDuringLesson=true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTranslationsInOneLesson() {
        return translationsInOneLesson;
    }

    public void setTranslationsInOneLesson(int translationsInOneLesson) {
        this.translationsInOneLesson = translationsInOneLesson;
    }

    public int getLessonPartsCount() {
        return lessonPartsCount;
    }

    public void setLessonPartsCount(int lessonPartsCount) {
        this.lessonPartsCount = lessonPartsCount;
    }

    public int getMinutesBetweenLessonParts() {
        return minutesBetweenLessonParts;
    }

    public void setMinutesBetweenLessonParts(int minutesBetweenLessonParts) {
        this.minutesBetweenLessonParts = minutesBetweenLessonParts;
    }

    public boolean isAutoPlayDuringLesson() {
        return autoPlayDuringLesson;
    }

    public void setAutoPlayDuringLesson(boolean autoPlayDuringLesson) {
        this.autoPlayDuringLesson = autoPlayDuringLesson;
    }
}
