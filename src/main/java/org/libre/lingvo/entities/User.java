package org.libre.lingvo.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by igorek2312 on 08.09.16.
 */

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    private String password;

    private boolean enabled = false;

    private boolean nonLocked = true;

    @OneToMany(mappedBy = "pk.user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    private Folder rootFolder;

    private int translationsInOneLesson = 15;

    private int lessonPartsCount = 3;

    private int minutesBetweenLessonParts = 10;

    private boolean autoPlayDuringLesson = true;

    @Column(length = 36)
    private String activationKey;

    @Column(length = 36)
    private String resetKey;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    public User() {
        Folder root = new Folder();
        root.setName("root");
        setRootFolder(root);
        root.setUser(this);
    }

    public User(User user) {
        super();
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.nonLocked = user.isNonLocked();
        this.userRoles = user.getUserRoles();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isNonLocked() {
        return nonLocked;
    }

    public void setNonLocked(boolean nonLocked) {
        this.nonLocked = nonLocked;
    }

    public Folder getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(Folder rootFolder) {
        this.rootFolder = rootFolder;
    }

    public int getTranslationsInOneLesson() {
        return translationsInOneLesson;
    }

    public void setTranslationsInOneLesson(int translationsInOneLesson) {
        this.translationsInOneLesson = translationsInOneLesson;
    }

    public int getLessonPartsCount() {
        return lessonPartsCount;
    }

    public void setLessonPartsCount(int lessonPartsCount) {
        this.lessonPartsCount = lessonPartsCount;
    }

    public int getMinutesBetweenLessonParts() {
        return minutesBetweenLessonParts;
    }

    public void setMinutesBetweenLessonParts(int minutesBetweenLessonParts) {
        this.minutesBetweenLessonParts = minutesBetweenLessonParts;
    }

    public boolean isAutoPlayDuringLesson() {
        return autoPlayDuringLesson;
    }

    public void setAutoPlayDuringLesson(boolean autoPlayDuringLesson) {
        this.autoPlayDuringLesson = autoPlayDuringLesson;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
