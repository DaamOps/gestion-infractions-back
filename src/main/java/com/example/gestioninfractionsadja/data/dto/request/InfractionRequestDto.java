package com.example.gestioninfractionsadja.data.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class InfractionRequestDto {

    @NotBlank
    private String prenomFraudeur;

    @NotBlank
    private String nomFraudeur;

    @NotBlank
    private String cniFraudeur;

    @NotBlank
    private String nom;

    @NotBlank
    private String type;

    @NotBlank
    private String cause;

    @NotBlank
    private String commentaire;

    @DecimalMin(value = "1.0", inclusive = true, message = "La valeur doit être positive.")
    private BigDecimal amende;
}
