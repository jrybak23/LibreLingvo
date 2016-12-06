package org.libre.lingvo.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.libre.lingvo.reference.PartOfSpeech;

import javax.validation.constraints.Size;

/**
 * Created by igorek2312 on 29.10.16.
 */
public class InputTranslationDto {
    @NotBlank(message = "{NotBlank.addedTranslationDto.sourceText}")
    @Size(max = 300, message = "{Size.addedTranslationDto.sourceText}")
    private String sourceText;

    @Size(min = 2, max = 2)
    private String sourceLangCode;

    @NotBlank(message = "{NotBlank.addedTranslationDto.resultText}")
    @Size(max = 300, message = "{Size.addedTranslationDto.resultText}")
    private String resultText;

    @Size(min = 2, max = 2)
    private String resultLangCode;

    private PartOfSpeech partOfSpeech;

    private String note;

    private Boolean learned;

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getSourceLangCode() {
        return sourceLangCode;
    }

    public void setSourceLangCode(String sourceLangCode) {
        this.sourceLangCode = sourceLangCode;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public String getResultLangCode() {
        return resultLangCode;
    }

    public void setResultLangCode(String resultLangCode) {
        this.resultLangCode = resultLangCode;
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

    public Boolean getLearned() {
        return learned;
    }

    public void setLearned(Boolean learned) {
        this.learned = learned;
    }
}
