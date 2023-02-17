package com.example.gestioninfractionsadja.service.impl;

import com.example.gestioninfractionsadja.data.dao.InfractionDao;
import com.example.gestioninfractionsadja.data.dao.RoleDao;
import com.example.gestioninfractionsadja.data.dao.UserDao;
import com.example.gestioninfractionsadja.data.dto.request.InfractionRequestDto;
import com.example.gestioninfractionsadja.data.model.Infraction;
import com.example.gestioninfractionsadja.data.model.User;
import com.example.gestioninfractionsadja.service.InfractionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InfractionServiceImpl implements InfractionService {

    private InfractionDao infractionDao;
    private UserDao userDao;
    private RoleDao roleDao;
    private ObjectMapper objectMapper;

    @Override
    public List<Infraction> getAllInfractions(Boolean paye) {
        List<Infraction> infractions = new ArrayList<>();
        if(paye == null) infractionDao.findAllByAnnuleFalse().forEach(i -> infractions.add(objectMapper.convertValue(i, Infraction.class)));
        else infractionDao.findAllByPayeAndAnnuleFalse(paye).forEach(i -> infractions.add(objectMapper.convertValue(i, Infraction.class)));


        return infractions;
    }

    @Override
    public Infraction getOneInfractionById(String id) {
        Optional<Infraction> infractionExist = infractionDao.findByIdAndAnnuleFalse(id);

        if (!infractionExist.isPresent()) {
            throw  new IllegalArgumentException("Cette infraction n'existe pas");
        }

        Infraction infraction = infractionExist.get();

        return infraction;
    }

    @Override
    public List<Infraction> getAllInfractionsByUser(String userId) {
        List<Infraction> infractions = new ArrayList<>();
        infractionDao.findAllByFraudeurIdAndAnnuleFalse(userId).forEach(i -> infractions.add(objectMapper.convertValue(i, Infraction.class)));

        return infractions;
    }

    @Override
    public Infraction addInfraction(InfractionRequestDto request) {
        Optional<User> userExist = userDao.findByCni(request.getCniFraudeur());
        User user = new User();
        if (userExist.isPresent()) {
            user = userExist.get();
        } else {
            user.setPrenom(request.getPrenomFraudeur());
            user.setNom(request.getNomFraudeur());
            user.setCni(request.getCniFraudeur());
            user.setRole(this.roleDao.findByLibelle("ROLE_USER").get());

            user = userDao.save(user);
        }
        Infraction infraction = new Infraction(request.getNom(), request.getType(), request.getCause(), request.getCommentaire(), request.getAmende(), user);
        infractionDao.save(infraction);

        return infraction;
    }

    @Override
    public Infraction reglerInfraction(String id) {
        Optional<Infraction> infractionExist = infractionDao.findByIdAndAnnuleFalse(id);

        if (!infractionExist.isPresent()) {
            throw  new IllegalArgumentException("Cette infraction n'existe pas");
        }

        Infraction infraction = infractionExist.get();
        infraction.setPaye(true);

        infractionDao.save(infraction);

        return infraction;
    }

    @Override
    public Infraction cancelInfraction(String id) {
        Optional<Infraction> infractionExist = infractionDao.findByIdAndAnnuleFalse(id);

        if (!infractionExist.isPresent()) {
            throw  new IllegalArgumentException("Cette infraction n'existe pas");
        }

        Infraction infraction = infractionExist.get();
        infraction.setAnnule(true);

        infractionDao.save(infraction);

        return infraction;
    }
}
