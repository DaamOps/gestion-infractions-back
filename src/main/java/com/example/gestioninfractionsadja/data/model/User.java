package com.example.gestioninfractionsadja.data.model;

import com.example.gestioninfractionsadja.data.dto.request.FraudeurRequestDto;
import com.example.gestioninfractionsadja.data.dto.request.GendarmeRequestDto;
import com.example.gestioninfractionsadja.data.model.abstracts.AuditMetadata;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
@Data
public class User extends AuditMetadata {

    @Id
    private String id;

    @Field(value = "username")
    private String username;

    @JsonIgnore
    @Field(value = "password")
    private String password;

    @Field(value = "prenom")
    private String prenom;

    @Field(value = "nom")
    private String nom;

    @Field(value = "cni")
    private String cni;

    @Field(value = "adresse")
    private String adresse;

    @Field(value = "telephone")
    private String telephone;

    @Field(value = "poste")
    private String poste;

    @Field(value = "brigade")
    private String brigade;

    @DBRef
    @Field(value = "role")
    private Role role;

    @Field(value = "voiture")
    private String matriculeVoiture;

    @Field(value = "authenticated")
    private boolean authenticated;

    @Field(value = "currentAccessToken")
    private String currentAccessToken;

    @Field(value = "statut")
    private int statut;

    public User() {
        this.authenticated = false;
    }

    /* Constructeur pour un admin: ROLE_ADMINISTRATEUR */
    public User(String username, String password, String prenom, String nom, Role role) {
        this.username = username;
        this.password = password;
        this.prenom = prenom;
        this.nom = nom;
        this.role = role;
        this.statut = 1;
    }

    /* Constructeur pour un utilisateur lambda: ROLE_USER */
    public User(FraudeurRequestDto request, Role role) {
        this.username = request.getUsername();
        this.password = request.getPassword();
        this.prenom = request.getPrenom();
        this.nom = request.getNom();
        this.cni = request.getCni();
        this.adresse = request.getAdresse();
        this.telephone = request.getTelephone();
        this.matriculeVoiture = request.getMatriculeVoiture();
        this.role = role;
        this.statut = 1;
    }

    /* Constructeur pour un gendarme: ROLE_GENDARME ou ROLE_SUPERIEUR */
    public User(GendarmeRequestDto request, Role role) {
        this.username = request.getUsername();
        this.password = request.getPassword();
        this.prenom = request.getPrenom();
        this.nom = request.getNom();
        this.telephone = request.getTelephone();
        this.poste = request.getPoste();
        this.brigade = request.getBrigade();
        this.role = role;
        this.statut = 1;
    }
}
