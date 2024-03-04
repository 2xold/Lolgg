package com.LeagueOfLegendsGG.LOL.GG.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
public class BoardEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Builder
    public BoardEntity(long id, String username, String title, String content) {
        this.username = username;
        this.title = title;
        this.content = content;
    }
}
