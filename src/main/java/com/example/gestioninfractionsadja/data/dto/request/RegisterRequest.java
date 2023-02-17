package com.example.gestioninfractionsadja.data.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class RegisterRequest {

    @NotBlank
    private String prenom;

    @NotBlank
    private String nom;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String role;

    public RegisterRequest() {
    }
}
