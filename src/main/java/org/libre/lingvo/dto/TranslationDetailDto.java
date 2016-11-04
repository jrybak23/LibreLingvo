package org.libre.lingvo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.libre.lingvo.model.PartOfSpeech;

import java.util.Date;

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

    @JsonFormat(pattern = "MM.dd.yyyy HH:mm:ss")
    private Date lastModificationDate;

    @JsonFormat(pattern = "MM.dd.yyyy HH:mm:ss")
    private Date learningDate;

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

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Date getLearningDate() {
        return learningDate;
    }

    public void setLearningDate(Date learningDate) {
        this.learningDate = learningDate;
    }
}
