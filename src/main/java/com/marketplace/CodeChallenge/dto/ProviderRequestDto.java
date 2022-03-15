package com.marketplace.CodeChallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProviderRequestDto {
    private Long providerId;
    private String username;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;


}
