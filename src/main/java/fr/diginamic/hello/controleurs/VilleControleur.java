package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.dto.VilleDto;
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
public class VilleControleur implements IVilleControleur {

    @Autowired
    private VilleService villeService;

    // GET all
    @GetMapping("/all")
    @Override
    public ResponseEntity<List<VilleDto>> getVilles() {
        List<VilleDto> villes = villeService.extractVilles();
        return ResponseEntity.ok(villes);
    }

    // GET par id
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getVilleParId(@PathVariable int id) {
        Optional<VilleDto> ville = villeService.extractVille(id);
        if (ville.isPresent()) {
            return ResponseEntity.ok(ville.get());
        } else {
            return ResponseEntity.status(404).body("Ville non trouvée");
        }
    }

    // GET par nom
    @GetMapping("/nom/{nom}")
    @Override
    public ResponseEntity<?> getVilleParNom(@PathVariable String nom) {
        Optional<VilleDto> ville = villeService.extractVille(nom);
        if (ville.isPresent()) {
            return ResponseEntity.ok(ville.get());
        } else {
            return ResponseEntity.status(404).body("Ville non trouvée");
        }
    }

    // POST
    @PostMapping
    @Override
    public ResponseEntity<String> ajouterVille(@RequestBody VilleDto nouvelleVille) throws ExceptionFonctionnelle {
        villeService.insertVille(nouvelleVille);
        return ResponseEntity.ok("Ville insérée avec succès");
    }

    // PUT
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<String> modifierVille(@PathVariable int id, @RequestBody VilleDto villeModifiee) throws ExceptionFonctionnelle {

        villeService.modifierVille(id, villeModifiee);
        return ResponseEntity.ok("Ville modifiée avec succès");
    }


    // DELETE
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<String> supprimerVille(@PathVariable int id) throws ExceptionFonctionnelle {
        villeService.supprimerVille(id);
        return ResponseEntity.ok("Ville supprimée avec succès");
    }


    // Nvll routes :

    // GET all avec pagination
    @GetMapping
    @Override
    public ResponseEntity<Page<VilleDto>> getVilles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<VilleDto> villes = villeService.extractVillesPaginated(page, size);
        return ResponseEntity.ok(villes);
    }

    // GET villes dont le nom commence par...
    @GetMapping("/recherche/nom")
    @Override
    public ResponseEntity<List<VilleDto>> getVillesStartingWith(@RequestParam String prefix) throws ExceptionFonctionnelle {
        List<VilleDto> villes = villeService.findVillesStartingWith(prefix);
        return ResponseEntity.ok(villes);
    }

    // GET villes avec population > min
    @GetMapping("/recherche/population/min")
    @Override
    public ResponseEntity<List<VilleDto>> getVillesWithMinPopulation(@RequestParam int min) throws ExceptionFonctionnelle {
        List<VilleDto> villes = villeService.findVillesWithPopulationGreaterThan(min);
        return ResponseEntity.ok(villes);
    }

    // GET villes avec population entre min et max
    @GetMapping("/recherche/population/range")
    @Override
    public ResponseEntity<List<VilleDto>> getVillesWithPopulationRange(@RequestParam int min, @RequestParam int max) throws ExceptionFonctionnelle {
        List<VilleDto> villes = villeService.findVillesWithPopulationBetween(min, max);
        return ResponseEntity.ok(villes);
    }

    // GET villes d'un département avec population > min
    @GetMapping("/departement/{departementId}/population/min")
    @Override
    public ResponseEntity<List<VilleDto>> getVillesByDepartementWithMinPopulation(@PathVariable int departementId, @RequestParam int min) throws ExceptionFonctionnelle {
        List<VilleDto> villes = villeService.findVillesByDepartementAndPopulationGreaterThan(departementId, min);
        return ResponseEntity.ok(villes);
    }

    // GET villes d'un département avec population entre min et max
    @GetMapping("/departement/{departementId}/population/range")
    @Override
    public ResponseEntity<List<VilleDto>> getVillesByDepartementWithPopulationRange(@PathVariable int departementId, @RequestParam int min, @RequestParam int max) throws ExceptionFonctionnelle {
        List<VilleDto> villes = villeService.findVillesByDepartementAndPopulationBetween(departementId, min, max);
        return ResponseEntity.ok(villes);
    }

    // GET top N villes d'un département
    @GetMapping("/departement/{departementId}/top")
    @Override
    public ResponseEntity<List<VilleDto>> getTopVillesByDepartement(@PathVariable int departementId, @RequestParam(defaultValue = "10") int limit) throws ExceptionFonctionnelle {
        List<VilleDto> villes = villeService.findTopVillesByDepartement(departementId, limit);
        return ResponseEntity.ok(villes);
    }

}
