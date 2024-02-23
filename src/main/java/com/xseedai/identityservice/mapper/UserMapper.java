package com.xseedai.identityservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.xseedai.identityservice.dto.UserDetailsDTO;
import com.xseedai.identityservice.entity.UserCredential;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserCredential convertToEntity(UserDetailsDTO userDetailsDTO) {
        return modelMapper.map(userDetailsDTO, UserCredential.class);
    }

    public UserDetailsDTO convertToDTO(UserCredential userCredential) {
        return modelMapper.map(userCredential, UserDetailsDTO.class);
    }
}
