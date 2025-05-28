package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.entities.Ville;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    private final List<Ville> villes = new ArrayList<>();
    private final Validator validator;

    public VilleControleur(Validator validator) {
        this.validator = validator;
        villes.add(new Ville(1, "Paris", 2161000));
        villes.add(new Ville(2, "Marseille", 861635));
        villes.add(new Ville(3, "Lyon", 513275));
        villes.add(new Ville(4, "Toulouse", 471941));
        villes.add(new Ville(5, "Nice", 342522));
        villes.add(new Ville(6, "Nantes", 309346));
        villes.add(new Ville(7, "Montpellier", 285121));
        villes.add(new Ville(8, "Strasbourg", 277270));
        villes.add(new Ville(9, "Bordeaux", 252040));
        villes.add(new Ville(10, "Lille", 232741));
    }

    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }

    @PostMapping
    public ResponseEntity<String> ajouterVille(@Valid @RequestBody Ville nouvelleVille, BindingResult result){
        if (result.hasErrors()) {
            String message = result.getFieldErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(",\n"));
            return ResponseEntity.badRequest().body(message);
        }


        boolean idExiste = villes.stream().anyMatch(v -> v.getId() == nouvelleVille.getId());
        if (idExiste) {
            return ResponseEntity.badRequest().body("Une ville avec cet ID existe déjà");
        }
        // A la base pas dans controller -> classe service
        // Il faudrait ajouter des règoles métiers de verifications de nom non null + nbHabitants positif
        // if (nouvelleVille.getNom() == null || nouvellVille.getNom().trim().length()>3)
        boolean villeExiste = villes.stream().anyMatch(v -> v.getNom().equalsIgnoreCase(nouvelleVille.getNom()));
        if(villeExiste){
            return ResponseEntity.badRequest().body("La ville existe déjà");
        }
        villes.add(nouvelleVille);

        return ResponseEntity.ok("ville insérée avec succès.");
    }




    // Get par l'id
    @GetMapping("/{id}")
    public ResponseEntity<Ville> getVilleParId(@PathVariable int id) {
        boolean villeExiste = villes.stream().anyMatch(v -> v.getId() == id);

        if (villeExiste) {
            Ville ville = villes.stream()
                    .filter(v -> v.getId() == id)
                    .findFirst()
                    .get();
            return ResponseEntity.ok(ville);
        } else {
            return ResponseEntity.notFound().build(); // ou .status(404).body("message")
        }

        // if (villes.stream().noneMatch(v -> v.getId() == id)) {
        // return ResponseEntity.status(404).body("message")
        // } return ResponseEntity.of(villes.stream().filter(v->v.getId() == id)).findAny();
    }

    // Put par Id
    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille(@PathVariable int id, @RequestBody Ville villeModifiee){
        Errors result = validator.validateObject(villeModifiee);

        if(result.hasErrors()){
            String message = result.getFieldErrors().stream().map(e->e.getDefaultMessage()).collect(Collectors.joining(",\n"));
            return ResponseEntity.badRequest().body(message);
        }

        boolean idExiste = villes.stream().anyMatch(v -> v.getId() == id);
        if (idExiste) {
            Ville ville = villes.stream()
                    .filter(v -> v.getId() == id)
                    .findFirst()
                    .get();

            ville.setNom(villeModifiee.getNom());
            ville.setNbHabitants(villeModifiee.getNbHabitants());

            return ResponseEntity.ok("Ville modifiée avec succès");
        }
        return ResponseEntity.notFound().build();
    }
/*
    @PutMapping
    public ResponseEntity<String> modifierVille(@RequestBody Ville ville){
     if (villes.stream().noneMatch(v -> v.getId() == ville.getId)) {
     return ResponseEntity.status(404).body("message")
     }
     Ville villeExistante villes.stream().filter(v->v.getId() == ville.getId)).findAny().orElse(null);
     villeExistante.setNom(villeModifiee.getNom());
     villeExistante.setNbHabitants(villeModifiee.getNbHabitants());
     return ResponseEntity.ok("Ville modifiée avec succès");
     }
 */

    // Delete avec Id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerVille(@PathVariable int id) {
        boolean villeExiste = villes.removeIf(v -> v.getId() == id);

        if (villeExiste) {
            return ResponseEntity.ok("Ville supprimée avec succès");
        }
        return ResponseEntity.notFound().build();
    }


}
