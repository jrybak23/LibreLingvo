package org.libre.lingvo.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;

/**
 * Created by igorek2312 on 08.11.16.
 */
public class UserUpdatingDto {
    @Size(max = 15, message = "{Size.userRegistrationDto.name}")
    private String name;

    @Range(min = 5, max = 100)
    private Integer translationsInOneExam = 15;

    @Range(min = 2, max = 1000)
    private Integer examPartsCount = 3;

    @Range(min = 1, max = 60)
    private Integer minutesBetweenExamParts = 10;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTranslationsInOneExam() {
        return translationsInOneExam;
    }

    public void setTranslationsInOneExam(Integer translationsInOneExam) {
        this.translationsInOneExam = translationsInOneExam;
    }

    public Integer getExamPartsCount() {
        return examPartsCount;
    }

    public void setExamPartsCount(Integer examPartsCount) {
        this.examPartsCount = examPartsCount;
    }

    public Integer getMinutesBetweenExamParts() {
        return minutesBetweenExamParts;
    }

    public void setMinutesBetweenExamParts(Integer minutesBetweenExamParts) {
        this.minutesBetweenExamParts = minutesBetweenExamParts;
    }
}
