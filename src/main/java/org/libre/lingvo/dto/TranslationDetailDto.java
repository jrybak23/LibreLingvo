package org.libre.lingvo.dto;

import org.libre.lingvo.model.PartOfSpeech;

/**
 * Created by igorek2312 on 31.10.16.
 */
public class TranslationDetailDto {
    private Long id;

    private WordDto sourceWord;

    private WordDto resultWord;

    private PartOfSpeech partOfSpeech;

    private String note;

    private Boolean learned;

    private Integer views;

    private String lastModificationDate;

    private String learningDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WordDto getSourceWord() {
        return sourceWord;
    }

    public void setSourceWord(WordDto sourceWord) {
        this.sourceWord = sourceWord;
    }

    public WordDto getResultWord() {
        return resultWord;
    }

    public void setResultWord(WordDto resultWord) {
        this.resultWord = resultWord;
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

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(String lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getLearningDate() {
        return learningDate;
    }

    public void setLearningDate(String learningDate) {
        this.learningDate = learningDate;
    }
}
