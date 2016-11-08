package org.libre.lingvo.dto;

import java.util.List;

/**
 * Created by igorek2312 on 08.11.16.
 */
public class LessonCreationDto {
    private List<Long> translationIds;

    public List<Long> getTranslationIds() {
        return translationIds;
    }

    public void setTranslationIds(List<Long> translationIds) {
        this.translationIds = translationIds;
    }
}
