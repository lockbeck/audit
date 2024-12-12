package com.lockbeck.entities.token;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query(value = """
      select t from Token t inner join UserEntity u\s
      on t.userEntity.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUserEntity(Integer id);



  Optional<Token> findByToken(String token);

  List<Token> findByUserEntityId(Integer id);

  @Modifying
  @Transactional
  @Query("""
delete from Token t where t.revoked = true and t.expired = true
""")
    void deleteExpired();
}
