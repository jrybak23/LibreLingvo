package org.libre.lingvo.dao.criteria.queries;

import org.libre.lingvo.entities.Lesson;
import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.entities.Translation_;
import org.libre.lingvo.entities.User_;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.criteria.*;

import static org.libre.lingvo.reference.ParameterNames.USER_ID;

/**
 * Created by igorek2312 on 12.11.16.
 */
@Configuration
public class LessonCriteriaQueries extends AbstractCriteriaQueriesConfig {

    @Bean
    public CriteriaQuery<Lesson> findLessonsByUserId() {
        CriteriaQuery<Lesson> cq = cb.createQuery(Lesson.class);
        ParameterExpression<Long> userIdPrm = cb.parameter(Long.class, USER_ID);
        Root<Translation> root = cq.from(Translation.class);
        Path<Long> userIdPath = root.get(Translation_.user).get(User_.id);
        Join<Translation, Lesson> lessons = root.join(Translation_.lesson, JoinType.INNER);
        cq.distinct(true);
        cq.select(lessons);
        cq.where(cb.equal(userIdPrm, userIdPath));
        return cq;
    }

}
