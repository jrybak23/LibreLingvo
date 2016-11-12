package org.libre.lingvo.dto;

import java.util.List;

/**
 * Created by igorek2312 on 09.11.16.
 */
public class LessonDto {
    private Long id;

    private List<TranslationLearningDto> translations;

    private Integer completedPartsOfLesson;

    private Integer maxPartsOfLesson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TranslationLearningDto> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslationLearningDto> translations) {
        this.translations = translations;
    }

    public Integer getCompletedPartsOfLesson() {
        return completedPartsOfLesson;
    }

    public void setCompletedPartsOfLesson(Integer completedPartsOfLesson) {
        this.completedPartsOfLesson = completedPartsOfLesson;
    }

    public Integer getMaxPartsOfLesson() {
        return maxPartsOfLesson;
    }

    public void setMaxPartsOfLesson(Integer maxPartsOfLesson) {
        this.maxPartsOfLesson = maxPartsOfLesson;
    }
}
