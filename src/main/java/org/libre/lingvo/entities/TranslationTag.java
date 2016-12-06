package org.libre.lingvo.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by igorek2312 on 04.12.16.
 */
@Entity
@AssociationOverrides(value = {
        @AssociationOverride(name = "pk.translation", joinColumns = @JoinColumn(name = "translationId")),
        @AssociationOverride(name = "pk.tag", joinColumns = @JoinColumn(name = "tagId"))
})
public class TranslationTag implements Serializable {
    private TranslationTagId pk = new TranslationTagId();

    @EmbeddedId
    public TranslationTagId getPk() {
        return pk;
    }

    public void setPk(TranslationTagId pk) {
        this.pk = pk;
    }

    @Transient
    public Translation getTranslation() {
        return pk.getTranslation();
    }

    public void setTranslation(Translation translation) {
        pk.setTranslation(translation);
    }

    @Transient
    public Tag getTag() {
        return pk.getTag();
    }

    public void setTag(Tag tag) {
        pk.setTag(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TranslationTag that = (TranslationTag) o;

        return pk != null ? pk.equals(that.pk) : that.pk == null;
    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }
}
