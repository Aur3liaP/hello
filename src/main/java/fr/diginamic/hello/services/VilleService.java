package fr.diginamic.hello.services;

import fr.diginamic.hello.Utils.DtoConverterUtils;
import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello.repos.DepartementRepository;
import fr.diginamic.hello.repos.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VilleService {

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private DtoConverterUtils dtoConverterUtils;


    public List<VilleDto> extractVilles() {
        return villeRepository.findAll().stream()
                .map(VilleDto::new)
                .collect(Collectors.toList());
    }

    public Optional<VilleDto> extractVille(int id) {
        return villeRepository.findById(id)
                .map(VilleDto::new);
    }

    public Optional<VilleDto> extractVille(String nom) {
        return villeRepository.findByNomIgnoreCase(nom)
                .map(VilleDto::new);
    }

    public VilleDto insertVille(VilleDto villeDto) throws ExceptionFonctionnelle {
        // Conversion DTO vers entité
        Ville ville = dtoConverterUtils.convertirVilleDtoVersEntite(villeDto);

        validerVille(ville, null);

        Ville villeSauvee = villeRepository.save(ville);
        return new VilleDto(villeSauvee);
    }

    public VilleDto modifierVille(int id, VilleDto villeDtoModifiee) throws ExceptionFonctionnelle{
        Optional<Ville> villeExiste = villeRepository.findById(id);
        if (villeExiste.isEmpty()) {
            throw new ExceptionFonctionnelle("Ville non trouvée avec l'ID : " + id);
        }

        Ville ville = villeExiste.get();
        Ville villeModifiee = dtoConverterUtils.convertirVilleDtoVersEntite(villeDtoModifiee);

        validerVille(villeModifiee, ville.getId());

        ville.setNom(villeModifiee.getNom());
        ville.setNbHabitants(villeModifiee.getNbHabitants());
        ville.setDepartement(villeModifiee.getDepartement());

        Ville villeSauvee = villeRepository.save(ville);
        return new VilleDto(villeSauvee);
    }

    public void supprimerVille(int id) throws ExceptionFonctionnelle {
        if (!villeRepository.existsById(id)) {
            throw new ExceptionFonctionnelle("Ville non trouvée avec l'ID : " + id);
        }
        villeRepository.deleteById(id);
    }

    // Nouvelles méthodes avec DTOs
    public Page<VilleDto> extractVillesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Ville> villesPage = villeRepository.findAll(pageable);
        return villesPage.map(VilleDto::new);
    }

    public List<VilleDto> findVillesStartingWith(String prefix) throws ExceptionFonctionnelle{
        if (prefix == null || prefix.trim().isEmpty()) {
            throw new ExceptionFonctionnelle("Le préfixe de recherche ne peut pas être vide");
        }

        List<Ville> villes = villeRepository.findByNomStartingWithIgnoreCase(prefix.trim());

        if (villes.isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville dont le nom commence par " + prefix + " n'a été trouvée");
        }

        return villes.stream()
                .map(VilleDto::new)
                .collect(Collectors.toList());
    }

    public List<VilleDto> findVillesWithPopulationGreaterThan(int min) throws ExceptionFonctionnelle{
        if (min < 0) {
            throw new ExceptionFonctionnelle("La population minimum doit être positive");
        }

        List<Ville> villes = villeRepository.findByNbHabitantsGreaterThanOrderByNbHabitantsDesc(min);

        if (villes.isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville n'a une population supérieure à " + min);
        }

        return villes.stream()
                .map(VilleDto::new)
                .collect(Collectors.toList());
    }

    public List<VilleDto> findVillesWithPopulationBetween(int min, int max) throws ExceptionFonctionnelle{
        if (min < 0 || max < 0) {
            throw new ExceptionFonctionnelle("Les valeurs de population doivent être positives");
        }

        if (min > max) {
            throw new ExceptionFonctionnelle("La population minimum ne peut pas être supérieure à la population maximum");
        }

        List<Ville> villes = villeRepository.findByNbHabitantsBetweenOrderByNbHabitantsDesc(min, max);

        if (villes.isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville n'a une population comprise entre " + min + " et " + max);
        }

        return villes.stream()
                .map(VilleDto::new)
                .collect(Collectors.toList());
    }

    public List<VilleDto> findVillesByDepartementAndPopulationGreaterThan(int departementId, int min) throws ExceptionFonctionnelle{
        Optional<Departement> departement = departementRepository.findById(departementId);
        if (departement.isEmpty()) {
            throw new ExceptionFonctionnelle("Département non trouvé avec l'ID : " + departementId);
        }

        if (min < 0) {
            throw new ExceptionFonctionnelle("La population minimum doit être positive");
        }

        List<Ville> villes = villeRepository.findByDepartement_IdAndNbHabitantsGreaterThanOrderByNbHabitantsDesc(departementId, min);

        if (villes.isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville n'a une population supérieure à " + min +
                    " dans le département " + departement.get().getCode());
        }

        return villes.stream()
                .map(VilleDto::new)
                .collect(Collectors.toList());
    }

    public List<VilleDto> findVillesByDepartementAndPopulationBetween(int departementId, int min, int max) throws ExceptionFonctionnelle {
        Optional<Departement> departement = departementRepository.findById(departementId);
        if (departement.isEmpty()) {
            throw new ExceptionFonctionnelle("Département non trouvé avec l'ID : " + departementId);
        }

        if (min < 0 || max < 0) {
            throw new ExceptionFonctionnelle("Les valeurs de population doivent être positives");
        }

        if (min > max) {
            throw new ExceptionFonctionnelle("La population minimum ne peut pas être supérieure à la population maximum");
        }

        List<Ville> villes = villeRepository.findByDepartement_IdAndNbHabitantsBetweenOrderByNbHabitantsDesc(departementId, min, max);

        if (villes.isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville n'a une population comprise entre " + min + " et " + max +
                    " dans le département " + departement.get().getCode());
        }

        return villes.stream()
                .map(VilleDto::new)
                .collect(Collectors.toList());
    }

    public List<VilleDto> findTopVillesByDepartement(int departementId, int limit) throws ExceptionFonctionnelle {
        Optional<Departement> departement = departementRepository.findById(departementId);
        if (departement.isEmpty()) {
            throw new ExceptionFonctionnelle("Département non trouvé avec l'ID : " + departementId);
        }

        if (limit <= 0) {
            throw new ExceptionFonctionnelle("Le nombre de villes à retourner doit être positif");
        }

        Pageable pageable = PageRequest.of(0, limit);
        List<Ville> villes = villeRepository.findTopVillesByDepartement(departementId, pageable);

        if (villes.isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville trouvée dans le département " + departement.get().getCode());
        }

        return villes.stream()
                .map(VilleDto::new)
                .collect(Collectors.toList());
    }

    public List<VilleDto> findVillesByDepartement(String departementCode) throws ExceptionFonctionnelle {
        Optional<Departement> departement = departementRepository.findByCode(departementCode);
        if (departement.isEmpty()) {
            throw new ExceptionFonctionnelle("Département non trouvé avec l'ID : " + departementCode);
        }

        List<Ville> villes = villeRepository.findVillesByDepartement_Code(departementCode);

        if (villes.isEmpty()) {
            throw new ExceptionFonctionnelle("Aucune ville trouvée dans le département " + departement.get().getCode());
        }

        return villes.stream()
                .map(VilleDto::new)
                .collect(Collectors.toList());
    }

    // Méthodes Utilitaires



    private void validerVille(Ville ville, Integer idVilleAExclure) throws ExceptionFonctionnelle {
        List<String> erreurs = new ArrayList<>();

        if (ville.getNom() == null || ville.getNom().length() < 2) {
            erreurs.add("Le nom de la ville doit contenir au moins 2 lettres.");
        }

        if (ville.getNbHabitants() < 10) {
            erreurs.add("La ville doit avoir au moins 10 habitants.");
        }

        if (ville.getDepartement() == null || ville.getDepartement().getCode() == null ||
                ville.getDepartement().getCode().length() != 2) {
            erreurs.add("Le code du département doit contenir exactement 2 caractères.");
        }

        if (erreurs.isEmpty()) {
            boolean nomDejaPris;
            if (idVilleAExclure != null) {
                nomDejaPris = villeRepository.existsByNomIgnoreCaseAndDepartementIdAndIdNot(
                        ville.getNom(), ville.getDepartement().getId(), idVilleAExclure);
            } else {
                nomDejaPris = villeRepository.existsByNomIgnoreCaseAndDepartementId(
                        ville.getNom(), ville.getDepartement().getId());
            }
            if (nomDejaPris) {
                erreurs.add("Une ville avec ce nom existe déjà dans ce département.");
            }
        }

        if (!erreurs.isEmpty()) {
            String messageErreur = String.join("\n", erreurs);
            throw new ExceptionFonctionnelle(messageErreur);
        }
    }
}