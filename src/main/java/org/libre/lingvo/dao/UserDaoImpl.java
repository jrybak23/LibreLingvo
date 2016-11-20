package org.libre.lingvo.dao;

import org.libre.lingvo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.libre.lingvo.model.ParameterNames.*;
import static org.libre.lingvo.utils.optional.dao.OptionalDaoUtil.findOptional;

/**
 * Created by igorek2312 on 08.09.16.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {
    private static final int EXPIRATION = 60 * 24; //minutes

    @Autowired
    @Qualifier("findUserByEmail")
    private CriteriaQuery<User> findUserByEmailCriteriaQuery;

    @Autowired
    @Qualifier("findUsersByEmailSubstring")
    private CriteriaQuery<User> findUsersByEmailSubstring;

    @Autowired
    @Qualifier("findExpiredNotActivatedUsers")
    private CriteriaQuery<User> findExpiredNotActivatedUsers;

    @Autowired
    @Qualifier("existsUserWithEmail")
    private CriteriaQuery<Boolean> existsUserWithEmail;

    @Autowired
    @Qualifier("findUsers")
    private CriteriaQuery<User> findUsers;

    @Autowired
    @Qualifier("findByActivationKey")
    private CriteriaQuery<User> findByActivationKey;

    @Autowired
    @Qualifier("findByResetKey")
    private CriteriaQuery<User> findByResetKey;

    @Override
    public Optional<User> findByEmail(String email) {
        return findOptional(() -> entityManager.createQuery(findUserByEmailCriteriaQuery)
                .setParameter("templates", email).getSingleResult());
    }

    @Override
    public List<User> findByEmailSubstring(String emailSubstring, Integer pageIndex, Integer maxRecords) {
        return entityManager.createQuery(findUsersByEmailSubstring)
                .setParameter("emailSubstring", emailSubstring)
                .setFirstResult((pageIndex - 1) * maxRecords)
                .setMaxResults(maxRecords)
                .getResultList();
    }

    @Override
    public void deleteExpiredNotActivatedUsers() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, -EXPIRATION);
        Date date = new Date(calendar.getTime().getTime());

        entityManager.createQuery(findExpiredNotActivatedUsers)
                .setParameter(EXPIRED_CURRENT_DATE, date)
                .getResultList().forEach(entityManager::remove);
    }

    @Override
    public boolean existWithEmail(String email) {
        return entityManager.createQuery(existsUserWithEmail)
                .setParameter(EMAIL, email)
                .getSingleResult();
    }

    @Override
    public List<User> findUsers(int pageIndex, int maxRecords) {
        return entityManager.createQuery(findUsers)
                .setFirstResult((pageIndex - 1) * maxRecords)
                .setMaxResults(maxRecords)
                .getResultList();
    }

    @Override
    public Optional<User> findByActivationKey(String activationKey) {
        return findOptional(() -> entityManager.createQuery(findByActivationKey)
                .setParameter(ACTIVATION_KEY,activationKey)
                .getSingleResult());
    }

    @Override
    public Optional<User> findByResetKey(String resetKey) {
        return findOptional(() -> entityManager.createQuery(findByResetKey)
                .setParameter(RESET_KEY,resetKey)
                .getSingleResult());
    }
}
