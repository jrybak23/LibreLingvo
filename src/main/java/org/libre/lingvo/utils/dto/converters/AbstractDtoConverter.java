package org.libre.lingvo.utils.dto.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by igorek2312 on 29.10.16.
 */
public abstract class AbstractDtoConverter {
    @Autowired
    @Qualifier("main")
    protected ModelMapper modelMapper;

}
