package com.LeagueOfLegendsGG.LOL.GG.repository;

import com.LeagueOfLegendsGG.LOL.GG.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findByUuid(String uuid);
    Optional<UserEntity> findByEmail(String email);
}
