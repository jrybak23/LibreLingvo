package org.libre.lingvo.dao.criteria.queries;

import org.libre.lingvo.entities.*;
import org.libre.lingvo.reference.PartOfSpeech;
import org.libre.lingvo.reference.SortingOptions;
import org.libre.lingvo.reference.TranslationSortFieldOptions;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by igorek2312 on 07.12.16.
 */
@ComponentScan
public class TranslationFilterQueryBuilder {

    @PersistenceContext
    private EntityManager entityManager;

    private Long userId;

    private String searchSubstring;

    private PartOfSpeech partOfSpeech;

    private String sourceLangKey;

    private String resultLangKey;

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

    public TranslationFilterQueryBuilder setSourceLangKey(String sourceLangKey) {
        this.sourceLangKey = sourceLangKey;
        return this;
    }

    public TranslationFilterQueryBuilder setResultLangKey(String resultLangKey) {
        this.resultLangKey = resultLangKey;
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

    public <T> CriteriaQuery<T> build(Class<T> resultType) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(resultType);
        Root<Translation> root = cq.from(Translation.class);

        Path<Long> userIdPath = root.get(Translation_.user).get(User_.id);
        Path<String> sourceTextPath = root.get(Translation_.sourceWord).get(Word_.text);
        Path<String> sourceLangKeyPath = root.get(Translation_.sourceWord).get(Word_.langCode);
        Path<String> resultTextPath = root.get(Translation_.resultWord).get(Word_.text);
        Path<String> resultLangKeyPath = root.get(Translation_.resultWord).get(Word_.langCode);
        Path<PartOfSpeech> partOfSpeechPath = root.get(Translation_.partOfSpeech);
        Path<Integer> viewsPath = root.get(Translation_.views);
        Path<Date> modificationDatePath = root.get(Translation_.lastModificationDate);
        Path<Lesson> lessonPath = root.get(Translation_.lesson);
        Path<Boolean> learnedPath = root.get(Translation_.learned);
        Path<Long> translationIdPath = root.get(Translation_.id);

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

        if (sourceLangKey != null)
            andPredicates.add(cb.equal(sourceLangKeyPath, sourceLangKey));

        if (resultLangKey != null)
            andPredicates.add(cb.equal(resultLangKeyPath, resultLangKey));

        if (learned != null)
            andPredicates.add(cb.equal(learnedPath, learned));

        Subquery<Long> sq = cq.subquery(Long.class);
        Root<TranslationTag> sqRoot = sq.from(TranslationTag.class);
        Path<Long> sqTranslationIdPath = sqRoot.get(TranslationTag_.pk).get(TranslationTagId_.translation).get(Translation_.id);
        Path<Long> sqTagIdPath = sqRoot.get(TranslationTag_.pk).get(TranslationTagId_.tag).get(Tag_.id);
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
            cq.select((Selection<? extends T>) cb.count(root.get(Translation_.id)));

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