package org.libre.lingvo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by igorek2312 on 07.11.16.
 */
public class LessonItemDto {
    private Long id;

    @JsonFormat(pattern = "MM.dd.yyyy HH:mm:ss")
    private Date waitUnitNextLessonPart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getWaitUnitNextLessonPart() {
        return waitUnitNextLessonPart;
    }

    public void setWaitUnitNextLessonPart(Date waitUnitNextLessonPart) {
        this.waitUnitNextLessonPart = waitUnitNextLessonPart;
    }
}
