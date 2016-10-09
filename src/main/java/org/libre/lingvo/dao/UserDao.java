package org.libre.lingvo.dao;

import org.libre.lingvo.entities.User;

import java.util.List;

/**
 * Created by igorek2312 on 08.09.16.
 */
public interface UserDao extends GenericDao<User,Long> {
    User findByEmail(String email);

    List<User> findByEmailSubstring(String emailSubstring, Integer pageIndex, Integer maxRecords);
}
