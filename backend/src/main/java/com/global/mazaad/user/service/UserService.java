package com.global.mazaad.user.service;

import com.global.mazaad.user.dto.UserDto;
import com.global.mazaad.user.dto.UserResponseDto;
import com.global.mazaad.user.entity.User;
import com.global.mazaad.user.mapper.UserMapper;
import com.global.mazaad.user.mapper.UserResponseMapper;
import com.global.mazaad.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserResponseMapper userResponseMapper;

  public UserResponseDto getProfile() {
    String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = findByPhoneNumber(phoneNumber);
    return userResponseMapper.mapToDto(user);
  }

  public User findByPhoneNumber(String phoneNumber) {
    return userRepository
        .findByPhoneNumber(phoneNumber)
        .orElseThrow(
            () ->
                new UsernameNotFoundException("User not found with phone Number: " + phoneNumber));
  }

  public User findUserById(Long id) {

    return userRepository
        .findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
  }

  public UserResponseDto findUserResponseDtoById(Long id) {
    User user = findUserById(id);
    return userResponseMapper.mapToDto(user);
  }

  public List<UserResponseDto> findAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream().map(userResponseMapper::mapToDto).toList();
  }

  public void updateCurrentUser(UserDto userDto) {
    User user = getCurrentUser();

    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setLocation(userDto.getLocation());
    user.setCompanyName(userDto.getCompanyName());
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));

    userRepository.save(user);
  }

  public void deleteUserById(Long id) {
    userRepository.deleteById(id);
  }

  public User getCurrentUser() {
    String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository
        .findByPhoneNumber(phoneNumber)
        .orElseThrow(
            () -> new UsernameNotFoundException("User not found with phoneNumber: " + phoneNumber));
  }
}
