package org.libre.lingvo.entities;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by igorek2312 on 04.12.16.
 */
@Embeddable
public class TranslationTagId implements Serializable {
    private Translation translation;

    private Tag tag;

    public TranslationTagId() {
    }

    public TranslationTagId(Translation translation, Tag tag) {
        this.translation = translation;
        this.tag = tag;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TranslationTagId that = (TranslationTagId) o;

        if (translation != null ? !translation.equals(that.translation) : that.translation != null) return false;
        return tag != null ? tag.equals(that.tag) : that.tag == null;
    }

    @Override
    public int hashCode() {
        int result = translation != null ? translation.hashCode() : 0;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }
}
