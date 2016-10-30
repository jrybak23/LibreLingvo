package org.libre.lingvo.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.libre.lingvo.model.PartOfSpeech;

import javax.validation.constraints.Size;

/**
 * Created by igorek2312 on 29.10.16.
 */
public class AddedTranslationDto {
    @NotBlank(message = "{NotBlank.addedTranslationDto.sourceText}")
    @Size(max = 300, message = "{Size.addedTranslationDto.sourceText}")
    private String sourceText;

    @Size(min = 2, max = 2)
    private String sourceLangKey;

    @NotBlank(message = "{NotBlank.addedTranslationDto.resultText}")
    @Size(max = 300, message = "{Size.addedTranslationDto.resultText}")
    private String resultText;

    @Size(min = 2, max = 2)
    private String resultLangKey;

    private PartOfSpeech partOfSpeech;

    private String note;

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getSourceLangKey() {
        return sourceLangKey;
    }

    public void setSourceLangKey(String sourceLangKey) {
        this.sourceLangKey = sourceLangKey;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public String getResultLangKey() {
        return resultLangKey;
    }

    public void setResultLangKey(String resultLangKey) {
        this.resultLangKey = resultLangKey;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
