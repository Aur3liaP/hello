package fr.diginamic.hello.services;


import fr.diginamic.hello.dao.DepartementDao;
import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.repos.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Departement insertDepartement(Departement departement) {
        if (departementRepository.existsByCode(departement.getCode())) {
            throw new IllegalArgumentException("Un département avec ce code existe déjà");
        }

        return departementRepository.save(departement);
    }

    public Departement modifierDepartement(int id, Departement departementModifie) {
        Optional<Departement> departementExistant = departementRepository.findById(id);
        if (departementExistant.isEmpty()) {
            throw new IllegalArgumentException("Département non trouvé avec l'ID : " + id);
        }

        Departement departement = departementExistant.get();
        departement.setCode(departementModifie.getCode());
        departement.setNom(departementModifie.getNom());

        return departementRepository.save(departement);
    }

    public void deleteDepartement(int id) {
        if (!departementRepository.existsById(id)) {
            throw new IllegalArgumentException("Département non trouvé avec l'ID : " + id);
        }
        departementRepository.deleteById(id);
    }

    public List<Ville> getTopVillesByDepartement(int departementId, int limit) {
        if (departementRepository.findById(departementId).isEmpty()) {
            throw new IllegalArgumentException("Département non trouvé avec l'ID : " + departementId);
        }
        return villeService.findTopVillesByDepartement(departementId, limit);
    }

    public List<Ville> getVillesByDepartementAndPopulationRange(int departementId, int minPopulation, int maxPopulation) {
        if (departementRepository.findById(departementId).isEmpty()) {
            throw new IllegalArgumentException("Département non trouvé avec l'ID : " + departementId);
        }

        if (minPopulation < 0 || maxPopulation < 0) {
            throw new IllegalArgumentException("Les valeurs de population doivent être positives");
        }

        if (minPopulation > maxPopulation) {
            throw new IllegalArgumentException("La population minimum ne peut pas être supérieure à la population maximum");
        }

        return villeService.findVillesByDepartementAndPopulationBetween(departementId, minPopulation, maxPopulation);
    }
}
