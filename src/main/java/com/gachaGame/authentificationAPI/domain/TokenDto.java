package com.gachaGame.authentificationAPI.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {

    @NotBlank(message = "Token cannot be empty")
    private String token;

    public TokenDto(String token) {
        this.token = token;
    }
}
