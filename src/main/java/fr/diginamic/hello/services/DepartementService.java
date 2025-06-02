package fr.diginamic.hello.services;


import fr.diginamic.hello.dao.DepartementDao;
import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementDao departementDao;

    public List<Departement> getAllDepartements() {
        return departementDao.findAll();
    }

    public Departement getDepartementById(int id) {
        return departementDao.findById(id);
    }

    public Departement getDepartementByCode(String code) {
        return departementDao.findByCode(code);
    }

    public Departement insertDepartement(Departement departement) {
        if (departementDao.existsByCode(departement.getCode())) {
            throw new IllegalArgumentException("Un département avec ce code existe déjà");
        }

        return departementDao.save(departement);
    }

    public List<Departement> modifierDepartement(int id, Departement departementModifie) {
        Departement departementExistant = departementDao.findById(id);
        if (departementExistant == null) {
            throw new IllegalArgumentException("Département non trouvé avec l'ID : " + id);
        }

        departementExistant.setCode(departementModifie.getCode());
        departementExistant.setNom(departementModifie.getNom());

        departementDao.save(departementExistant);
        return departementDao.findAll();
    }

    public void deleteDepartement(int id) {
        boolean supprime = departementDao.deleteById(id);
        if (!supprime) {
            throw new IllegalArgumentException("Département non trouvé avec l'ID : " + id);
        }
    }

    public List<Ville> getTopVillesByDepartement(int departementId, int limit) {
        Departement departement = departementDao.findById(departementId);
        if (departement == null) {
            throw new IllegalArgumentException("Département non trouvé avec l'ID : " + departementId);
        }

        return departementDao.findTopVillesByDepartement(departementId, limit);
    }

    public List<Ville> getVillesByDepartementAndPopulationRange(int departementId, int minPopulation, int maxPopulation) {
        Departement departement = departementDao.findById(departementId);
        if (departement == null) {
            throw new IllegalArgumentException("Département non trouvé avec l'ID : " + departementId);
        }

        if (minPopulation < 0 || maxPopulation < 0) {
            throw new IllegalArgumentException("Les valeurs de population doivent être positives");
        }

        if (minPopulation > maxPopulation) {
            throw new IllegalArgumentException("La population minimum ne peut pas être supérieure à la population maximum");
        }

        return departementDao.findVillesByDepartementAndPopulationRange(departementId, minPopulation, maxPopulation);
    }
}
