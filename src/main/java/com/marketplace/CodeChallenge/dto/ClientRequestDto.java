package com.marketplace.CodeChallenge.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDto {
    private Long clientId;
    private String username;
    private String password;
}
