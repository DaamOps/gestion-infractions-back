package com.example.gestioninfractionsadja.service.impl;

import com.example.gestioninfractionsadja.data.dao.RoleDao;
import com.example.gestioninfractionsadja.data.dao.UserDao;
import com.example.gestioninfractionsadja.data.dto.request.GendarmeRequestDto;
import com.example.gestioninfractionsadja.data.dto.request.FraudeurRequestDto;
import com.example.gestioninfractionsadja.data.dto.response.GendarmeResponseDto;
import com.example.gestioninfractionsadja.data.dto.response.FraudeurResponseDto;
import com.example.gestioninfractionsadja.data.model.Role;
import com.example.gestioninfractionsadja.data.model.User;
import com.example.gestioninfractionsadja.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private ObjectMapper objectMapper;
    private PasswordEncoder encoder;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao, ObjectMapper objectMapper, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.objectMapper = objectMapper;
        this.encoder = encoder;
    }

    @Override
    public List<FraudeurResponseDto> getFraudeurs() {
        List<FraudeurResponseDto> fraudeurs = new ArrayList<>();
        Role role = this.roleDao.findByLibelle("ROLE_USER").get();
        this.userDao.findAllByRole(role).forEach(u -> fraudeurs.add(this.objectMapper.convertValue(u, FraudeurResponseDto.class)));

        return fraudeurs;
    }

    @Override
    public List<GendarmeResponseDto> getGendarmes(String roleLibelle) {
        List<GendarmeResponseDto> gendarmes = new ArrayList<>();
        Role role = this.roleDao.findByLibelle(roleLibelle).get();
        this.userDao.findAllByRole(role).forEach(u -> gendarmes.add(this.objectMapper.convertValue(u, GendarmeResponseDto.class)));

        return gendarmes;
    }

    @Override
    public User findOneByUsername(String username) {
        Optional<User> userExist = this.userDao.findByUsername(username);

        if (!userExist.isPresent()) {
            return null;
        }

        return userExist.get();
    }

    @Override
    public FraudeurResponseDto addFraudeur(FraudeurRequestDto request) {
        Role role = roleDao.findByLibelle("ROLE_USER").get();
        request.setPassword(encoder.encode(request.getPassword()));
        User user = new User(request, role);
        this.userDao.save(user);

        return objectMapper.convertValue(user, FraudeurResponseDto.class);
    }

    @Override
    public GendarmeResponseDto addGendarme(GendarmeRequestDto request) {
        Role role = new Role();
        if (request.isSuperieur()) {
            role = roleDao.findByLibelle("ROLE_SUPERIEUR").get();
        } else {
            role = roleDao.findByLibelle("ROLE_GENDARME").get();
        }

        request.setPassword(encoder.encode(request.getPassword()));
        User user = new User(request, role);
        this.userDao.save(user);

        return objectMapper.convertValue(user, GendarmeResponseDto.class);
    }
}
