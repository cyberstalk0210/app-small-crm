package org.example.appsmallcrm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")
@Builder
public class LoginDTO {

    @NotNull
    private String username;

    @NotNull
//    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-]).{8,}$")
    private String password;
}
