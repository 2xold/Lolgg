package com.LeagueOfLegendsGG.LOL.GG.controller.riot;

import com.LeagueOfLegendsGG.LOL.GG.dto.riot.LeagueEntryDto;
import com.LeagueOfLegendsGG.LOL.GG.dto.riot.RiotDto;
import com.LeagueOfLegendsGG.LOL.GG.dto.riot.SummonerDto;
import com.LeagueOfLegendsGG.LOL.GG.service.riot.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/riot")
public class SummonerController {
    private final SummonerService summonerService;

    @PostMapping(value = "/summonerByName")
    public RiotDto callSummonerByName(@RequestBody SummonerDto dto){
        SummonerDto summonerDto = summonerService.callRiotSummonerName(dto.getName());
        List<LeagueEntryDto> leagueEntryDtoArrays = summonerService.callRiotSummonerInfo(summonerDto.getId());
        LeagueEntryDto leagueEntryDto = leagueEntryDtoArrays.get(0);
        RiotDto riotDto = RiotDto.builder()
                .name(summonerDto.getName())
                .tier(leagueEntryDto.getTier())
                .tierRank(leagueEntryDto.getRank())
                .leaguePoint(leagueEntryDto.getLeaguePoints())
                .winsPer(((float)leagueEntryDto.getWins() / ((float)leagueEntryDto.getWins() + (float)leagueEntryDto.getLosses()))*100 + "%")
                .wins(leagueEntryDto.getWins())
                .losses(leagueEntryDto.getLosses())
                .build();

        return riotDto;
    }
}
