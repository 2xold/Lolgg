package com.LeagueOfLegendsGG.LOL.GG.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="smtp")
@Data
public class EmailSendConfig {
    private String host;
    private String port;
    private String username;
    private String password;
    private String auth;
}