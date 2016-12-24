package org.libre.lingvo.dao;

import org.libre.lingvo.entities.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by igorek2312 on 08.09.16.
 */
public interface UserDao extends GenericDao<User,Long> {
    Optional<User> findByEmail(String email);

    void deleteExpiredNotActivatedUsers();

    boolean existsWithEmail(String email);

    List<User> findUsers(int pageIndex, int maxRecords);

    Optional<User> findByActivationKey(String activationKey);

    Optional<User> findByResetKey(String resetKey);
}
