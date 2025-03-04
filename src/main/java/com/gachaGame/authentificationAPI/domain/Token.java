package com.gachaGame.authentificationAPI.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "tokens")
@Getter
@Setter
public class Token {

    public Token() {
        this.id = id;
        this.token = token;
        this.username = username;
        this.expirationDate = expirationDate;
    }

    @Id
    private String id;
    private String token;
    private String username;
    private LocalDateTime expirationDate;
}
