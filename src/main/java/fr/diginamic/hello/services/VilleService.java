package fr.diginamic.hello.services;

import fr.diginamic.hello.dao.VilleDao;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.repos.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VilleService {

    @Autowired
    private VilleRepository villeRepository;

    public List<Ville> extractVilles() {
        return villeRepository.findAll();
    }

    public Optional<Ville> extractVille(int id) {
        return villeRepository.findById(id);
    }

    public Optional<Ville> extractVille(String nom) {
        return villeRepository.findByNomIgnoreCase(nom);
    }

    public Ville insertVille(Ville ville) {
        if (ville.getId() != 0 && villeRepository.existsById(ville.getId())) {
            throw new IllegalArgumentException("Une ville avec cet Id existe déjà");
        }

        return villeRepository.save(ville);
    }

    public Ville modifierVille(int id, Ville villeModifiee) {

        Optional<Ville> villeExiste = villeRepository.findById(id);
        if (villeExiste.isEmpty()) {
            throw new IllegalArgumentException("Ville non trouvée avec l'ID : " + id);
        }

        Ville ville = villeExiste.get();
        ville.setNom(villeModifiee.getNom());
        ville.setNbHabitants(villeModifiee.getNbHabitants());
        ville.setDepartement(villeModifiee.getDepartement());

        return villeRepository.save(ville);
    }

    public void supprimerVille(int id) {
        if (!villeRepository.existsById(id)) {
            throw new IllegalArgumentException("Ville non trouvée avec l'ID : " + id);
        }
        villeRepository.deleteById(id);
    }


    // Nvll méthodes

    public Page<Ville> extractVillesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return villeRepository.findAll(pageable);
    }

    public List<Ville> findVillesStartingWith(String prefix) {
        return villeRepository.findByNomStartingWithIgnoreCase(prefix);
    }

    public List<Ville> findVillesWithPopulationGreaterThan(int min) {
        return villeRepository.findByNbHabitantsGreaterThanOrderByNbHabitantsDesc(min);
    }

    public List<Ville> findVillesWithPopulationBetween(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("La population minimum ne peut pas être supérieure à la population maximum");
        }
        return villeRepository.findByNbHabitantsBetweenOrderByNbHabitantsDesc(min, max);
    }

    public List<Ville> findVillesByDepartementAndPopulationGreaterThan(int departementId, int min) {
        return villeRepository.findByDepartement_IdAndNbHabitantsGreaterThanOrderByNbHabitantsDesc(departementId, min);
    }

    public List<Ville> findVillesByDepartementAndPopulationBetween(int departementId, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("La population minimum ne peut pas être supérieure à la population maximum");
        }
        return villeRepository.findByDepartement_IdAndNbHabitantsBetweenOrderByNbHabitantsDesc(departementId, min, max);
    }

    public List<Ville> findTopVillesByDepartement(int departementId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return villeRepository.findTopVillesByDepartement(departementId, pageable);
    }

}
