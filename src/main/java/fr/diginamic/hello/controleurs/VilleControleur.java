package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.services.VilleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @Autowired
    private VilleService villeService;

    // GET all
    @GetMapping("/all")
    public ResponseEntity<List<Ville>> getVilles() {
        List<Ville> villes = villeService.extractVilles();
        return ResponseEntity.ok(villes);
    }

    // GET par id
    @GetMapping("/{id}")
    public ResponseEntity<?> getVilleParId(@PathVariable int id) {
        Optional<Ville> ville = villeService.extractVille(id);
        if (ville.isPresent()) {
            return ResponseEntity.ok(ville.get());
        } else {
            return ResponseEntity.status(404).body("Ville non trouvée");
        }
    }

    // GET par nom
    @GetMapping("/nom/{nom}")
    public ResponseEntity<?> getVilleParNom(@PathVariable String nom) {
        Optional<Ville> ville = villeService.extractVille(nom);
        if (ville.isPresent()) {
            return ResponseEntity.ok(ville.get());
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


    // Nvll routes :

    // GET all avec pagination
    @GetMapping
    public ResponseEntity<Page<Ville>> getVilles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Ville> villes = villeService.extractVillesPaginated(page, size);
        return ResponseEntity.ok(villes);
    }

    // GET villes dont le nom commence par...
    @GetMapping("/recherche/nom")
    public ResponseEntity<List<Ville>> getVillesStartingWith(@RequestParam String prefix) {
        List<Ville> villes = villeService.findVillesStartingWith(prefix);
        return ResponseEntity.ok(villes);
    }

    // GET villes avec population > min
    @GetMapping("/recherche/population/min")
    public ResponseEntity<List<Ville>> getVillesWithMinPopulation(@RequestParam int min) {
        List<Ville> villes = villeService.findVillesWithPopulationGreaterThan(min);
        return ResponseEntity.ok(villes);
    }

    // GET villes avec population entre min et max
    @GetMapping("/recherche/population/range")
    public ResponseEntity<List<Ville>> getVillesWithPopulationRange(
            @RequestParam int min, @RequestParam int max) {
        try {
            List<Ville> villes = villeService.findVillesWithPopulationBetween(min, max);
            return ResponseEntity.ok(villes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // GET villes d'un département avec population > min
    @GetMapping("/departement/{departementId}/population/min")
    public ResponseEntity<List<Ville>> getVillesByDepartementWithMinPopulation(
            @PathVariable int departementId, @RequestParam int min) {
        List<Ville> villes = villeService.findVillesByDepartementAndPopulationGreaterThan(departementId, min);
        return ResponseEntity.ok(villes);
    }

    // GET villes d'un département avec population entre min et max
    @GetMapping("/departement/{departementId}/population/range")
    public ResponseEntity<List<Ville>> getVillesByDepartementWithPopulationRange(
            @PathVariable int departementId, @RequestParam int min, @RequestParam int max) {
        try {
            List<Ville> villes = villeService.findVillesByDepartementAndPopulationBetween(departementId, min, max);
            return ResponseEntity.ok(villes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // GET top N villes d'un département
    @GetMapping("/departement/{departementId}/top")
    public ResponseEntity<List<Ville>> getTopVillesByDepartement(
            @PathVariable int departementId, @RequestParam(defaultValue = "10") int limit) {
        List<Ville> villes = villeService.findTopVillesByDepartement(departementId, limit);
        return ResponseEntity.ok(villes);
    }

}
