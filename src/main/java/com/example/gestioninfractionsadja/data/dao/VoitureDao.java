package com.example.gestioninfractionsadja.data.dao;

import com.example.gestioninfractionsadja.data.model.Voiture;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoitureDao extends PagingAndSortingRepository<Voiture, String> {
}
