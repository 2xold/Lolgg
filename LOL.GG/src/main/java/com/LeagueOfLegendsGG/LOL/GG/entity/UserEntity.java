package com.LeagueOfLegendsGG.LOL.GG.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users", indexes = @Index(name = "idx__phoneNumber__email", columnList = "phoneNumber, email"))
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "ID")
    private long id;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "username")
    private String username;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @Builder
    public UserEntity(String uuid,String username ,String phoneNumber, String email, String password) {
        this.uuid = uuid;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }
}