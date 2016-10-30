package org.libre.lingvo.dto;

/**
 * Created by igorek2312 on 29.10.16.
 */
public class WordDto {
    private Long id;

    private String text;

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
}
