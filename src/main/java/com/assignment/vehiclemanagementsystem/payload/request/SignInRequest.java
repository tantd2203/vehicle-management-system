package com.assignment.vehiclemanagementsystem.payload.request;
import com.assignment.vehiclemanagementsystem.constant.Platform;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class SignInRequest {
   @NotBlank(message = "userName must be not blank")
   private String username;
   @NotBlank(message = "password must be not blank")

   private String password;
   private String deviceInfo;
//   @NotBlank(message = "platform must be not blank")
   private Platform platform;
}
