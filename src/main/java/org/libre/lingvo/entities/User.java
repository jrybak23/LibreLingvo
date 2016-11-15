package org.libre.lingvo.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
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

    private Boolean enabled = false;

    private Boolean nonLocked = true;

    @OneToMany(mappedBy = "pk.user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private VerificationToken verificationToken;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    private Folder rootFolder;

    private int translationsInOneLesson = 15;

    private int lessonPartsCount = 3;

    private int minutesBetweenLessonParts = 10;

    private boolean autoPlayDuringLesson=true;

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

    public Boolean isNonLocked() {
        return nonLocked;
    }

    public void setNonLocked(Boolean nonLocked) {
        this.nonLocked = nonLocked;
    }

    public VerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(VerificationToken verificationToken) {
        this.verificationToken = verificationToken;
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
}
