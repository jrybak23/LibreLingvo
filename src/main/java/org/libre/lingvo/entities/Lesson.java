package org.libre.lingvo.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by igorek2312 on 07.11.16.
 */
@Entity
public class Lesson {
    @Id
    @GeneratedValue
    private Long id;

    private Integer completedPartsOfLesson=0;

    private Integer maxPartsOfLesson;

    @Temporal(TemporalType.TIMESTAMP)
    private Date waitUnitNextLessonPart;

    @OneToMany(mappedBy = "lesson",cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Translation> translations=new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void increaseCompletedPartsOfLesson(){
        completedPartsOfLesson++;
    }

    public Boolean isCompleted(){
        return completedPartsOfLesson.equals(maxPartsOfLesson);
    }

    public Integer getCompletedPartsOfLesson() {
        return completedPartsOfLesson;
    }

    public void setCompletedPartsOfLesson(Integer completedPartsOfLesson) {
        this.completedPartsOfLesson = completedPartsOfLesson;
    }

    public Integer getMaxPartsOfLesson() {
        return maxPartsOfLesson;
    }

    public void setMaxPartsOfLesson(Integer maxPartsOfLesson) {
        this.maxPartsOfLesson = maxPartsOfLesson;
    }

    public Date getWaitUnitNextLessonPart() {
        return waitUnitNextLessonPart;
    }

    public void setWaitUnitNextLessonPart(Date waitUnitNextLessonPart) {
        this.waitUnitNextLessonPart = waitUnitNextLessonPart;
    }

    public Set<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<Translation> translations) {
        this.translations = translations;
    }
}
