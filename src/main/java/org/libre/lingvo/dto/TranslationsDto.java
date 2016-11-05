package org.libre.lingvo.dto;

import org.libre.lingvo.model.PartOfSpeech;

import java.util.List;

/**
 * Created by igorek2312 on 30.10.16.
 */
public class TranslationsDto {
    private List<TranslationDto> translations;

    private Long filteredRecords;

    private Long totalRecords;

    private List<LangCodesPairDto> langCodesPairs;

    private List<PartOfSpeech> partsOfSpeech;

    public List<TranslationDto> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslationDto> translations) {
        this.translations = translations;
    }

    public Long getFilteredRecords() {
        return filteredRecords;
    }

    public void setFilteredRecords(Long filteredRecords) {
        this.filteredRecords = filteredRecords;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<LangCodesPairDto> getLangCodesPairs() {
        return langCodesPairs;
    }

    public void setLangCodesPairs(List<LangCodesPairDto> langCodesPairs) {
        this.langCodesPairs = langCodesPairs;
    }

    public List<PartOfSpeech> getPartsOfSpeech() {
        return partsOfSpeech;
    }

    public void setPartsOfSpeech(List<PartOfSpeech> partsOfSpeech) {
        this.partsOfSpeech = partsOfSpeech;
    }
}
