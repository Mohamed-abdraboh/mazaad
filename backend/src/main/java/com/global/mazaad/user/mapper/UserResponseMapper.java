package com.global.mazaad.user.mapper;

import com.global.mazaad.user.dto.UserDto;
import com.global.mazaad.user.dto.UserResponseDto;
import com.global.mazaad.user.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserResponseMapper {
  UserResponseDto mapToDto(User user);
}
