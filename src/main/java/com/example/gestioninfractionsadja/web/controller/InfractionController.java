package com.example.gestioninfractionsadja.web.controller;

import com.example.gestioninfractionsadja.data.dto.request.InfractionRequestDto;
import com.example.gestioninfractionsadja.data.model.Infraction;
import com.example.gestioninfractionsadja.service.InfractionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/infractions")
@Tag(name = "Infraction", description = "Gestion des infractions")
@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATEUR', 'ROLE_GENDARME', 'ROLE_SUPERIEUR')")
public class InfractionController {

    private InfractionService infractionService;

    public InfractionController(InfractionService infractionService) {
        this.infractionService = infractionService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllInfractions(@RequestParam(required = false) Boolean paye) {

        List<Infraction> infractions = infractionService.getAllInfractions(paye);

        return ResponseEntity.ok(infractions);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findOneInfractionById(@PathVariable(value = "id") String id) {

        Infraction infraction = infractionService.getOneInfractionById(id);

        return ResponseEntity.ok(infraction);
    }

    @GetMapping("/list/paye")
    public ResponseEntity<?> findInfractionsPaye() {

        List<Infraction> infractions = infractionService.getAllInfractions(true);

        return ResponseEntity.ok(infractions);
    }

    @GetMapping("/list/non-paye")
    public ResponseEntity<?> findInfractionsNonPaye() {

        List<Infraction> infractions = infractionService.getAllInfractions(false);

        return ResponseEntity.ok(infractions);
    }

    @GetMapping("/list/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findInfractionsUser(@PathVariable(value = "id") String id) {

        List<Infraction> infractions = infractionService.getAllInfractionsByUser(id);

        return ResponseEntity.ok(infractions);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createInfraction(@Valid @RequestBody InfractionRequestDto request) {

        Infraction infraction = infractionService.addInfraction(request);

        return ResponseEntity.ok(infraction);
    }

    @PutMapping("/regler/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATEUR', 'ROLE_SUPERIEUR')")
    public ResponseEntity<?> reglerInfraction(@PathVariable(value = "id") String id) {

        Infraction infraction = infractionService.reglerInfraction(id);

        return ResponseEntity.ok(infraction);
    }

    @PutMapping("/annuler/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATEUR', 'ROLE_SUPERIEUR')")
    public ResponseEntity<?> annulerInfraction(@PathVariable(value = "id") String id) {

        Infraction infraction = infractionService.cancelInfraction(id);

        return ResponseEntity.ok(infraction);
    }
}
