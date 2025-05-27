package org.example.appsmallcrm.mapper;

import org.example.appsmallcrm.dto.UserDTO;
import org.example.appsmallcrm.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO userDTO);

    UserDTO toDto(User user);

    List<UserDTO> toDto(List<User> users);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget User user, UserDTO userDTO);
}
