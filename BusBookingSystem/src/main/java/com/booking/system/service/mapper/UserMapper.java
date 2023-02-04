package com.booking.system.service.mapper;

import com.booking.system.model.User;
import com.booking.system.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "password", source = "user.password")
    UserDTO userToUserDTO(User user);

    @Mapping(target = "firstName", source = "dto.firstName")
    @Mapping(target = "lastName", source = "dto.lastName")
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "password", source = "dto.password")
    User userDTOToUser(UserDTO dto);

    User populateUserWithPresentUserDtoFields(@MappingTarget User user, UserDTO userDto);
}
