package org.libre.lingvo.utils.dto.converters;

import org.libre.lingvo.dto.FullUserDetailsDto;
import org.libre.lingvo.dto.UserDetailsDto;
import org.libre.lingvo.dto.UserRegistrationDto;
import org.libre.lingvo.entities.User;

/**
 * Created by igorek2312 on 08.09.16.
 */
public interface UserDtoConverter {
    UserDetailsDto convertToUserDetailsDto(User user);

    FullUserDetailsDto convertToFullUserDetailsDto(User user);

    User convertFromUserRegistrationDto(UserRegistrationDto dto);
}
