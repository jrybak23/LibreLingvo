package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Word;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by igorek2312 on 29.10.16.
 */
@Repository
public class WordDaoImpl extends GenericDaoImpl<Word, Long> implements WordDao {

    @Override
    public Optional<Word> findByTextAndLangCode(String text, String langCode) {

        Word word = (Word) getSession().createQuery("from org.libre.lingvo.entities.Word word where " +
                "word.text = :text and " +
                "word.langCode  = :langCode")
                .setParameter("text", text)
                .setParameter("langCode", langCode)
                .uniqueResult();
        return Optional.ofNullable(word);
    }
}