package com.LeagueOfLegendsGG.LOL.GG.dto.riot;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RiotDto {
    private String name;
    private String ranking;
    private String tier;
    private String tierRank;
    private int leaguePoint;
    private String winsPer;
    private int wins;
    private int losses;

    @Builder
    public RiotDto(String name, String ranking, String tier, String tierRank,
                   int leaguePoint, String winsPer, int wins, int losses) {
        this.name = name;
        this.ranking = ranking;
        this.tier = tier;
        this.tierRank = tierRank;
        this.leaguePoint = leaguePoint;
        this.winsPer = winsPer;
        this.wins = wins;
        this.losses = losses;
    }
}
