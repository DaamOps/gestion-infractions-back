package com.example.gestioninfractionsadja.data.dao;

import com.example.gestioninfractionsadja.data.model.Infraction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InfractionDao extends PagingAndSortingRepository<Infraction, String> {

    List<Infraction> findAllByAnnuleFalse();

    List<Infraction> findAllByPayeAndAnnuleFalse(boolean paye);

    List<Infraction> findAllByFraudeurIdAndAnnuleFalse(String userId);

    Optional<Infraction> findByIdAndAnnuleFalse(String id);

    boolean existsByNomAndAnnuleFalse(String nom);
}
