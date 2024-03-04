package com.LeagueOfLegendsGG.LOL.GG.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "otp", indexes = @Index(name = "idx__phoneNumber", columnList = "phoneNumber"))
public class OtpEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "ID")
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "otpNumber")
    private String otp;

    @Builder
    public OtpEntity(String username, String phoneNumber, String otp) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.otp = otp;
    }
}
