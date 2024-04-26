package com.global.mazaad.security.jwt;

import com.global.mazaad.security.config.SecurityConstants;
import com.global.mazaad.security.exception.AbsentBearerHeaderException;
import com.global.mazaad.security.exception.JwtTokenBlacklistedException;
import com.global.mazaad.security.exception.JwtTokenException;
import com.global.mazaad.security.exception.JwtTokenHasNoUserEmailException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtAuthenticationProvider jwtAuthenticationProvider;

  @Override
  protected void doFilterInternal(
      @NonNull final HttpServletRequest httpRequest,
      @NonNull final HttpServletResponse httpResponse,
      @NonNull final FilterChain filterChain)
      throws IOException {
    try {
      if (shouldNotFilter(httpRequest)) {
        return;
      }
      var authenticationToken = jwtAuthenticationProvider.get(httpRequest);

      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      log.info("******************************************************");
      log.info(authenticationToken.getName() + " " + authenticationToken.getAuthorities());
      filterChain.doFilter(httpRequest, httpResponse);

    }
     catch (JwtTokenBlacklistedException exception) {
      handleException(
          httpResponse, "JWT Token is blacklisted", exception, HttpServletResponse.SC_BAD_REQUEST);
    } catch (AbsentBearerHeaderException exception) {
      handleException(
          httpResponse,
          "Bearer authentication header is absent",
          exception,
          HttpServletResponse.SC_BAD_REQUEST);
    } catch (ExpiredJwtException exception) {
      handleException(
          httpResponse,
          "Jwt accessToken is expired",
          exception,
          HttpServletResponse.SC_UNAUTHORIZED);
    } catch (JwtTokenHasNoUserEmailException exception) {
      handleException(
          httpResponse,
          "User email not found in jwtToken",
          exception,
          HttpServletResponse.SC_BAD_REQUEST);
    } catch (UsernameNotFoundException exception) {
      handleException(
          httpResponse,
          "User with the provided email does not exist",
          exception,
          HttpServletResponse.SC_NOT_FOUND);
    } catch (ServletException e) {

    }
  }

  private void handleException(
      HttpServletResponse httpResponse, String errorMessage, Exception exception, int statusCode)
      throws IOException {
    log.error(errorMessage, exception);
    httpResponse.setStatus(statusCode);
    httpResponse.getWriter().write("{ \"message\": \"" + errorMessage + "\" }");
  }

  @Override
  protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
    return !isSecuredUrl(request);
  }

  private boolean isSecuredUrl(HttpServletRequest request) {

    return Stream.of(
            SecurityConstants.USERS_URL,
            SecurityConstants.ADMINS_URL,
            SecurityConstants.AUCTIONS_URL,
            SecurityConstants.BIDS_URL)
        .anyMatch(securedUrl -> new AntPathRequestMatcher(securedUrl).matches(request));
  }
}
