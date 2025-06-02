package fr.diginamic.hello.services;

import fr.diginamic.hello.dao.VilleDao;
import fr.diginamic.hello.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {

    @Autowired
    private VilleDao villeDao;

    public List<Ville> extractVilles() {
        return villeDao.findAllVille();
    }

    public Ville extractVille(int id) {
        return villeDao.findVilleById(id);
    }

    public Ville extractVille(String nom) {
        return villeDao.findVilleByNom(nom);
    }

    public List<Ville> insertVille(Ville ville) {
        if (villeDao.existsById(ville.getId())) {
            throw new IllegalArgumentException("Une ville avec cet Id existe déjà");
        }

        villeDao.saveVille(ville);
        return villeDao.findAllVille();
    }

    public List<Ville> modifierVille(int id, Ville villeModifiee) {

        Ville villeExiste = villeDao.findVilleById(id);
        if (villeExiste == null) {
            throw new IllegalArgumentException("Ville non trouvée avec l'ID : " + id);
        }

        villeExiste.setNom(villeModifiee.getNom());
        villeExiste.setNbHabitants(villeModifiee.getNbHabitants());
        villeExiste.setDepartement(villeModifiee.getDepartement());

        villeDao.saveVille(villeExiste);
        return villeDao.findAllVille();
    }

    public List<Ville> supprimerVille(int id) {
        boolean villeExiste = villeDao.deleteVilleById(id);
        if (!villeExiste) {
            throw new IllegalArgumentException("Ville non trouvée avec l'ID : " + id);
        }
        return villeDao.findAllVille();
    }

}
