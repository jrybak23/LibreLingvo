package org.libre.lingvo.dto;

import java.util.List;

/**
 * Created by igorek2312 on 30.10.16.
 */
public class TranslationsDto {
    private List<TranslationDto> translations;

    private Long totalRecords;

    public List<TranslationDto> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslationDto> translations) {
        this.translations = translations;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
