package org.libre.lingvo.dao.criteria.queries;

import org.libre.lingvo.reference.PartOfSpeech;
import org.libre.lingvo.reference.SortingOptions;
import org.libre.lingvo.reference.TranslationSortFieldOptions;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by igorek2312 on 23.12.16.
 */
public interface TranslationFilterQueryBuilder {
    TranslationFilterQueryBuilder setUserId(Long userId);

    TranslationFilterQueryBuilder setSearchSubstring(String searchSubstring);

    TranslationFilterQueryBuilder setPartOfSpeech(PartOfSpeech partOfSpeech);

    TranslationFilterQueryBuilder setSourceLangKey(String sourceLangKey);

    TranslationFilterQueryBuilder setResultLangKey(String resultLangKey);

    TranslationFilterQueryBuilder setLearned(Boolean learned);

    TranslationFilterQueryBuilder setTagIds(List<Long> tagIds);

    TranslationFilterQueryBuilder setSortFieldOptions(TranslationSortFieldOptions sortFieldOptions);

    TranslationFilterQueryBuilder setSortingOptions(SortingOptions sortingOptions);

    <T> CriteriaQuery<T> build(Class<T> resultType);
}
