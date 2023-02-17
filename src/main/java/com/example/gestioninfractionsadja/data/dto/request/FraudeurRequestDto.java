package com.example.gestioninfractionsadja.data.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class FraudeurRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String prenom;

    @NotBlank
    private String nom;

    @NotBlank
    private String cni;

    @NotBlank
    private String adresse;

    @NotBlank
    private String telephone;

    @NotBlank
    private String matriculeVoiture;
}
