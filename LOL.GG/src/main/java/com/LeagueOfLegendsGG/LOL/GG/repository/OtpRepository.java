package com.LeagueOfLegendsGG.LOL.GG.repository;

import com.LeagueOfLegendsGG.LOL.GG.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    Optional<OtpEntity> findByPhoneNumber(String phoneNumber);
    Optional<OtpEntity> deleteByPhoneNumber(String phoneNumber);
}
