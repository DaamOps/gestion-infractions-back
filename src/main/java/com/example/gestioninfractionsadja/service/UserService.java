package com.example.gestioninfractionsadja.service;

import com.example.gestioninfractionsadja.data.dto.request.GendarmeRequestDto;
import com.example.gestioninfractionsadja.data.dto.request.FraudeurRequestDto;
import com.example.gestioninfractionsadja.data.dto.response.GendarmeResponseDto;
import com.example.gestioninfractionsadja.data.dto.response.FraudeurResponseDto;
import com.example.gestioninfractionsadja.data.model.User;

import java.util.List;

public interface UserService {

    public List<FraudeurResponseDto> getFraudeurs();

    public List<GendarmeResponseDto> getGendarmes(String role);

    public User findOneByUsername(String username);

    public FraudeurResponseDto addFraudeur(FraudeurRequestDto request);

    public GendarmeResponseDto addGendarme(GendarmeRequestDto request);
}
