package com.global.mazaad.user.endpoint;

import com.global.mazaad.user.dto.UserDto;
import com.global.mazaad.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor()
@RequestMapping("api/v1/users")
public class UserController {
  private final UserService userService;

  @GetMapping("/profile")
  public ResponseEntity<?> getProfile() {
    UserDto profile = userService.getProfile();
    return ResponseEntity.ok(profile);
  }
}
