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

    @Column(nullable = false)
    private boolean learned=false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Folder folder;

    @Type(type="text")
    private String note;

    @Column(nullable = false)
    private int views=1;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date lastModificationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date learnedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Lesson lesson;

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

    public boolean isLearned() {
        return learned;
    }

    public void setLearned(boolean learned) {
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

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Date getLearnedDate() {
        return learnedDate;
    }

    public void setLearnedDate(Date learnedDate) {
        this.learnedDate = learnedDate;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
