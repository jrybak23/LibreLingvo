package org.libre.lingvo.dto;

import java.util.Date;

/**
 * Created by igorek2312 on 07.11.16.
 */
public class LessonItemDto {
    private Long id;

    private String title;

    private Date untilNextLessonPart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUntilNextLessonPart() {
        return untilNextLessonPart;
    }

    public void setUntilNextLessonPart(Date untilNextLessonPart) {
        this.untilNextLessonPart = untilNextLessonPart;
    }
}
