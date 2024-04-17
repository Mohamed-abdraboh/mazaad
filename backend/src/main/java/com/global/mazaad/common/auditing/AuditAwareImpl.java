package com.global.mazaad.common.auditing;

import com.global.mazaad.user.repository.UserRepository;
import jakarta.security.auth.message.config.AuthConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class AuditAwareImpl implements AuditorAware<String> {
  private final UserRepository userRepository;

  @Override
  @NonNull
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.empty(); // No authenticated user found
    }

    Object principal = authentication.getPrincipal();
    if (principal instanceof UserDetails) {
      return Optional.of(((UserDetails) principal).getUsername());
    } else if (principal instanceof String) {
      return Optional.of((String) principal);
    } else {
      return Optional.empty(); // Unable to retrieve the username
    }
  }

  //  @Override
  //  @NonNull
  //  public Optional<String> getCurrentAuditor() {
  //
  //    //    if (phoneNumber.equals("anonymousUser")) return Optional.of("SelfRegistration");
  //    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  //    if (authentication == null) return Optional.of("SelfRegistration");
  //
  //    String phoneNumber = authentication.getName();
  //
  //    User user =
  //        userRepository
  //            .findByPhoneNumber(phoneNumber)
  //            .orElseThrow(() -> new UserNotFoundException(1L));
  //
  //    return Optional.of(user.getName());
  //  }
}
