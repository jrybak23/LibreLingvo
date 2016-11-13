package org.libre.lingvo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by igorek2312 on 07.11.16.
 */
public class LessonItemDto {
    private Long id;

    private String title;

  @JsonFormat(pattern = "MM.dd.yyyy HH:mm:ss")
    private Date waitUnitNextLessonPart;

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

    public Date getWaitUnitNextLessonPart() {
        return waitUnitNextLessonPart;
    }

    public void setWaitUnitNextLessonPart(Date waitUnitNextLessonPart) {
        this.waitUnitNextLessonPart = waitUnitNextLessonPart;
    }
}
