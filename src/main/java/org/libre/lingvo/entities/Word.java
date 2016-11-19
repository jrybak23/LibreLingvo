package org.libre.lingvo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by igorek2312 on 26.10.16.
 */
@Entity
public class Word {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 300)
    private String text;

    @Column(length = 2)
    private String langCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (!text.equals(word.text)) return false;
        return langCode.equals(word.langCode);

    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + langCode.hashCode();
        return result;
    }
}
