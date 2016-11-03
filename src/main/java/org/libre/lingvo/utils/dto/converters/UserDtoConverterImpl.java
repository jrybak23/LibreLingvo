package org.libre.lingvo.utils.dto.converters;

import org.libre.lingvo.dto.FullUserDetailsDto;
import org.libre.lingvo.dto.UserDetailsDto;
import org.libre.lingvo.dto.UserRegistrationDto;
import org.libre.lingvo.entities.User;
import org.springframework.stereotype.Component;

/**
 * Created by igorek2312 on 08.09.16.
 */
@Component
public class UserDtoConverterImpl extends AbstractDtoConverter implements UserDtoConverter {

    @Override
    public UserDetailsDto convertToUserDetailsDto(User user) {

        return modelMapper.map(user,UserDetailsDto.class);
    }

    @Override
    public FullUserDetailsDto convertToFullUserDetailsDto(User user) {
        return modelMapper.map(user, FullUserDetailsDto.class);
    }

    @Override
    public User convertFromUserRegistrationDto(UserRegistrationDto dto) {
        return modelMapper.map(dto, User.class);
    }

}
