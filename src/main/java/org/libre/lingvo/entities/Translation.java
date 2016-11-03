package org.libre.lingvo.entities;

import org.hibernate.annotations.Type;
import org.libre.lingvo.model.PartOfSpeech;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by igorek2312 on 26.10.16.
 */
@Entity
public class Translation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Word sourceWord;

    @ManyToOne
    @JoinColumn
    private Word resultWord;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PartOfSpeech partOfSpeech;

    private Boolean learned;

    @ManyToOne
    @JoinColumn
    private Folder folder;

    @Type(type="text")
    private String note;

    private Integer views=1;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModificationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date learningDate;

    public void incrementViews(){
        views++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Word getSourceWord() {
        return sourceWord;
    }

    public void setSourceWord(Word sourceWord) {
        this.sourceWord = sourceWord;
    }

    public Word getResultWord() {
        return resultWord;
    }

    public void setResultWord(Word resultWord) {
        this.resultWord = resultWord;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public Boolean isLearned() {
        return learned;
    }

    public void setLearned(Boolean learned) {
        this.learned = learned;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
