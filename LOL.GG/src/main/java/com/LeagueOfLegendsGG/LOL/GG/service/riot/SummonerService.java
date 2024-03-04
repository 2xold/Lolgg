package com.LeagueOfLegendsGG.LOL.GG.service.riot;

import com.LeagueOfLegendsGG.LOL.GG.dto.riot.LeagueEntryDto;
import com.LeagueOfLegendsGG.LOL.GG.dto.riot.SummonerDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SummonerService {
    @Value("${riot.api.key}")
    private String apiKey;

    public SummonerDto callRiotSummonerName(String summonerName) {
        SummonerDto result;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String name = summonerName.replaceAll(" ", "%20");
            String encodedSummonerName = URLEncoder.encode(name, StandardCharsets.UTF_8);

            String requestURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + encodedSummonerName + "?api_key=" + apiKey;

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(requestURL);

            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            result = objectMapper.readValue(entity.getContent(), SummonerDto.class);

        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public List<LeagueEntryDto> callRiotSummonerInfo(String summonerId) {
        List<LeagueEntryDto> result;
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String requestURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerId + "?api_key=" + apiKey;

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(requestURL);

            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            result = objectMapper.readValue(entity.getContent(), new TypeReference<List<LeagueEntryDto>>(){});

        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
