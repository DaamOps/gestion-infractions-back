package com.example.gestioninfractionsadja.data.fixture;

import com.example.gestioninfractionsadja.data.dao.RoleDao;
import com.example.gestioninfractionsadja.data.dao.UserDao;
import com.example.gestioninfractionsadja.data.model.User;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@AllArgsConstructor
@Component
@Order(2)
public class UserFixture implements CommandLineRunner {
    private UserDao userDao;
    private RoleDao roleDao;

    @Override
    public void run(String... args) throws Exception {
        String password = "password";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        if (!userDao.existsByUsername("admin")) {
            userDao.saveAll(Arrays.asList(
                    new User("admin", encodedPassword, "Adja Thiouna", "Fall", roleDao.findByLibelle("ROLE_ADMINISTRATEUR").get()),
                    new User("fraudeur", encodedPassword, "Ahmet", "Thiam", roleDao.findByLibelle("ROLE_USER").get()),
                    new User("gendarme", encodedPassword, "Demba", "Diallo", roleDao.findByLibelle("ROLE_GENDARME").get()),
                    new User("superieur", encodedPassword, "Moussa", "Sy", roleDao.findByLibelle("ROLE_SUPERIEUR").get())
            ));
        }
    }
}
