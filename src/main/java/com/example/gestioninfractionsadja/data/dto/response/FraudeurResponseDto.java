package com.example.gestioninfractionsadja.data.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FraudeurResponseDto {

    private String username;

    private String prenom;

    private String nom;

    private String cni;

    private String adresse;

    private String telephone;

    private String matriculeVoiture;
}
