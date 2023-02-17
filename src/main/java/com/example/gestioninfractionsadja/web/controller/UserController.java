package com.example.gestioninfractionsadja.web.controller;

import com.example.gestioninfractionsadja.data.dto.request.FraudeurRequestDto;
import com.example.gestioninfractionsadja.data.dto.request.GendarmeRequestDto;
import com.example.gestioninfractionsadja.data.dto.request.InfractionRequestDto;
import com.example.gestioninfractionsadja.data.dto.response.FraudeurResponseDto;
import com.example.gestioninfractionsadja.data.dto.response.GendarmeResponseDto;
import com.example.gestioninfractionsadja.data.model.User;
import com.example.gestioninfractionsadja.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Utilisateur", description = "Gestion des utilisateurs")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}/check")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findOneUserByUsername(@PathVariable(value = "username") String username) {
        User user = userService.findOneByUsername(username);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/fraudeurs/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATEUR', 'ROLE_GENDARME', 'ROLE_SUPERIEUR')")
    public ResponseEntity<?> findAllFraudeurs() {
        List<FraudeurResponseDto> fraudeurs = userService.getFraudeurs();

        return ResponseEntity.status(HttpStatus.OK).body(fraudeurs);
    }

    @GetMapping("/gendarmes/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATEUR', 'ROLE_GENDARME', 'ROLE_SUPERIEUR')")
    public ResponseEntity<?> findAllGendarmes() {
        List<GendarmeResponseDto> gendarmes = userService.getGendarmes("ROLE_GENDARME");

        return ResponseEntity.status(HttpStatus.OK).body(gendarmes);
    }

    @GetMapping("/superieurs/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATEUR', 'ROLE_GENDARME', 'ROLE_SUPERIEUR')")
    public ResponseEntity<?> findAllSuperieurs() {
        List<GendarmeResponseDto> superieurs = userService.getGendarmes("ROLE_SUPERIEUR");

        return ResponseEntity.status(HttpStatus.OK).body(superieurs);
    }

    @PostMapping("/fraudeurs/add")
    public ResponseEntity<?> createFraudeur(@Valid @RequestBody FraudeurRequestDto request) {
        FraudeurResponseDto fraudeur = userService.addFraudeur(request);

        return ResponseEntity.status(HttpStatus.OK).body(fraudeur);
    }

    @PostMapping("/gendarmes/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATEUR', 'ROLE_SUPERIEUR')")
    public ResponseEntity<?> createGendarme(@Valid @RequestBody GendarmeRequestDto request) {
        GendarmeResponseDto gendarme = userService.addGendarme(request);

        return ResponseEntity.status(HttpStatus.OK).body(gendarme);
    }

    @PostMapping("/superieurs/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATEUR', 'ROLE_SUPERIEUR')")
    public ResponseEntity<?> createSuperieur(@Valid @RequestBody GendarmeRequestDto request) {
        GendarmeResponseDto superieur = userService.addGendarme(request);

        return ResponseEntity.status(HttpStatus.OK).body(superieur);
    }
}
