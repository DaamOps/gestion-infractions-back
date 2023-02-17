package com.example.gestioninfractionsadja.service.impl;


import com.example.gestioninfractionsadja.data.dao.RoleDao;
import com.example.gestioninfractionsadja.data.dao.UserDao;
import com.example.gestioninfractionsadja.data.dto.request.LoginRequest;
import com.example.gestioninfractionsadja.data.dto.request.RegisterRequest;
import com.example.gestioninfractionsadja.data.dto.response.JwtResponse;
import com.example.gestioninfractionsadja.data.model.User;
import com.example.gestioninfractionsadja.service.AuthService;
import com.example.gestioninfractionsadja.web.security.jwt.JwtUtils;
import com.example.gestioninfractionsadja.web.security.services.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    AuthenticationManager authenticationManager;
    UserDao userDao;
    RoleDao roleDao;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserDao userDao, RoleDao roleDao, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public JwtResponse authenticate(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            User userDetails = validateCompteUser(loginRequest.getUsername());
            if (userDetails == null) {
                return null;
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            return new JwtResponse(jwt);

        } catch(Exception e) {
            System.out.println(e);
        }

        return null;
    }

    @Override
    public void logout() {
        UserDetailsImpl currentUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<User> userExist = userDao.findByUsername(currentUser.getUsername());
        if (!userExist.isPresent()) {
            return;
        }

        User user = userExist.get();

        user.setAuthenticated(false);
        user.setCurrentAccessToken(null);
        userDao.save(user);
    }

    @Override
    public User validateCompteUser(String username) {
        Optional<User> userExist = userDao.findByUsername(username);

        if (!userExist.isPresent() || userExist.get().getStatut() == -1) {
            return null;
        }

        return userExist.get();
    }

    @Override
    public JwtResponse register(RegisterRequest request) {
        if (userDao.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Ce nom d'utilisateur existe déjà");
        }
        User user = new User(request.getUsername(), encoder.encode(request.getPassword()), request.getPrenom(), request.getNom(), roleDao.findByLibelle(request.getRole()).get());
        userDao.save(user);

        return this.authenticate(new LoginRequest(request.getUsername(), request.getPassword()));
    }
}