package com.example.gestioninfractionsadja.service;

import com.example.gestioninfractionsadja.data.dto.request.LoginRequest;
import com.example.gestioninfractionsadja.data.dto.request.RegisterRequest;
import com.example.gestioninfractionsadja.data.dto.response.JwtResponse;
import com.example.gestioninfractionsadja.data.model.User;

public interface AuthService {

  public JwtResponse authenticate(LoginRequest loginRequest);

  public void logout();

  public User validateCompteUser(String username);

  public JwtResponse register(RegisterRequest registerRequest);
}
