package org.libre.lingvo.dto;

/**
 * Created by igorek2312 on 08.09.16.
 */
public class UserDetailsDto {
    private Long id;

    private String email;

    private String name;

    private Integer translationsInOneLesson;

    private Integer lessonPartsCount;

    private Integer minutesBetweenLessonParts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
