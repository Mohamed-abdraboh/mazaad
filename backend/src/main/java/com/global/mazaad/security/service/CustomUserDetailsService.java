package com.global.mazaad.security.service;

import com.global.mazaad.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
    return userRepository
        .findByPhoneNumber(phoneNumber)
        .orElseThrow(
            () -> {
              log.debug("Failed to get the user with the phoneNumber = {}.", phoneNumber);
              return new UsernameNotFoundException(
                  String.format(
                      "Invalid credentials for user's account with phone number = '%s'",
                      phoneNumber));
            });
  }
}
