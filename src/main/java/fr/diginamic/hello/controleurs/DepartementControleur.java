package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.dto.DepartementDto;
import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departements")
public class DepartementControleur implements IDepartementControleur {

    @Autowired
    private DepartementService departementService;

    //GET
    @GetMapping
    @Override
    public ResponseEntity<List<DepartementDto>> getAllDepartements() {
        List<DepartementDto> departements = departementService.getAllDepartements();
        return ResponseEntity.ok(departements);
    }

    // GET par id
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getDepartementById(@PathVariable int id) {
        Optional<DepartementDto> departement = departementService.getDepartementById(id);
        if (departement.isPresent()) {
            return ResponseEntity.ok(departement.get());
        }
        return ResponseEntity.status(404).body("Departement non trouvé");

    }

    //GET par code
    @GetMapping("/code/{code}")
    @Override
    public ResponseEntity<?> getDepartementByCode(@PathVariable String code) {
        Optional<DepartementDto> departement = departementService.getDepartementByCode(code);
        if (departement.isPresent()) {
            return ResponseEntity.ok(departement.get());
        } else {
            return ResponseEntity.status(404).body("Departement non trouvé");
        }
    }

    //POST
    @PostMapping
    @Override
    public ResponseEntity<?> createDepartement(@RequestBody DepartementDto departement) throws ExceptionFonctionnelle {
        DepartementDto saved = departementService.insertDepartement(departement);
        return ResponseEntity.ok(saved);
    }

    //PUT
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> updateDepartement(@PathVariable int id, @RequestBody DepartementDto departementModifie) throws ExceptionFonctionnelle{
        DepartementDto updated = departementService.modifierDepartement(id, departementModifie);
        return ResponseEntity.ok(updated);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> deleteDepartement(@PathVariable int id) throws ExceptionFonctionnelle{
        departementService.deleteDepartement(id);
        return ResponseEntity.ok("Département Supprimé");
    }

    // GET : villes les plus peuplées d’un département (avec limit)
    @GetMapping("/{id}/villes/top")
    @Override
    public ResponseEntity<?> getTopVilles(@PathVariable int id, @RequestParam(defaultValue = "5") int limit) throws ExceptionFonctionnelle{
        List<VilleDto> villes = departementService.getTopVillesByDepartement(id, limit);
        return ResponseEntity.ok(villes);
    }

    // GET : villes par tranche de population
    @GetMapping("/{id}/villes")
    @Override
    public ResponseEntity<?> getVillesByPopulationRange(@PathVariable int id, @RequestParam int min, @RequestParam int max) throws ExceptionFonctionnelle{
        List<VilleDto> villes = departementService.getVillesByDepartementAndPopulationRange(id, min, max);
        return ResponseEntity.ok(villes);
    }
}
