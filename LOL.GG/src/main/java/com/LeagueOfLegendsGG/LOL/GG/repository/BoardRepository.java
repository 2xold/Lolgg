package com.LeagueOfLegendsGG.LOL.GG.repository;

import com.LeagueOfLegendsGG.LOL.GG.entity.BoardEntity;
import com.LeagueOfLegendsGG.LOL.GG.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Optional<BoardEntity> findById(long id);
}
