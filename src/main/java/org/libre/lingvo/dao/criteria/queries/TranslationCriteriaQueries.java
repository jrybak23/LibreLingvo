package org.libre.lingvo.dao.criteria.queries;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.entities.Translation_;
import org.libre.lingvo.entities.User_;
import org.libre.lingvo.entities.Word_;
import org.libre.lingvo.model.ActionOptions;
import org.libre.lingvo.model.PartOfSpeech;
import org.libre.lingvo.model.TranslationsCriteria;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.criteria.*;

import static org.libre.lingvo.model.ParameterNames.*;

/**
 * Created by igorek2312 on 29.10.16.
 */
@Configuration
public class TranslationCriteriaQueries extends AbstractCriteriaQueriesConfig {

    private void configureForFilteredTranslationsQuery(CriteriaQuery cq, Root<Translation> translationRoot) {
        ParameterExpression<Long> userIdPrm = cb.parameter(Long.class, USER_ID);
        ParameterExpression<String> searchSubstringPrm = cb.parameter(String.class, SEARCH_SUBSTRING);
        Expression<String> searchSubstringPattern = cb.concat(cb.concat("%", searchSubstringPrm), "%");
        ParameterExpression<PartOfSpeech> partOfSpeechPrm = cb.parameter(PartOfSpeech.class, PART_OF_SPEECH);

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
                                cb.equal(partOfSpeechPrm, partOfSpeechPath),
                                cb.isNull(partOfSpeechPrm)
                        )
                )
        );

        /*CriteriaBuilder.Case<Path> sort = cb.selectCase();

        Expression<Path> sortSourceTextAsc = sort
                .when(cb.equal(sortSourceTextPrm, SORT_ASC), sourceTextPath)
                .otherwise(cb.nullLiteral(Path.class));

        Expression<Path> sortSourceTextDesc = sort
                .when(cb.equal(sortSourceTextPrm, SORT_DESC), sourceTextPath)
                .otherwise(cb.nullLiteral(Path.class));

        Expression<Path> sortResultTextAsc = sort
                .when(cb.equal(sortResultTextPrm, SORT_ASC), resultTextPath)
                .otherwise(cb.nullLiteral(Path.class));

        Expression<Path> sortResultTextDesc = sort
                .when(cb.equal(sortResultTextPrm, SORT_DESC), resultTextPath)
                .otherwise(cb.nullLiteral(Path.class));

        cq.orderBy(
                cb.asc(sortSourceTextAsc),
                cb.desc(sortSourceTextDesc),
                cb.asc(sortResultTextAsc),
                cb.desc(sortResultTextDesc)
        );*/

    }

    @Bean
    public LoadingCache<TranslationsCriteria,CriteriaQuery> filteredTranslationsQueries(){
        CacheLoader<TranslationsCriteria, CriteriaQuery> loader;
        loader = new CacheLoader<TranslationsCriteria, CriteriaQuery>() {
            @Override
            public CriteriaQuery load(TranslationsCriteria key) throws Exception {
                CriteriaQuery cq=null;
                if (key.getActionOption().equals(ActionOptions.FIND))
                    cq = cb.createQuery(Translation.class);
                if (key.getActionOption().equals(ActionOptions.COUNT))
                    cq = cb.createQuery(Long.class);

                Root<Translation> translationRoot = cq.from(Translation.class);
                ParameterExpression<Long> userIdPrm = cb.parameter(Long.class, USER_ID);
                ParameterExpression<String> searchSubstringPrm = cb.parameter(String.class, SEARCH_SUBSTRING);
                Expression<String> searchSubstringPattern = cb.concat(cb.concat("%", searchSubstringPrm), "%");
                ParameterExpression<PartOfSpeech> partOfSpeechPrm = cb.parameter(PartOfSpeech.class, PART_OF_SPEECH);

                Path<Long> userIdPath = translationRoot.get(Translation_.user).get(User_.id);
                Path<String> sourceTextPath = translationRoot.get(Translation_.sourceWord).get(Word_.text);
                Path<String> resultTextPath = translationRoot.get(Translation_.resultWord).get(Word_.text);
                Path<PartOfSpeech> partOfSpeechPath = translationRoot.get(Translation_.partOfSpeech);
                Path<Integer> viewsPath = translationRoot.get(Translation_.views);
                cq.where(
                        cb.and(
                                cb.equal(userIdPrm, userIdPath),
                                cb.or(
                                        cb.like(sourceTextPath, searchSubstringPattern),
                                        cb.like(resultTextPath, searchSubstringPattern)
                                ),
                                cb.or(
                                        cb.equal(partOfSpeechPrm, partOfSpeechPath),
                                        cb.isNull(partOfSpeechPrm)
                                )
                        )
                );

                if (key.getSortFieldOption()!=null){
                    Path sortingPath=null;
                    switch (key.getSortFieldOption()){
                        case SORT_SOURCE_TEXT: sortingPath=sourceTextPath; break;
                        case SORT_RESULT_TEXT: sortingPath=resultTextPath; break;
                        case SORT_VIEWS: sortingPath=viewsPath; break;
                    }

                    Order order=null;
                    switch (key.getSortingOption()){
                        case ASC:order=cb.asc(sortingPath); break;
                        case DESC:order=cb.desc(sortingPath); break;
                    }
                    cq.orderBy(order);
                }

                if (key.getActionOption().equals(ActionOptions.FIND))
                    cq.select(translationRoot);
                if (key.getActionOption().equals(ActionOptions.COUNT))
                    cq.select(cb.count(translationRoot.get(Translation_.id)));

                return cq;
            }
        };
        return CacheBuilder.newBuilder().build(loader);
    }

   /* @Bean
    public CriteriaQuery<Translation> filteredTranslations() {
        CriteriaQuery<Translation> cq = cb.createQuery(Translation.class);
        Root<Translation> translationRoot = cq.from(Translation.class);
        configureForFilteredTranslationsQuery(cq, translationRoot);
        cq.select(translationRoot);
        return cq;
    }*/

  /*  @Bean
    public CriteriaQuery<Long> countFilteredUserTranslations() {
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Translation> translationRoot = cq.from(Translation.class);
        configureForFilteredTranslationsQuery(cq, translationRoot);
        cq.select(cb.count(translationRoot.get(Translation_.id)));
        return cq;
    }*/

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
    CriteriaQuery<Boolean> existsOtherTranslationsDependedOnWord() {
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
                        cb.notEqual(translationIdPrm, translationIdPath),
                        cb.or(
                                cb.equal(wordIdPrm, sourceWordIdPath),
                                cb.equal(wordIdPrm, resultWordIdPath)
                        )
                )
        );

        CriteriaBuilder.Case<Boolean> booleanCase = cb.<Boolean>selectCase();
        Expression<Boolean> expression = booleanCase.when(cb.exists(sq), true).otherwise(false);
        cq.select(expression);

        return cq;
    }
}
