package org.libre.lingvo.dao.criteria.queries;

import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.entities.Translation_;
import org.libre.lingvo.entities.User_;
import org.libre.lingvo.entities.Word_;
import org.libre.lingvo.model.PartOfSpeech;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.criteria.*;

/**
 * Created by igorek2312 on 29.10.16.
 */
@Configuration
public class TranslationCriteriaQueries extends AbstractCriteriaQueriesConfig {

    @Bean
    public CriteriaQuery<Translation> findUserTranslations() {
        CriteriaQuery<Translation> cq = cb.createQuery(Translation.class);
        Root<Translation> translationRoot = cq.from(Translation.class);
        ParameterExpression<Long> userIdParameter = cb.parameter(Long.class, "userId");
        Path<Long> userIdPath = translationRoot.get(Translation_.user).get(User_.id);
        cq.select(translationRoot);
        cq.where(cb.equal(userIdParameter, userIdPath));
        return cq;
    }

    @Bean
    public CriteriaQuery<Long> countTranslationsByUserId() {
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Translation> translationRoot = cq.from(Translation.class);
        ParameterExpression<Long> userIdParameter = cb.parameter(Long.class, "userId");
        Path<Long> userIdPath = translationRoot.get(Translation_.user).get(User_.id);
        cq.select(cb.count(translationRoot));
        cq.where(cb.equal(userIdParameter, userIdPath));
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
}
