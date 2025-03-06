package authCom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponseDto {
    private String message;
    private int status;
    private String token;

    // Constructor for cases where there is no token
    public ApiResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
