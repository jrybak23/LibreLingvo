package org.libre.lingvo.entities;

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
    private Word sourceWord;

    @ManyToOne
    @JoinColumn
    private Word resultWord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Translation that = (Translation) o;

        if (!sourceWord.equals(that.sourceWord)) return false;
        return resultWord.equals(that.resultWord);

    }

    @Override
    public int hashCode() {
        int result = sourceWord.hashCode();
        result = 31 * result + resultWord.hashCode();
        return result;
    }
}
