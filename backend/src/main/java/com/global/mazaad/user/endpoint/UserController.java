package com.global.mazaad.user.endpoint;

import com.global.mazaad.user.dto.UserDto;
import com.global.mazaad.user.dto.UserResponseDto;
import com.global.mazaad.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor()
@RequestMapping("api/v1/users")
public class UserController {
  private final UserService userService;

  @GetMapping("/profile")
  public ResponseEntity<UserResponseDto> getProfile() {
    UserResponseDto userResponseDto = userService.getProfile();
    return ResponseEntity.ok(userResponseDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
    UserResponseDto userResponseDto = userService.findUserResponseDtoById(id);
    return ResponseEntity.ok(userResponseDto);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping()
  public ResponseEntity<List<UserResponseDto>> getAllUsers() {
    List<UserResponseDto> userResponseDtos = userService.findAllUsers();
    return ResponseEntity.ok(userResponseDtos);
  }

  @PutMapping
  public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto) {
    userService.updateCurrentUser(userDto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
    userService.deleteUserById(id);
    return ResponseEntity.ok().build();
  }
}
