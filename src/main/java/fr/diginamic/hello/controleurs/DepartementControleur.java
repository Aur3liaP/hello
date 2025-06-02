package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.services.DepartementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Departement> getDepartementById(@PathVariable int id) {
        Departement departement = departementService.getDepartementById(id);
        if (departement != null) {
            return ResponseEntity.ok(departement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //GET par code
    @GetMapping("/code/{code}")
    public ResponseEntity<Departement> getDepartementByCode(@PathVariable String code) {
        try {
            Departement departement = departementService.getDepartementByCode(code);
            return ResponseEntity.ok(departement);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //POST
    @PostMapping
    public ResponseEntity<Departement> createDepartement(@Valid @RequestBody Departement departement) {
        try {
            Departement saved = departementService.insertDepartement(departement);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<List<Departement>> updateDepartement(@PathVariable int id, @Valid @RequestBody Departement departementModifie) {
        try {
            List<Departement> updatedList = departementService.modifierDepartement(id, departementModifie);
            return ResponseEntity.ok(updatedList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartement(@PathVariable int id) {
        try {
            departementService.deleteDepartement(id);
            return ResponseEntity.ok("Département Supprimé");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET : villes les plus peuplées d’un département (avec limit)
    @GetMapping("/{id}/villes/top")
    public ResponseEntity<List<Ville>> getTopVilles(@PathVariable int id,@RequestParam(defaultValue = "5") int limit) {
        try {
            List<Ville> villes = departementService.getTopVillesByDepartement(id, limit);
            return ResponseEntity.ok(villes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET : villes par tranche de population
    @GetMapping("/{id}/villes")
    public ResponseEntity<List<Ville>> getVillesByPopulationRange(@PathVariable int id, @RequestParam int min, @RequestParam int max) {
        try {
            List<Ville> villes = departementService.getVillesByDepartementAndPopulationRange(id, min, max);
            return ResponseEntity.ok(villes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
