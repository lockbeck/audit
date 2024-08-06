package com.lockbeck.entities.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  Optional<UserEntity> findByEmail(String email);
  @Transactional
  @Modifying // It means it's not a select statement
  @Query(value = "UPDATE UserEntity set password = :password where id = :id")
  void changeUserPassword(@Param("password") String password, @Param("id") Integer id);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUsernameAndDeletedFalse(String username);

    Iterable<UserEntity> findAllByDeletedFalse();
}
