package org.libre.lingvo.dto;

/**
 * Created by igorek2312 on 29.10.16.
 */
public class InputWordDto {
    private String text;

    private String langCode;

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
}
