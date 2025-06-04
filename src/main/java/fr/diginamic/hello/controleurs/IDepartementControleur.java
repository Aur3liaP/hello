package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.dto.DepartementDto;
import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.exceptions.ExceptionFonctionnelle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IDepartementControleur {

    // GET all
    @Operation(summary = "Retourne une liste de tous les départements.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne liste de tous les départements.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepartementDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
    })
    @GetMapping
    ResponseEntity<List<DepartementDto>> getAllDepartements();

    // GET par id
    @Operation(summary = "Retourne un département en fonction de son id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne le département en fonction de son id.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepartementDto.class))),
            @ApiResponse(responseCode = "404", description = "Département non trouvé",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
    })
    @GetMapping("/{id}")
    ResponseEntity<?> getDepartementById(@PathVariable int id);

    // GET par code
    @Operation(summary = "Retourne un département en fonction de son code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne le département en fonction de son code.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepartementDto.class))),
            @ApiResponse(responseCode = "404", description = "Département non trouvé",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
    })
    @GetMapping("/code/{code}")
    ResponseEntity<?> getDepartementByCode(@PathVariable String code);

    // POST
    @Operation(summary = "Création d'un nouveau département")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Département créé avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Departement.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain"))
    })
    @PostMapping
    ResponseEntity<?> createDepartement(@RequestBody DepartementDto departement) throws ExceptionFonctionnelle;

    // PUT
    @Operation(summary = "Modification d'un département existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Département modifié avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Departement.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Département non trouvé",
                    content = @Content(mediaType = "text/plain"))
    })
    @PutMapping("/{id}")
    ResponseEntity<?> updateDepartement(@PathVariable int id, @RequestBody DepartementDto departementModifie) throws ExceptionFonctionnelle;

    // DELETE
    @Operation(summary = "Suppression d'un département existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Département supprimé avec succès",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Département non trouvé",
                    content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteDepartement(@PathVariable int id) throws ExceptionFonctionnelle;

    // GET : villes les plus peuplées d'un département (avec limit)
    @Operation(summary = "Retourne les villes les plus peuplées d'un département.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne les villes les plus peuplées d'un département.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Département non trouvé",
                    content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/{id}/villes/top")
    ResponseEntity<?> getTopVilles(@PathVariable int id, @RequestParam(defaultValue = "5") int limit) throws ExceptionFonctionnelle;

    // GET : villes par tranche de population
    @Operation(summary = "Retourne les villes d'un département avec une population entre les données indiquées.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne les villes d'un département avec une population entre les données indiquées.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Département non trouvé",
                    content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/{id}/villes")
    ResponseEntity<?> getVillesByPopulationRange(@PathVariable int id, @RequestParam int min, @RequestParam int max) throws ExceptionFonctionnelle;
}