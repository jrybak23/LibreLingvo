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
    private Long id;

    @Column(length = 50)
    private String text;

    @Column(length = 2)
    private String langKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (!text.equals(word.text)) return false;
        return langKey.equals(word.langKey);

    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + langKey.hashCode();
        return result;
    }
}
