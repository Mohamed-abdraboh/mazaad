package com.global.mazaad.user.service;

import com.global.mazaad.user.dto.UserDto;
import com.global.mazaad.user.entity.User;
import com.global.mazaad.user.mapper.UserMapper;
import com.global.mazaad.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserDto getProfile() {
    String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = findByPhoneNumber(phoneNumber);
    return userMapper.map(user);
  }

  public User findByPhoneNumber(String phoneNumber) {
    return userRepository
        .findByPhoneNumber(phoneNumber)
        .orElseThrow(
            () ->
                new UsernameNotFoundException("User not found with phone Number: " + phoneNumber));
  }
}
