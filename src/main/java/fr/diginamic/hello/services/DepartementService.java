package fr.diginamic.hello.services;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello.repos.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private VilleService villeService;

    public List<Departement> getAllDepartements() {
        return departementRepository.findAll();
    }

    public Optional<Departement> getDepartementById(int id) {
        return departementRepository.findById(id);
    }

    public Optional<Departement> getDepartementByCode(String code) {
        return departementRepository.findByCode(code);
    }

    public Departement insertDepartement(Departement departement) throws ExceptionFonctionnelle{
        validerDepartement(departement, null);
        return departementRepository.save(departement);
    }

    public Departement modifierDepartement(int id, Departement departementModifie) throws ExceptionFonctionnelle{
        Optional<Departement> departementExistant = departementRepository.findById(id);
        if (departementExistant.isEmpty()) {
            throw new ExceptionFonctionnelle("Département non trouvé avec l'ID : " + id);
        }

        Departement departement = departementExistant.get();
        validerDepartement(departementModifie, departement.getId());

        departement.setCode(departementModifie.getCode());
        departement.setNom(departementModifie.getNom());

        return departementRepository.save(departement);
    }

    public void deleteDepartement(int id) throws ExceptionFonctionnelle{
        if (!departementRepository.existsById(id)) {
            throw new ExceptionFonctionnelle("Département non trouvé avec l'ID : " + id);
        }
        departementRepository.deleteById(id);
    }

    public List<Ville> getTopVillesByDepartement(int departementId, int limit) throws ExceptionFonctionnelle{
        if (departementRepository.findById(departementId).isEmpty()) {
            throw new ExceptionFonctionnelle("Département non trouvé avec l'ID : " + departementId);
        }
        return villeService.findTopVillesByDepartement(departementId, limit);
    }

    public List<Ville> getVillesByDepartementAndPopulationRange(int departementId, int minPopulation, int maxPopulation) throws ExceptionFonctionnelle{
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

    private void validerDepartement(Departement departement, Integer idDepartementAExclure) throws ExceptionFonctionnelle {
        List<String> erreurs = new ArrayList<>();

        if (departement.getNom() == null || departement.getNom().length() < 3) {
            erreurs.add("Le nom du département doit contenir au moins 3 lettres.");
        }

        boolean nomExistant = departementRepository.existsByNomIgnoreCase(departement.getNom());

        if (nomExistant) {
            erreurs.add("Un département avec ce nom existe déjà.");
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
            String messageErreur = String.join("\n", erreurs); // Séparateur par ligne
            throw new ExceptionFonctionnelle(messageErreur);
        }
    }

}
