package org.libre.lingvo.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by igorek2312 on 26.10.16.
 */
@Entity
public class Tag implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50)
    private String name;

    @Column
    private int position;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "pk.tag")
    private Set<TranslationTag> translationTags=new HashSet<>();

    public void increasePosition(){
        position++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<TranslationTag> getTranslationTags() {
        return translationTags;
    }

    public void setTranslationTags(Set<TranslationTag> translationTags) {
        this.translationTags = translationTags;
    }
}
