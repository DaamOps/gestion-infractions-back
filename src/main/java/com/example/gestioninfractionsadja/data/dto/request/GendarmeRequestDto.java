package com.example.gestioninfractionsadja.data.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class GendarmeRequestDto {

    @NotBlank
    private String prenom;

    @NotBlank
    private String nom;

    @NotBlank
    private String telephone;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String poste;

    @NotBlank
    private String brigade;

    @NotBlank
    private boolean superieur;
}
