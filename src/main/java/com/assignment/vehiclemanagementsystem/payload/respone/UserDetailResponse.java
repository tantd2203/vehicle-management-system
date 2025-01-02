package com.assignment.vehiclemanagementsystem.payload.respone;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDetailResponse {
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;

}
