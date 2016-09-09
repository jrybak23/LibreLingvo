package com.example.libre.lingvo.services;

import com.example.libre.lingvo.dto.UserDetailsDto;
import com.example.libre.lingvo.entities.User;

/**
 * Created by igorek2312 on 08.09.16.
 */

public interface UserService {
    UserDetailsDto getUserDetails(User user);
}
