package com.example.gestioninfractionsadja.data.dto.response;

import com.example.gestioninfractionsadja.data.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GendarmeResponseDto {

    private String prenom;

    private String nom;

    private String telephone;

    private String poste;

    private String brigade;

    private String username;

    private Role role;
}
