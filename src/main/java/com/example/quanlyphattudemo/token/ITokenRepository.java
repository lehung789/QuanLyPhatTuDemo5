package com.example.quanlyphattudemo.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ITokenRepository extends JpaRepository<Token, Integer> {


    @Query(value = "select * from token " +
            "where phattuid = :id and (expired = false or revoked = false )", nativeQuery = true)
    List<Token> findAllValidTokensByUser(@Param("id")int id);
    @Query(value = "select * from token where stoken = :token", nativeQuery = true)
    Optional<Token> findByStoken(@Param("token") String token);
}
