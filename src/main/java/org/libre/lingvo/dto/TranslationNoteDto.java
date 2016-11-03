package org.libre.lingvo.dto;

/**
 * Created by igorek2312 on 03.11.16.
 */
public class TranslationNoteDto {

    private String note;

    public TranslationNoteDto() {
    }

    public TranslationNoteDto(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
