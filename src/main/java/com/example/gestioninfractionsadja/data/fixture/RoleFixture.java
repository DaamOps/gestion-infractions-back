package com.example.gestioninfractionsadja.data.fixture;

import com.example.gestioninfractionsadja.data.dao.RoleDao;
import com.example.gestioninfractionsadja.data.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Order(1)
public class RoleFixture implements CommandLineRunner {
    private RoleDao roleDao;

    @Override
    public void run(String... args) throws Exception {
        String[] roles = {"ROLE_USER", "ROLE_ADMINISTRATEUR", "ROLE_GENDARME", "ROLE_SUPERIEUR"};
        for (String i : roles) {
            if (!roleDao.existsByLibelle(i)) roleDao.save(new Role(i));
        }
    }
}
