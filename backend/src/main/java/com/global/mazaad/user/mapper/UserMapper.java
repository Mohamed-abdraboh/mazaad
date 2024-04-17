package com.global.mazaad.user.mapper;

import com.global.mazaad.user.dto.UserDto;
import com.global.mazaad.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Mapper
public interface UserMapper {
  UserDto map(User user);

  User unmap(UserDto userDto);
}
