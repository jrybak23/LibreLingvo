package org.libre.lingvo.dao.criteria.queries;

import org.libre.lingvo.entities.Word;
import org.libre.lingvo.entities.Word_;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 * Created by igorek2312 on 29.10.16.
 */
@Configuration
public class WordCriteriaQueries extends AbstractCriteriaQueriesConfig {
    @Bean
    public CriteriaQuery<Word> findWordByTextAndLangId() {
        CriteriaQuery<Word> cq = cb.createQuery(Word.class);
        Root<Word> wordRoot = cq.from(Word.class);
        ParameterExpression<String> textParameter = cb.parameter(String.class, "text");
        ParameterExpression<String> langKeyParameter = cb.parameter(String.class, "langKey");
        Path<String> textPath = wordRoot.get(Word_.text);
        Path<String> langKeyPath = wordRoot.get(Word_.langCode);
        cq.select(wordRoot);
        cq.where(
                cb.and(
                        cb.equal(textParameter, textPath),
                        cb.equal(langKeyParameter, langKeyPath)
                )
        );
        return cq;
    }
}
