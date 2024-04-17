package com.global.mazaad.security.converter;

import com.global.mazaad.security.dto.UserRegistrationRequest;
import com.global.mazaad.user.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserToRegistrationRequestMapper {

  User mapToUser(final UserRegistrationRequest userRegistrationRequest);

  UserRegistrationRequest mapToRequest(final User user);
}
