package org.libre.lingvo.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by igorek2312 on 07.11.16.
 */
@Entity
public class Lesson {
    @Id
    @GeneratedValue
    private Long id;

    private Integer completedPartsOfExam;

    private Integer maxPartsOfExam;

    @Temporal(TemporalType.TIMESTAMP)
    private Date waitUnitNextExamPart;

    @OneToMany(mappedBy = "lesson")
    private Set<Translation> translations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCompletedPartsOfExam() {
        return completedPartsOfExam;
    }

    public void setCompletedPartsOfExam(Integer completedPartsOfExam) {
        this.completedPartsOfExam = completedPartsOfExam;
    }

    public Integer getMaxPartsOfExam() {
        return maxPartsOfExam;
    }

    public void setMaxPartsOfExam(Integer maxPartsOfExam) {
        this.maxPartsOfExam = maxPartsOfExam;
    }

    public Date getWaitUnitNextExamPart() {
        return waitUnitNextExamPart;
    }

    public void setWaitUnitNextExamPart(Date waitUnitNextExamPart) {
        this.waitUnitNextExamPart = waitUnitNextExamPart;
    }

    public Set<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<Translation> translations) {
        this.translations = translations;
    }
}
