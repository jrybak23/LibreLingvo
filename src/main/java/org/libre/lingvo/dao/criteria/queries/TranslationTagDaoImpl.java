package org.libre.lingvo.dao.criteria.queries;

import org.libre.lingvo.dao.GenericDaoImpl;
import org.libre.lingvo.dao.TranslationTagDao;
import org.libre.lingvo.entities.TranslationTag;
import org.libre.lingvo.entities.TranslationTagId;
import org.springframework.stereotype.Repository;

/**
 * Created by igorek2312 on 05.12.16.
 */
@Repository
public class TranslationTagDaoImpl extends GenericDaoImpl<TranslationTag, TranslationTagId> implements TranslationTagDao {
}
