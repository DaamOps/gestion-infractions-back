package com.example.gestioninfractionsadja.data.model;

import com.example.gestioninfractionsadja.data.model.abstracts.AuditMetadata;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "infractions")
@Data
@NoArgsConstructor
public class Infraction extends AuditMetadata {

    @Id
    private String id;

    @Field(value = "nom")
    private String nom;

    @Field(value = "type")
    private String type;

    @Field(value = "cause")
    private String cause;

    @Field(value = "commentaire")
    private String commentaire;

    @Field(name = "amende")
    private BigDecimal amende = BigDecimal.valueOf(0);

    @Field(value = "paye")
    private boolean paye;

    @DBRef
    @Field(value = "fraudeur")
    private User fraudeur;

    @Field(name = "dateInfraction")
    private LocalDate dateInfraction;

    @Field(name = "annule")
    private boolean annule;

    public Infraction(String nom, String type, String cause, String commentaire, BigDecimal amende, User fraudeur) {
        this.nom = nom;
        this.type = type;
        this.cause = cause;
        this.commentaire = commentaire;
        this.amende = amende;
        this.fraudeur = fraudeur;
        this.paye = false;
        this.dateInfraction = LocalDate.now();
        this.annule = false;
    }
}
