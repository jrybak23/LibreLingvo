package com.example.libre.lingvo.dao;

import com.example.libre.lingvo.entities.User;

/**
 * Created by igorek2312 on 08.09.16.
 */
public interface UserDao extends GenericDao<User,Long> {
    User findByEmail(String email);
}
