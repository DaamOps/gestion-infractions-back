package com.example.gestioninfractionsadja.data.dao;

import com.example.gestioninfractionsadja.data.model.Role;
import com.example.gestioninfractionsadja.data.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends PagingAndSortingRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByCni(String cni);

    List<User> findAllByRole(Role role);

    boolean existsByUsername(String username);
}
