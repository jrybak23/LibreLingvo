package org.libre.lingvo.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn
    private Folder folder;

    @Type(type="text")
    private String note;

    private Integer views;

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
}
