package com.example.libre.lingvo.services;

import com.example.libre.lingvo.dao.UserDao;
import com.example.libre.lingvo.dto.UserDetailsDto;
import com.example.libre.lingvo.entities.User;
import com.example.libre.lingvo.utils.dto.converters.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by igorek2312 on 08.09.16.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserDtoConverter userDtoConverter;

    @Override
    public UserDetailsDto getUserDetails(User user) {
        /*User user = userDao.find(id);*/
        return userDtoConverter.convertToUserDetailsDto(user);
    }
}
