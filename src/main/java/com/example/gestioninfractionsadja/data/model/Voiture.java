package com.example.gestioninfractionsadja.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "voitures")
@Data
@NoArgsConstructor
public class Voiture {

    @Id
    private String id;

    @Field(value = "matricule")
    private String matricule;

    public Voiture(String matricule) {
        this.matricule = matricule;
    }
}
