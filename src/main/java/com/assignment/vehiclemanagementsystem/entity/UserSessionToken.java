package com.assignment.vehiclemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RedisHash("UserSessionToken")
@Entity
@Table(name = "tbl_user_session_token")
public class UserSessionToken implements Serializable {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "device_id", nullable = false)
    private String deviceID;
    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;
    @Column (name = "access_token", nullable = false)
    private String accessToken;

}