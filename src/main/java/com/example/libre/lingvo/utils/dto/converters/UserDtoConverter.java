package com.example.libre.lingvo.utils.dto.converters;

import com.example.libre.lingvo.dto.UserDetailsDto;
import com.example.libre.lingvo.entities.User;

/**
 * Created by igorek2312 on 08.09.16.
 */
public interface UserDtoConverter {
    UserDetailsDto convertToUserDetailsDto(User user);

}
