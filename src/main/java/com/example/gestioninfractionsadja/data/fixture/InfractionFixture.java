package com.example.gestioninfractionsadja.data.fixture;

import com.example.gestioninfractionsadja.data.dao.InfractionDao;
import com.example.gestioninfractionsadja.data.dao.UserDao;
import com.example.gestioninfractionsadja.data.model.Infraction;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@AllArgsConstructor
@Component
@Order(3)
public class InfractionFixture implements CommandLineRunner {

    private InfractionDao infractionDao;
    private UserDao userDao;


    @Override
    public void run(String... args) throws Exception {
        if (!infractionDao.existsByNomAndAnnuleFalse("infraction1")) {
            for (int i=1; i<10; i++) {
                infractionDao.save(new Infraction("infraction"+i, "type"+i, "cause"+i, "commentaire"+i, BigDecimal.valueOf(2000), userDao.findByUsername("fraudeur").get()));
            }
        }
    }
}
