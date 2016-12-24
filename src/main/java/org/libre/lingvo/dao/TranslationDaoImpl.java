package org.libre.lingvo.dao;

import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.libre.lingvo.dao.criteria.queries.TranslationFilterQueryBuilder;
import org.libre.lingvo.dto.LangCodesPairDto;
import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.reference.PartOfSpeech;
import org.libre.lingvo.reference.SortingOptions;
import org.libre.lingvo.reference.TranslationSortFieldOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static org.libre.lingvo.reference.ParameterNames.*;

/**
 * Created by igorek2312 on 29.10.16.
 */
@Repository
public class TranslationDaoImpl extends GenericDaoImpl<Translation, Long> implements TranslationDao {
    private final ProjectionList projectionList = Projections.projectionList()
            .add(Projections.property("sw.langCode"), "source")
            .add(Projections.property("rw.langCode"), "result");

    @Autowired
    private TranslationFilterQueryBuilder translationFilterQueryBuilder;

    @Override
    public List<Translation> findFilteredUserTranslations(
            Long userId,
            String searchSubstring,
            PartOfSpeech partOfSpeech,
            String sourceLangCode,
            String resultLangCode,
            Boolean learned,
            List<Long> tagIds,
            SortingOptions sortingOption,
            TranslationSortFieldOptions sortFieldOption, Integer pageIndex,
            Integer maxRecords
    ) {
        CriteriaQuery<Translation> cq = translationFilterQueryBuilder
                .setUserId(userId)
                .setSearchSubstring(searchSubstring)
                .setPartOfSpeech(partOfSpeech)
                .setSourceLangKey(sourceLangCode)
                .setResultLangKey(resultLangCode)
                .setLearned(learned)
                .setTagIds(tagIds)
                .setSortFieldOptions(sortFieldOption)
                .setSortingOptions(sortingOption)
                .build(Translation.class);

        return entityManager.createQuery(cq)
                .setFirstResult((pageIndex - 1) * maxRecords)
                .setMaxResults(maxRecords)
                .getResultList();
    }

    @Override
    public Long countFilteredUserTranslations(
            Long userId,
            String searchSubstring,
            PartOfSpeech partOfSpeech,
            String sourceLangCode,
            String resultLangCode,
            Boolean learned,
            List<Long> tagIds) {

        CriteriaQuery<Long> cq = translationFilterQueryBuilder
                .setUserId(userId)
                .setSearchSubstring(searchSubstring)
                .setPartOfSpeech(partOfSpeech)
                .setSourceLangKey(sourceLangCode)
                .setResultLangKey(resultLangCode)
                .setLearned(learned)
                .setTagIds(tagIds)
                .build(Long.class);

        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public Long countTotalUserTranslations(Long userId) {
        return (Long) getSession().createQuery("select count(*) " +
                "from org.libre.lingvo.entities.Translation translation  where " +
                "translation.user.id = :userId")
                .setParameter("userId", userId)
                .uniqueResult();
    }

    @Override
    public boolean existsSuchTranslation(
            Long userId,
            String sourceText,
            String sourceLangCode,
            String resultText,
            String resultLangCode,
            PartOfSpeech partOfSpeech
    ) {
        return (boolean) getSession().createQuery("select case when (count(user) > 0)  then true else false end " +
                "from org.libre.lingvo.entities.Translation translation where " +
                "translation.user.id = :userId and " +
                "translation.sourceWord.text = :sourceText and " +
                "translation.sourceWord.langCode = :sourceLangCode and " +
                "translation.resultWord.text = :resultText and " +
                "translation.resultWord.langCode = :resultLangCode and " +
                "translation.partOfSpeech = :partOfSpeech")
                .setParameter("userId", userId)
                .setParameter("sourceText", sourceText)
                .setParameter("sourceLangCode", sourceLangCode)
                .setParameter("resultText", resultText)
                .setParameter("resultLangCode", resultLangCode)
                .setParameter("partOfSpeech", partOfSpeech)
                .uniqueResult();
    }

    @Override
    public List<Translation> findSuchTranslations(
            Long userId,
            String sourceText,
            String sourceLangKey,
            String resultLangKey
    ) {
        return getSession()
                .createQuery("from org.libre.lingvo.entities.Translation translation where " +
                        "translation.user.id = :userId and " +
                        "translation.sourceWord.text = :sourceText and " +
                        "translation.sourceWord.langCode = :sourceLangCode and " +
                        "translation.resultWord.langCode = :resultLangCode")
                .setParameter("userId", userId)
                .setParameter("sourceText", sourceText)
                .setParameter("sourceLangCode", sourceLangKey)
                .setParameter("resultLangCode", resultLangKey)
                .list();
    }

    @Override
    public boolean existsOtherTranslationsDependedOnWord(Long translationId, Long wordId) {
        return (boolean) getSession().createQuery("select case when (count(user) > 0)  then true else false end " +
                "from org.libre.lingvo.entities.Translation translation where " +
                "translation.id != :translationId and " +
                "( translation.sourceWord.id = :wordId or translation.resultWord.id = :wordId )")
                .setParameter("translationId", translationId)
                .setParameter("wordId", wordId)
                .uniqueResult();
    }

    @Override
    public List<LangCodesPairDto> findLangCodesByUserId(Long userId) {
        return getSession().createCriteria(Translation.class)
                .createAlias("sourceWord", "sw")
                .createAlias("resultWord", "rw")
                .add(Restrictions.eq("user.id", userId))
                .setProjection(Projections.distinct(projectionList))
                .setResultTransformer(Transformers.aliasToBean(LangCodesPairDto.class))
                .list();
    }

    @Override
    public List<PartOfSpeech> findPartsOfSpeechByUserId(Long userId) {
        return getSession().createQuery("select distinct translation.partOfSpeech " +
                "from org.libre.lingvo.entities.Translation translation where translation.user.id = :userId")
                .setParameter("userId", userId)
                .list();
    }

}
