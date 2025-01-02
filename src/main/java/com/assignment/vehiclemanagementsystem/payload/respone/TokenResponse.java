package com.assignment.vehiclemanagementsystem.payload.respone;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/*
* @author: TanTD1
* @since: 06/08/2024 11:01
* @description : response token
* @update:
*
* */
@Getter
@Setter
@Builder

public class TokenResponse implements Serializable {

    private String accessToken;
    private String refreshToken;
    private Long userId;
}
