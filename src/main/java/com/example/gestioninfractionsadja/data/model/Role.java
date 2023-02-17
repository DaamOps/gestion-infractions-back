package com.example.gestioninfractionsadja.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
public class Role {

    @Id
    private String id;

    @Field(name = "libelle")
    private String libelle;

    public Role(String libelle) {
        this.libelle = libelle;
    }
}
