package org.libre.lingvo.dao.criteria.queries;

import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.entities.Translation_;
import org.libre.lingvo.entities.User_;
import org.libre.lingvo.entities.Word_;
import org.libre.lingvo.model.PartOfSpeech;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.criteria.*;

import static org.libre.lingvo.model.ParametersNames.TRANSLATION_ID;
import static org.libre.lingvo.model.ParametersNames.WORD_ID;

/**
 * Created by igorek2312 on 29.10.16.
 */
@Configuration
public class TranslationCriteriaQueries extends AbstractCriteriaQueriesConfig {

    private void configureForFilteredUserTranslationsQuery(CriteriaQuery cq, Root<Translation> translationRoot) {
        ParameterExpression<Long> userIdPrm = cb.parameter(Long.class, "userId");
        ParameterExpression<String> searchSubstringPrm = cb.parameter(String.class, "searchSubstring");
        Expression<String> searchSubstringPattern = cb.concat(cb.concat("%", searchSubstringPrm), "%");
        ParameterExpression<PartOfSpeech> partOfSpeechPrm = cb.parameter(PartOfSpeech.class, "partOfSpeech");
        Path<Long> userIdPath = translationRoot.get(Translation_.user).get(User_.id);
        Path<String> sourceTextPath = translationRoot.get(Translation_.sourceWord).get(Word_.text);
        Path<String> resultTextPath = translationRoot.get(Translation_.resultWord).get(Word_.text);
        Path<PartOfSpeech> partOfSpeechPath = translationRoot.get(Translation_.partOfSpeech);
        cq.where(
                cb.and(
                        cb.equal(userIdPrm, userIdPath),
                        cb.or(
                                cb.like(sourceTextPath, searchSubstringPattern),
                                cb.like(resultTextPath, searchSubstringPattern)
                        ),
                        cb.or(
                                cb.equal(partOfSpeechPrm,partOfSpeechPath),
                                cb.isNull(partOfSpeechPrm)
                        )
                )
        );
    }

    @Bean
    public CriteriaQuery<Translation> findFilteredUserTranslations() {
        CriteriaQuery<Translation> cq = cb.createQuery(Translation.class);
        Root<Translation> translationRoot = cq.from(Translation.class);
        configureForFilteredUserTranslationsQuery(cq, translationRoot);
        cq.select(translationRoot);
        return cq;
    }

    @Bean
    public CriteriaQuery<Long> countFilteredUserTranslations() {
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Translation> translationRoot = cq.from(Translation.class);
        configureForFilteredUserTranslationsQuery(cq, translationRoot);
        cq.select(cb.count(translationRoot.get(Translation_.id)));
        return cq;
    }

    @Bean
    public CriteriaQuery<Long> countTotalUserTranslations() {
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Translation> translationRoot = cq.from(Translation.class);
        ParameterExpression<Long> userIdPrm = cb.parameter(Long.class, "userId");
        Path<Long> userIdPath = translationRoot.get(Translation_.user).get(User_.id);
        cq.where(cb.equal(userIdPrm, userIdPath));
        cq.select(cb.count(translationRoot.get(Translation_.id)));
        return cq;
    }

    @Bean
    public CriteriaQuery<Boolean> existsSuchTranslation() {
        CriteriaQuery<Boolean> cq = cb.createQuery(Boolean.class);
        cq.from(Translation.class);
        Subquery<Translation> sq = cq.subquery(Translation.class);
        ParameterExpression<Long> userIdPrm = cb.parameter(Long.class, "userId");
        ParameterExpression<String> sourceTextPrm = cb.parameter(String.class, "sourceText");
        ParameterExpression<String> sourceLangKeyPrm = cb.parameter(String.class, "sourceLangKey");
        ParameterExpression<String> resultTextPrm = cb.parameter(String.class, "resultText");
        ParameterExpression<String> resultLangKeyPrm = cb.parameter(String.class, "resultLangKey");
        ParameterExpression<PartOfSpeech> partOfSpeechPrm = cb.parameter(PartOfSpeech.class, "partOfSpeech");
        Root<Translation> translationRoot = sq.from(Translation.class);
        Path<Long> userIdPath = translationRoot.get(Translation_.user).get(User_.id);
        Path<String> sourceTextPath = translationRoot.get(Translation_.sourceWord).get(Word_.text);
        Path<String> sourceLangKeyPath = translationRoot.get(Translation_.sourceWord).get(Word_.langKey);
        Path<String> resultTextPath = translationRoot.get(Translation_.resultWord).get(Word_.text);
        Path<String> resultLangKeyPath = translationRoot.get(Translation_.resultWord).get(Word_.langKey);
        Path<PartOfSpeech> partOfSpeechPath = translationRoot.get(Translation_.partOfSpeech);

        sq.select(translationRoot);
        sq.where(
                cb.and(
                        cb.equal(userIdPrm, userIdPath),
                        cb.equal(sourceTextPrm, sourceTextPath),
                        cb.equal(resultTextPrm, resultTextPath),
                        cb.equal(sourceLangKeyPrm, sourceLangKeyPath),
                        cb.equal(resultLangKeyPrm, resultLangKeyPath),
                        cb.equal(partOfSpeechPrm, partOfSpeechPath)
                )
        );

        CriteriaBuilder.Case<Boolean> booleanCase = cb.<Boolean>selectCase();
        Expression<Boolean> expression = booleanCase.when(cb.exists(sq), true).otherwise(false);
        cq.select(expression);
        return cq;
    }

    @Bean
    CriteriaQuery<Translation> findUserTranslationsForChecking() {
        CriteriaQuery<Translation> cq = cb.createQuery(Translation.class);
        Root<Translation> translationRoot = cq.from(Translation.class);
        cq.select(translationRoot);
        ParameterExpression<Long> userIdPrm = cb.parameter(Long.class, "userId");
        ParameterExpression<String> sourceTextPrm = cb.parameter(String.class, "sourceText");
        ParameterExpression<String> sourceLangKeyPrm = cb.parameter(String.class, "sourceLangKey");
        ParameterExpression<String> resultLangKeyPrm = cb.parameter(String.class, "resultLangKey");
        Path<Long> userIdPath = translationRoot.get(Translation_.user).get(User_.id);
        Path<String> sourceTextPath = translationRoot.get(Translation_.sourceWord).get(Word_.text);
        Path<String> sourceLangKeyPath = translationRoot.get(Translation_.sourceWord).get(Word_.langKey);
        Path<String> resultLangKeyPath = translationRoot.get(Translation_.resultWord).get(Word_.langKey);
        cq.where(
                cb.and(
                        cb.equal(userIdPrm, userIdPath),
                        cb.equal(sourceTextPrm, sourceTextPath),
                        cb.equal(sourceLangKeyPrm, sourceLangKeyPath),
                        cb.equal(resultLangKeyPrm, resultLangKeyPath)
                )
        );

        return cq;
    }

    @Bean
    CriteriaQuery<Boolean> existsOtherTranslationsDependedOnWord(){
        CriteriaQuery<Boolean> cq = cb.createQuery(Boolean.class);
        cq.from(Translation.class);
        Subquery<Translation> sq = cq.subquery(Translation.class);
        Root<Translation> translationRoot = sq.from(Translation.class);

        ParameterExpression<Long> translationIdPrm = cb.parameter(Long.class, TRANSLATION_ID);
        ParameterExpression<Long> wordIdPrm = cb.parameter(Long.class, WORD_ID);
        Path<Long> translationIdPath = translationRoot.get(Translation_.id);
        Path<Long> sourceWordIdPath = translationRoot.get(Translation_.sourceWord).get(Word_.id);
        Path<Long> resultWordIdPath = translationRoot.get(Translation_.resultWord).get(Word_.id);
        sq.select(translationRoot);
        sq.where(
                cb.and(
                        cb.notEqual(translationIdPrm,translationIdPath),
                        cb.or(
                                cb.equal(wordIdPrm,sourceWordIdPath),
                                cb.equal(wordIdPrm,resultWordIdPath)
                        )
                )
        );

        CriteriaBuilder.Case<Boolean> booleanCase = cb.<Boolean>selectCase();
        Expression<Boolean> expression = booleanCase.when(cb.exists(sq), true).otherwise(false);
        cq.select(expression);

        return cq;
    }
}
