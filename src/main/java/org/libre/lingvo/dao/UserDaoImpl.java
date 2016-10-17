package org.libre.lingvo.dao;

import org.libre.lingvo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.libre.lingvo.utils.optional.dao.OptionalDaoUtil.findOptional;

/**
 * Created by igorek2312 on 08.09.16.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    @Autowired
    @Qualifier("findUserByEmail")
    private CriteriaQuery<User> findUserByEmailCriteriaQuery;

    @Autowired
    @Qualifier("findUsersByEmailSubstring")
    private CriteriaQuery<User> findUsersByEmailSubstring;

    @Autowired
    @Qualifier("findNotEnabledUsersWithExpiredTokens")
    private CriteriaQuery<User> findNotEnabledUsersWithExpiredTokens;

    @Autowired
    @Qualifier("existsUserWithEmail")
    private CriteriaQuery<Boolean> existsUserWithEmail;

    @Override
    public Optional<User> findByEmail(String email) {
        return findOptional(() -> entityManager.createQuery(findUserByEmailCriteriaQuery)
                .setParameter("templates", email).getSingleResult());
    }

    @Override
    public List<User> findByEmailSubstring(String emailSubstring, Integer pageIndex, Integer maxRecords) {
        return entityManager.createQuery(findUsersByEmailSubstring)
                .setParameter("emailSubstring", emailSubstring)
                .setFirstResult(pageIndex-1)
                .setMaxResults(maxRecords)
                .getResultList();
    }

    @Override
    public void deleteNotEnabledUsersWithExpiredTokens() {
        entityManager.createQuery(findNotEnabledUsersWithExpiredTokens)
                .setParameter("currentDate",new Date())
                .getResultList()
                .stream()
                .forEach(entityManager::remove);
    }

    @Override
    public boolean existWithEmail(String email) {
        return entityManager.createQuery(existsUserWithEmail)
                .setParameter("email",email)
                .getSingleResult();
    }
}
