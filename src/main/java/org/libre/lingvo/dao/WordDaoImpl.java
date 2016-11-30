package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.Optional;

import static org.libre.lingvo.utils.DaoRetrieverUtil.findOptional;

/**
 * Created by igorek2312 on 29.10.16.
 */
@Repository
public class WordDaoImpl extends GenericDaoImpl<Word, Long> implements WordDao {
    @Autowired
    @Qualifier("findWordByTextAndLangId")
    private CriteriaQuery<Word> findWordByTextAndLangId;

    @Override
    public Optional<Word> findByTextAndLangKey(String text,String langKey) {
        return findOptional(
                () -> entityManager.createQuery(findWordByTextAndLangId)
                        .setParameter("text",text)
                        .setParameter("langKey",langKey)
                        .getSingleResult()
        );
    }
}
