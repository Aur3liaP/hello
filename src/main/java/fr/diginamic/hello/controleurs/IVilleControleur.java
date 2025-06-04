package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.exceptions.ExceptionFonctionnelle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IVilleControleur {
    // GET all
    @Operation(summary = "Retourne une liste de toutes les villes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne liste de toutes les villes.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
    })
    @GetMapping("/all")
    ResponseEntity<List<VilleDto>> getVilles();

    // GET par id
    @Operation(summary = "Retourne une ville en fonction de son id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne la ville en fonction de son id.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
    })
    @GetMapping("/{id}")
    ResponseEntity<?> getVilleParId(@PathVariable int id);

    // GET par nom
    @Operation(summary = "Retourne une ville en fonction de son nom.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne la ville en fonction de son nom.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
    })
    @GetMapping("/nom/{nom}")
    ResponseEntity<?> getVilleParNom(@PathVariable String nom);

    // POST
    @Operation(summary = "Création d'une nouvelle ville")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ville insérée avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier (ex: nom trop court, population insuffisante...)",
                    content = @Content(mediaType = "text/plain"))
    })
    @PostMapping
    ResponseEntity<String> ajouterVille(@RequestBody VilleDto nouvelleVille) throws ExceptionFonctionnelle;

    // PUT
    @Operation(summary = "Modification d'une ville existante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ville modifiée avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Ville non trouvée",
                    content = @Content(mediaType = "text/plain"))
    })
    @PutMapping("/{id}")
    ResponseEntity<String> modifierVille(@PathVariable int id, @RequestBody VilleDto villeModifiee) throws ExceptionFonctionnelle;

    // GET all avec pagination
    @Operation(summary = "Retourne une liste de 10 villes par page.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne une liste de 10 villes par page.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
    })
    @GetMapping
    ResponseEntity<Page<VilleDto>> getVilles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);

    // DELETE
    @Operation(summary = "Suppression d'une ville existante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ville supprimée avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Ville non trouvée",
                    content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping("/{id}")
    ResponseEntity<String> supprimerVille(@PathVariable int id) throws ExceptionFonctionnelle;

    // GET villes dont le nom commence par...
    @Operation(summary = "Retourne une liste de villes dont le nom commence par le prefix indiqué")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne une liste de ville dont le nom commence par le prefix indiqué.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
    })
    @GetMapping("/recherche/nom")
    ResponseEntity<List<VilleDto>> getVillesStartingWith(@RequestParam String prefix) throws ExceptionFonctionnelle;

    // GET villes avec population > min
    @Operation(summary = "Retourne une liste de villes avec une population supérieur à la donnée indiquée.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne une liste de villes avec une population supérieur à la donnée indiquée.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
    })
    @GetMapping("/recherche/population/min")
    ResponseEntity<List<VilleDto>> getVillesWithMinPopulation(@RequestParam int min) throws ExceptionFonctionnelle;

    // GET villes avec population entre min et max
    @Operation(summary = "Retourne une liste de villes avec une population entre les données indiquées.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne une liste de villes avec une population entre les données indiquées.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
    })
    @GetMapping("/recherche/population/range")
    ResponseEntity<List<VilleDto>> getVillesWithPopulationRange(@RequestParam int min, @RequestParam int max) throws ExceptionFonctionnelle;

    // GET villes d'un département avec population > min
    @Operation(summary = "Retourne une liste de villes d'un département avec une population supérieur à la donnée indiquée.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne une liste de villes d'un département avec une population supérieur à la donnée indiquée.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Département non trouvé",
                    content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/departement/{departementId}/population/min")
    ResponseEntity<List<VilleDto>> getVillesByDepartementWithMinPopulation(@PathVariable int departementId, @RequestParam int min) throws ExceptionFonctionnelle;

    // GET villes d'un département avec population entre min et max
    @Operation(summary = "Retourne une liste de villes d'un département avec une population entre les données indiquées.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne une liste de villes d'un département avec une population entre les données indiquées.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Département non trouvé",
                    content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/departement/{departementId}/population/range")
    ResponseEntity<List<VilleDto>> getVillesByDepartementWithPopulationRange(@PathVariable int departementId, @RequestParam int min, @RequestParam int max) throws ExceptionFonctionnelle;

    // GET top N villes d'un département
    @Operation(summary = "Retourne un top des villes les plus peuplées d'un département.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retourne un top des villes les plus peuplées d'un département.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class))),
            @ApiResponse(responseCode = "400", description = "Erreur métier lors de la validation des données",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Département non trouvé",
                    content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/departement/{departementId}/top")
    ResponseEntity<List<VilleDto>> getTopVillesByDepartement(@PathVariable int departementId, @RequestParam(defaultValue = "10") int limit) throws ExceptionFonctionnelle;
}
