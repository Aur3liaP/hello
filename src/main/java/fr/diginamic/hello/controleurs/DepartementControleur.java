package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.services.DepartementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departements")
public class DepartementControleur {

    @Autowired
    private DepartementService departementService;

    //GET
    @GetMapping
    public ResponseEntity<List<Departement>> getAllDepartements() {
        List<Departement> departements = departementService.getAllDepartements();
        return ResponseEntity.ok(departements);
    }

    // GET par id
    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartementById(@PathVariable int id) {
        Optional<Departement> departement = departementService.getDepartementById(id);
        if (departement.isPresent()) {
            return ResponseEntity.ok(departement.get());
        }
        return ResponseEntity.notFound().build();

    }

    //GET par code
    @GetMapping("/code/{code}")
    public ResponseEntity<?> getDepartementByCode(@PathVariable String code) {
        Optional<Departement> departement = departementService.getDepartementByCode(code);
        if (departement.isPresent()) {
            return ResponseEntity.ok(departement.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //POST
    @PostMapping
    public ResponseEntity<?> createDepartement(@Valid @RequestBody Departement departement) {
        try {
            Departement saved = departementService.insertDepartement(departement);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartement(@PathVariable int id, @Valid @RequestBody Departement departementModifie) {
        try {
            Departement updated = departementService.modifierDepartement(id, departementModifie);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartement(@PathVariable int id) {
        try {
            departementService.deleteDepartement(id);
            return ResponseEntity.ok("Département Supprimé");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET : villes les plus peuplées d’un département (avec limit)
    @GetMapping("/{id}/villes/top")
    public ResponseEntity<?> getTopVilles(@PathVariable int id,@RequestParam(defaultValue = "5") int limit) {
        try {
            List<Ville> villes = departementService.getTopVillesByDepartement(id, limit);
            return ResponseEntity.ok(villes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET : villes par tranche de population
    @GetMapping("/{id}/villes")
    public ResponseEntity<?> getVillesByPopulationRange(@PathVariable int id, @RequestParam int min, @RequestParam int max) {
        try {
            List<Ville> villes = departementService.getVillesByDepartementAndPopulationRange(id, min, max);
            return ResponseEntity.ok(villes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
