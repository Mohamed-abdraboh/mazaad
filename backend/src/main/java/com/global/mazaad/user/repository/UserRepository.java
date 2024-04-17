package com.global.mazaad.user.repository;

import java.util.Optional;
import java.util.UUID;

import com.global.mazaad.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByPhoneNumber(String phoneNumber);

  /**
   * Updates the locked status of a user based on the given email.
   *
   * @param newPassword The new password of the user.
   * @param userId The id of the user.
   */
  @Modifying
  @Query(value = "UPDATE User u SET u.password = :newPassword WHERE u.id = :userId")
  void changeUserPassword(@Param("newPassword") String newPassword, @Param("userId") UUID userId);

  /**
   * Updates the locked status of a user based on the given email.
   *
   * @param email The email of the user.
   * @param accountLocked The new locked status to set.
   */
  //  @Modifying
  //  @Query("UPDATE User u SET u.isAccountNonLocked = :accountLocked WHERE u.email = :email")
  //  void setAccountLockedStatus(
  //      @Param("email") String email, @Param("accountLocked") boolean accountLocked);
  //
  //  /**
  //   * Unlocks all users that have corresponding entries in the login_attempts table with
  //   * is_user_locked set to false.
  //   */
  //  @Modifying
  //  @Query(
  //      value =
  //          "UPDATE User u SET u.isAccountNonLocked = true WHERE u.email IN (SELECT la.userEmail
  // FROM LoginAttemptEntity la WHERE la.isUserLocked = false)")
  //  void unlockUsers();
}
