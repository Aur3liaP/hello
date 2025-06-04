package fr.diginamic.hello.services;

import fr.diginamic.hello.Utils.DtoConverterUtils;
import fr.diginamic.hello.dto.DepartementDto;
import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello.repos.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private VilleService villeService;

    @Autowired
    private DtoConverterUtils dtoConverterUtils;

    public List<DepartementDto> getAllDepartements() {
        return departementRepository.findAll().stream()
                .map(DepartementDto::new)
                .collect(Collectors.toList());
    }

    public Optional<DepartementDto> getDepartementById(int id) {
        return departementRepository.findById(id)
                .map(DepartementDto::new);
    }

    public Optional<DepartementDto> getDepartementByCode(String code) {
        return departementRepository.findByCode(code)
                .map(DepartementDto::new);
    }

    public DepartementDto insertDepartement(DepartementDto departementDto) throws ExceptionFonctionnelle{
        // Conversion DTO vers entité
        Departement departement = dtoConverterUtils.convertirDepartementDtoVersEntite(departementDto);

        validerDepartement(departement, null);

        Departement departementSauve = departementRepository.save(departement);
        return new DepartementDto(departementSauve);
    }

    public DepartementDto modifierDepartement(int id, DepartementDto departementDtoModifie) throws ExceptionFonctionnelle{
        Optional<Departement> departementExistant = departementRepository.findById(id);
        if (departementExistant.isEmpty()) {
            throw new ExceptionFonctionnelle("Département non trouvé avec l'ID : " + id);
        }

        Departement departement = departementExistant.get();
        Departement departementModifie = dtoConverterUtils.convertirDepartementDtoVersEntite(departementDtoModifie);

        validerDepartement(departementModifie, departement.getId());

        departement.setCode(departementModifie.getCode());
        departement.setNom(departementModifie.getNom());

        Departement departementSauve = departementRepository.save(departement);
        return new DepartementDto(departementSauve);
    }

    public void deleteDepartement(int id) throws ExceptionFonctionnelle{
        if (!departementRepository.existsById(id)) {
            throw new ExceptionFonctionnelle("Département non trouvé avec l'ID : " + id);
        }
        departementRepository.deleteById(id);
    }

    public List<VilleDto> getTopVillesByDepartement(int departementId, int limit) throws ExceptionFonctionnelle{
        if (departementRepository.findById(departementId).isEmpty()) {
            throw new ExceptionFonctionnelle("Département non trouvé avec l'ID : " + departementId);
        }
        return villeService.findTopVillesByDepartement(departementId, limit);
    }

    public List<VilleDto> getVillesByDepartementAndPopulationRange(int departementId, int minPopulation, int maxPopulation) throws ExceptionFonctionnelle{
        if (departementRepository.findById(departementId).isEmpty()) {
            throw new ExceptionFonctionnelle("Département non trouvé avec l'ID : " + departementId);
        }

        if (minPopulation < 0 || maxPopulation < 0) {
            throw new ExceptionFonctionnelle("Les valeurs de population doivent être positives");
        }

        if (minPopulation > maxPopulation) {
            throw new ExceptionFonctionnelle("La population minimum ne peut pas être supérieure à la population maximum");
        }

        return villeService.findVillesByDepartementAndPopulationBetween(departementId, minPopulation, maxPopulation);
    }


    // Méthode utilitaire pour convertir DTO vers entité


    private void validerDepartement(Departement departement, Integer idDepartementAExclure) throws ExceptionFonctionnelle {
        List<String> erreurs = new ArrayList<>();

        if (departement.getNom() == null || departement.getNom().length() < 3) {
            erreurs.add("Le nom du département doit contenir au moins 3 lettres.");
        }

        if (departement.getCode() == null || departement.getCode().length() != 2) {
            erreurs.add("Le code du département doit contenir exactement 2 caractères.");
        }

        if (erreurs.isEmpty()) {
            boolean codeExistant;
            if (idDepartementAExclure != null) {
                codeExistant = departementRepository.existsByCodeAndIdNot(departement.getCode(), idDepartementAExclure);
            } else {
                codeExistant = departementRepository.existsByCode(departement.getCode());
            }

            if (codeExistant) {
                erreurs.add("Un département avec ce code existe déjà.");
            }
        }

        if (!erreurs.isEmpty()) {
            String messageErreur = String.join("\n", erreurs);
            throw new ExceptionFonctionnelle(messageErreur);
        }
    }
}