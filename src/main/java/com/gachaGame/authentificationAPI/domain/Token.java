package com.gachaGame.authentificationAPI.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Document(collection = "tokens")
@Getter
@Setter
public class Token {

    @Id
    private String id;

    private String token;
    private String username;
    private LocalDateTime expirationDate;
}
