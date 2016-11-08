package org.libre.lingvo.services;

import org.libre.lingvo.dto.FullUserDetailsDto;
import org.libre.lingvo.dto.UserDetailsDto;
import org.libre.lingvo.dto.UserRegistrationDto;
import org.libre.lingvo.dto.UserUpdatingDto;
import org.libre.lingvo.entities.User;

import java.util.List;

/**
 * Created by igorek2312 on 08.09.16.
 */

public interface UserService {
    UserDetailsDto getUserDetails(Long userId);

    List<FullUserDetailsDto> getAllFullUserDetail();

    User registerUser(UserRegistrationDto dto);

    void updateUser(Long userId,UserUpdatingDto dto);

    void deleteNotEnabledUsersWithExpiredTokens();
}
