package com.example.gestioninfractionsadja.service;

import com.example.gestioninfractionsadja.data.dto.request.InfractionRequestDto;
import com.example.gestioninfractionsadja.data.model.Infraction;

import java.util.List;

public interface InfractionService {

    public List<Infraction> getAllInfractions(Boolean paye);

    public Infraction getOneInfractionById(String id);

    public List<Infraction> getAllInfractionsByUser(String userId);

    public Infraction addInfraction(InfractionRequestDto request);

    public Infraction reglerInfraction(String id);

    public Infraction cancelInfraction(String id);
}
