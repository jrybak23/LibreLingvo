package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Word;

import java.util.Optional;

/**
 * Created by igorek2312 on 29.10.16.
 */
public interface WordDao extends GenericDao<Word,Long> {
    Optional<Word> findByTextAndLangKey(String text,String langKey);
}
