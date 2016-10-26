package org.libre.lingvo.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by igorek2312 on 26.10.16.
 */
@Entity
public class Folder {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20)
    private String name;

    @ManyToOne
    @JoinColumn
    private Folder parentFolder;

    @OneToMany(mappedBy = "parentFolder",cascade = CascadeType.ALL)
    private Set<Folder> subFolders =new HashSet<>();

    @OneToMany(mappedBy = "folder",cascade = CascadeType.ALL)
    private Set<TranslationPreferences> translations=new HashSet<>();

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

    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public Set<Folder> getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(Set<Folder> subFolders) {
        this.subFolders = subFolders;
    }

    public Set<TranslationPreferences> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<TranslationPreferences> translations) {
        this.translations = translations;
    }
}
