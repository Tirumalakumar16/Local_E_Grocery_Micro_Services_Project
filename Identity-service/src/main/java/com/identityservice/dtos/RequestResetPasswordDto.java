package com.identityservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestResetPasswordDto {

    private String emailId;
    private String newPassword;

}
