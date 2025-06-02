package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.services.VilleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @Autowired
    private VilleService villeService;

    // GET all
    @GetMapping
    public ResponseEntity<List<Ville>> getVilles() {
        List<Ville> villes = villeService.extractVilles();
        return ResponseEntity.ok(villes);
    }

    // GET par id
    @GetMapping("/{id}")
    public ResponseEntity<?> getVilleParId(@PathVariable int id) {
        Ville ville = villeService.extractVille(id);
        if (ville != null) {
            return ResponseEntity.ok(ville);
        } else {
            return ResponseEntity.status(404).body("Ville non trouvée");
        }
    }

    // GET par nom
    @GetMapping("/nom/{nom}")
    public ResponseEntity<?> getVilleParNom(@PathVariable String nom) {
        Ville ville = villeService.extractVille(nom);
        if (ville != null) {
            return ResponseEntity.ok(ville);
        } else {
            return ResponseEntity.status(404).body("Ville non trouvée");
        }
    }

    // POST
    @PostMapping
    public ResponseEntity<String> ajouterVille(@Valid @RequestBody Ville nouvelleVille, BindingResult result) {
        if (result.hasErrors()) {
            String message = result.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(",\n"));
            return ResponseEntity.badRequest().body(message);
        }

        try {
            villeService.insertVille(nouvelleVille);
            return ResponseEntity.ok("Ville insérée avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille(@PathVariable int id, @Valid @RequestBody Ville villeModifiee,
            BindingResult result) {

        if (result.hasErrors()) {
            String message = result.getFieldErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining(",\n"));
            return ResponseEntity.badRequest().body(message);
        }

        try {
            villeService.modifierVille(id, villeModifiee);
            return ResponseEntity.ok("Ville modifiée avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerVille(@PathVariable int id) {
        try {
            villeService.supprimerVille(id);
            return ResponseEntity.ok("Ville supprimée avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
