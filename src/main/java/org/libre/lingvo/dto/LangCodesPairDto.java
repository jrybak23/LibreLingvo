package org.libre.lingvo.dto;

/**
 * Created by igorek2312 on 05.11.16.
 */
public class LangCodesPairDto {
    private String source;
    private String result;

    public LangCodesPairDto() {
    }

    public LangCodesPairDto(String source, String result) {
        this.source = source;
        this.result = result;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
