package org.libre.lingvo.dao.criteria.queries;


import org.libre.lingvo.entities.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.criteria.*;

import static org.libre.lingvo.reference.ParameterNames.*;

/**
 * Created by igorek2312 on 03.12.16.
 */
@Configuration
public class TagCriteriaQueries extends AbstractCriteriaQueriesConfig {

    @Bean
    public CriteriaQuery<Tag> findTagsByUserId() {
        CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
        Root<Tag> tagRoot = cq.from(Tag.class);
        cq.select(tagRoot);
        ParameterExpression<Long> userIdPrm = cb.parameter(Long.class, USER_ID);
        Path<Long> userIdPath = tagRoot.get(Tag_.user).get(User_.id);
        Path<Integer> positionPath = tagRoot.get(Tag_.position);
        cq.where(cb.equal(userIdPrm, userIdPath));
        cq.orderBy(cb.asc(positionPath));
        return cq;
    }

    @Bean
    public CriteriaDelete<TranslationTag> removeTranslationTag(){
        CriteriaDelete<TranslationTag> cd = cb.createCriteriaDelete(TranslationTag.class);
        Root<TranslationTag> root = cd.from(TranslationTag.class);
        ParameterExpression<Long> translationIdPrm = cb.parameter(Long.class, TRANSLATION_ID);
        ParameterExpression<Long> tagIdPrm = cb.parameter(Long.class, TAG_ID);
        Path<Long> translationIdPath = root.get(TranslationTag_.pk).get(TranslationTagId_.translation).get(Translation_.id);
        Path<Long> tagIdPath = root.get(TranslationTag_.pk).get(TranslationTagId_.tag).get(Tag_.id);
        cd.where(
                cb.and(
                        cb.equal(translationIdPrm,translationIdPath),
                        cb.equal(tagIdPrm,tagIdPath)
                )
        );

        return cd;
    }

}
