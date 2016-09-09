package com.example.libre.lingvo.utils.dto.converters;

import com.example.libre.lingvo.dto.UserDetailsDto;
import com.example.libre.lingvo.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by igorek2312 on 08.09.16.
 */
@Component
public class UserDtoConverterImpl implements UserDtoConverter {

    @Autowired
    @Qualifier("main")
    private ModelMapper modelMapper;

    @Override
    public UserDetailsDto convertToUserDetailsDto(User user) {
        return modelMapper.map(user,UserDetailsDto.class);
    }

}
