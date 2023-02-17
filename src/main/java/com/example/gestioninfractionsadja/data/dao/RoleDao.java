package com.example.gestioninfractionsadja.data.dao;

import com.example.gestioninfractionsadja.data.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends PagingAndSortingRepository<Role, String> {

    boolean existsByLibelle(String libelle);

    Optional<Role> findByLibelle(String libelle);
}
