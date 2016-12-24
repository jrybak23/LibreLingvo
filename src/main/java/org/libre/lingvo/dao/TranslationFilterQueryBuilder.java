package org.libre.lingvo.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.entities.TranslationTag;
import org.libre.lingvo.reference.PartOfSpeech;
import org.libre.lingvo.reference.SortingOptions;
import org.libre.lingvo.reference.TranslationSortFieldOptions;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

/**
 * Created by igorek2312 on 07.12.16.
 */
@Component
public class TranslationFilterQueryBuilder {

    public enum Action {
        SELECT,
        COUNT
    }

    @PersistenceContext
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    private Long userId;

    private String searchSubstring;

    private PartOfSpeech partOfSpeech;

    private String sourceLangCode;

    private String resultLangCode;

    private Boolean learned;

    private List<Long> tagIds;

    private TranslationSortFieldOptions sortFieldOptions;

    private SortingOptions sortingOptions;

    private Action action;

    public TranslationFilterQueryBuilder setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public TranslationFilterQueryBuilder setSearchSubstring(String searchSubstring) {
        this.searchSubstring = searchSubstring;
        return this;
    }

    public TranslationFilterQueryBuilder setPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
        return this;
    }

    public TranslationFilterQueryBuilder setSourceLangCode(String sourceLangCode) {
        this.sourceLangCode = sourceLangCode;
        return this;
    }

    public TranslationFilterQueryBuilder setResultLangCode(String resultLangCode) {
        this.resultLangCode = resultLangCode;
        return this;
    }

    public TranslationFilterQueryBuilder setLearned(Boolean learned) {
        this.learned = learned;
        return this;
    }

    public TranslationFilterQueryBuilder setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
        return this;
    }

    public TranslationFilterQueryBuilder setSortFieldOptions(TranslationSortFieldOptions sortFieldOptions) {
        this.sortFieldOptions = sortFieldOptions;
        return this;
    }

    public TranslationFilterQueryBuilder setSortingOptions(SortingOptions sortingOptions) {
        this.sortingOptions = sortingOptions;
        return this;
    }

    public TranslationFilterQueryBuilder setAction(Action action) {
        this.action = action;
        return this;
    }

    public Criteria build() {
        Criteria criteria = getSession()
                .createCriteria(Translation.class, "trn")
                .createAlias("trn.user", "user")
                .createAlias("trn.sourceWord", "sourceWord")
                .createAlias("trn.resultWord", "resultWord");

        criteria.add(or(
                like("sourceWord.text", searchSubstring, MatchMode.ANYWHERE),
                like("resultWord.text", searchSubstring, MatchMode.ANYWHERE)
        ));
        criteria.add(isNull("trn.lesson"));

        if (userId != null)
            criteria.add(eq("user.id", userId));
        if (partOfSpeech != null)
            criteria.add(eq("trn.partOfSpeech", partOfSpeech));
        if (sourceLangCode != null)
            criteria.add(eq("sourceWord.langCode", sourceLangCode));
        if (resultLangCode != null)
            criteria.add(eq("resultWord.langCode", resultLangCode));
        if (learned != null)
            criteria.add(eq("trn.learned", learned));

        if (tagIds != null && !tagIds.isEmpty()) {
            DetachedCriteria existCriteria = DetachedCriteria.forClass(TranslationTag.class)
                    .add(Property.forName("pk.translation.id").eqProperty("trn.id"))
                    .add(Property.forName("pk.tag.id").in(tagIds))
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            //criteria.add(Subqueries.exists(existCriteria.setProjection(Projections.property("pk.translation.id"))));
            criteria.add(Subqueries.exists(existCriteria.setProjection(Projections.id())));
        }

        if (sortFieldOptions != null) {
            String sortingPath = null;
            switch (sortFieldOptions) {
                case SORT_SOURCE_TEXT:
                    sortingPath = "sourceWord.text";
                    break;
                case SORT_RESULT_TEXT:
                    sortingPath = "resultWord.text";
                    break;
                case SORT_VIEWS:
                    sortingPath = "trn.views";
                    break;
                case SORT_MODIFICATION_DATE:
                    sortingPath = "trn.lastModificationDate";
                    break;
            }

            Order order = null;
            switch (sortingOptions) {
                case ASC:
                    order = Order.asc(sortingPath);
                    break;
                case DESC:
                    order = Order.desc(sortingPath);
                    break;
            }
            criteria.addOrder(order);
        }

        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        if (action.equals(Action.COUNT))
            criteria.setProjection(Projections.rowCount());

        return criteria;
    }
}