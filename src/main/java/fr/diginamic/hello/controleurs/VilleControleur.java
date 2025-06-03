package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello.services.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<String> ajouterVille(@RequestBody Ville nouvelleVille) throws ExceptionFonctionnelle {
        villeService.insertVille(nouvelleVille);
        return ResponseEntity.ok("Ville insérée avec succès");
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille(@PathVariable int id, @RequestBody Ville villeModifiee) throws ExceptionFonctionnelle {

        villeService.modifierVille(id, villeModifiee);
        return ResponseEntity.ok("Ville modifiée avec succès");
    }


    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerVille(@PathVariable int id) throws ExceptionFonctionnelle {
        villeService.supprimerVille(id);
        return ResponseEntity.ok("Ville supprimée avec succès");
    }


    // Nvll routes :

    // GET all avec pagination
    @GetMapping
    public ResponseEntity<Page<Ville>> getVilles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Ville> villes = villeService.extractVillesPaginated(page, size);
        return ResponseEntity.ok(villes);
    }

    // GET villes dont le nom commence par...
    @GetMapping("/recherche/nom")
    public ResponseEntity<List<Ville>> getVillesStartingWith(@RequestParam String prefix) throws ExceptionFonctionnelle {
        List<Ville> villes = villeService.findVillesStartingWith(prefix);
        return ResponseEntity.ok(villes);
    }

    // GET villes avec population > min
    @GetMapping("/recherche/population/min")
    public ResponseEntity<List<Ville>> getVillesWithMinPopulation(@RequestParam int min) throws ExceptionFonctionnelle {
        List<Ville> villes = villeService.findVillesWithPopulationGreaterThan(min);
        return ResponseEntity.ok(villes);
    }

    // GET villes avec population entre min et max
    @GetMapping("/recherche/population/range")
    public ResponseEntity<List<Ville>> getVillesWithPopulationRange( @RequestParam int min, @RequestParam int max) throws ExceptionFonctionnelle {
        List<Ville> villes = villeService.findVillesWithPopulationBetween(min, max);
        return ResponseEntity.ok(villes);
    }

    // GET villes d'un département avec population > min
    @GetMapping("/departement/{departementId}/population/min")
    public ResponseEntity<List<Ville>> getVillesByDepartementWithMinPopulation(@PathVariable int departementId, @RequestParam int min) throws ExceptionFonctionnelle {
        List<Ville> villes = villeService.findVillesByDepartementAndPopulationGreaterThan(departementId, min);
        return ResponseEntity.ok(villes);
    }

    // GET villes d'un département avec population entre min et max
    @GetMapping("/departement/{departementId}/population/range")
    public ResponseEntity<List<Ville>> getVillesByDepartementWithPopulationRange(@PathVariable int departementId, @RequestParam int min, @RequestParam int max) throws ExceptionFonctionnelle {
        List<Ville> villes = villeService.findVillesByDepartementAndPopulationBetween(departementId, min, max);
        return ResponseEntity.ok(villes);
    }

    // GET top N villes d'un département
    @GetMapping("/departement/{departementId}/top")
    public ResponseEntity<List<Ville>> getTopVillesByDepartement(@PathVariable int departementId, @RequestParam(defaultValue = "10") int limit) throws ExceptionFonctionnelle {
        List<Ville> villes = villeService.findTopVillesByDepartement(departementId, limit);
        return ResponseEntity.ok(villes);
    }

}
