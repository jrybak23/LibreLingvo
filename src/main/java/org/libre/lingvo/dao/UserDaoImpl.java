package org.libre.lingvo.dao;

import org.libre.lingvo.entities.User;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by igorek2312 on 08.09.16.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {
    private static final int EXPIRATION = 60 * 24; //minutes


    @Override
    public Optional<User> findByEmail(String email) {
        User user = (User) getSession().createQuery("from org.libre.lingvo.entities.User user where user.email = :email")
                .setParameter("email", email)
                .uniqueResult();
        return Optional.ofNullable(user);
    }

    @Override
    public void deleteExpiredNotActivatedUsers() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, -EXPIRATION);
        Date date = new Date(calendar.getTime().getTime());

        getSession().createQuery("from org.libre.lingvo.entities.User user where " +
                "user.enabled = false and " +
                "user.registrationDate < :currentMinusExpiredDate")
                .setParameter("currentMinusExpiredDate", date)
                .list()
                .forEach(getSession()::delete);

        getSession().flush();
    }

    @Override
    public boolean existsWithEmail(String email) {
        return (boolean) getSession().createQuery("select case when (count(user) > 0)  then true else false end " +
                "from org.libre.lingvo.entities.User user where user.email = :email")
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public List<User> findUsers(int pageIndex, int maxRecords) {
        return getSession().createQuery("from org.libre.lingvo.entities.User")
                .setFirstResult((pageIndex - 1) * maxRecords)
                .setMaxResults(maxRecords)
                .list();
    }

    @Override
    public Optional<User> findByActivationKey(String activationKey) {
        User user = (User) getSession().createQuery("from org.libre.lingvo.entities.User user " +
                "where user.activationKey = :activationKey")
                .setParameter("activationKey", activationKey)
                .uniqueResult();

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByResetKey(String resetKey) {
        User user = (User) getSession().createQuery("from org.libre.lingvo.entities.User user " +
                "where user.resetKey = :resetKey")
                .setParameter("resetKey", resetKey)
                .uniqueResult();

        return Optional.ofNullable(user);
    }

}
