package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Lesson;
import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.entities.TranslationTag;
import org.libre.lingvo.reference.PartOfSpeech;
import org.libre.lingvo.reference.SortingOptions;
import org.libre.lingvo.reference.TranslationSortFieldOptions;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by igorek2312 on 07.12.16.
 */
@Component
public class TranslationFilterQueryBuilder {

    @PersistenceContext
    private EntityManager entityManager;


    private Long userId;

    private String searchSubstring;

    private PartOfSpeech partOfSpeech;

    private String sourceLangCode;

    private String resultLangCode;

    private Boolean learned;

    private List<Long> tagIds;

    private TranslationSortFieldOptions sortFieldOptions;

    private SortingOptions sortingOptions;


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
        this.tagIds = (tagIds != null && tagIds.isEmpty()) ? null : tagIds;
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


    /*public Criteria build() {
        Criteria criteria = getSession()
                .createCriteria(Translation.class, "trn")
                .createAlias("trn.user", "user")
                .createAlias("trn.sourceWord", "sourceWord")
                .createAlias("trn.resultWord", "resultWord");

        criteria.add(or(
                like("sourceWord.text", searchSubstring, MatchMode.ANYWHERE),
                like("resultWord.text", searchSubstring, MatchMode.ANYWHERE)
        ));
        //criteria.add(isNull("trn.lesson"));


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
                    .add(Property.forName("pk.tag.id").in(tagIds));
            criteria.add(Subqueries.exists(existCriteria.setProjection(Projections.property("pk.translation.id"))));
            //criteria.add(Subqueries.exists(existCriteria.setProjection(Projections.property("pk"))));
            criteria.add(Subqueries.exists(existCriteria));
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

        if (action.equals(Action.SELECT))
            criteria.setProjection(Projections.distinct(P));

        if (action.equals(Action.COUNT))
            criteria.setProjection(Projections.rowCount());

        return criteria;
    }*/

    public <T> CriteriaQuery<T> build(Class<T> resultType) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(resultType);
        Root<Translation> root = cq.from(Translation.class);


        Path<Long> userIdPath = root.get("user").get("id");
        Path<String> sourceTextPath = root.get("sourceWord").get("text");
        Path<String> sourceLangKeyPath = root.get("sourceWord").get("langCode");
        Path<String> resultTextPath = root.get("resultWord").get("text");
        Path<String> resultLangKeyPath = root.get("resultWord").get("langCode");
        Path<PartOfSpeech> partOfSpeechPath = root.get("partOfSpeech");
        Path<Integer> viewsPath = root.get("views");
        Path<Date> modificationDatePath = root.get("lastModificationDate");
        Path<Lesson> lessonPath = root.get("lesson");
        Path<Boolean> learnedPath = root.get("learned");
        Path<Long> translationIdPath = root.get("id");

        String searchSubStringPattern = "%" + searchSubstring + "%";

        List<Predicate> andPredicates = new ArrayList<Predicate>();


        andPredicates.add(
                cb.or(
                        cb.like(sourceTextPath, searchSubStringPattern),
                        cb.like(resultTextPath, searchSubStringPattern)
                )
        );

        andPredicates.add(cb.isNull(lessonPath));

        if (userId != null)
            andPredicates.add(cb.equal(userIdPath, userId));

        if (partOfSpeech != null)
            andPredicates.add(cb.equal(partOfSpeechPath, partOfSpeech));

        if (sourceLangCode != null)
            andPredicates.add(cb.equal(sourceLangKeyPath, sourceLangCode));

        if (resultLangCode != null)
            andPredicates.add(cb.equal(resultLangKeyPath, resultLangCode));

        if (learned != null)
            andPredicates.add(cb.equal(learnedPath, learned));

        Subquery<Long> sq = cq.subquery(Long.class);
        Root<TranslationTag> sqRoot = sq.from(TranslationTag.class);
        Path<Long> sqTranslationIdPath = sqRoot.get("pk").get("translation").get("id");
        Path<Long> sqTagIdPath = sqRoot.get("pk").get("tag").get("id");
        sq.select(sqTranslationIdPath);

        if (tagIds != null && !tagIds.isEmpty())
            sq.where(
                    cb.and(
                            cb.equal(translationIdPath, sqTranslationIdPath),
                            sqTagIdPath.in(tagIds)
                    )
            );

        if (tagIds != null && !tagIds.isEmpty())
            andPredicates.add(cb.exists(sq));

        cq.where(cb.and((Predicate[]) andPredicates.toArray(new Predicate[]{})));

        if (resultType.isAssignableFrom(Translation.class))
            cq.select((Selection<? extends T>) root);
        if (resultType.isAssignableFrom(Long.class))
            cq.select((Selection<? extends T>) cb.count(translationIdPath));

        if (sortFieldOptions != null) {
            Path sortingPath = null;
            switch (sortFieldOptions) {
                case SORT_SOURCE_TEXT:
                    sortingPath = sourceTextPath;
                    break;
                case SORT_RESULT_TEXT:
                    sortingPath = resultTextPath;
                    break;
                case SORT_VIEWS:
                    sortingPath = viewsPath;
                    break;
                case SORT_MODIFICATION_DATE:
                    sortingPath = modificationDatePath;
                    break;
            }

            Order order = null;
            switch (sortingOptions) {
                case ASC:
                    order = cb.asc(sortingPath);
                    break;
                case DESC:
                    order = cb.desc(sortingPath);
                    break;
            }
            cq.orderBy(order);
        }

        return cq;
    }
}