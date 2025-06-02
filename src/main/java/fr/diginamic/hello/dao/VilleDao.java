package fr.diginamic.hello.dao;

import fr.diginamic.hello.entities.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class VilleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Ville> findAllVille() {
        TypedQuery<Ville> query = entityManager.createQuery("SELECT v FROM Ville v", Ville.class);
        return query.getResultList();
    }

    @Transactional
    public Ville findVilleById(int id) {
        return entityManager.find(Ville.class, id);
    }

    @Transactional
    public Ville findVilleByNom(String nom) {
        TypedQuery<Ville> query = entityManager.createQuery("SELECT v FROM Ville v WHERE LOWER(v.nom) = LOWER(:nom)", Ville.class);
        query.setParameter("nom", nom);
        return query.getSingleResult();
    }

    @Transactional
    public Ville saveVille (Ville ville) {
        if (ville.getId() == 0) {
            entityManager.persist(ville);
            return ville;
        } else {
            return entityManager.merge(ville);
        }
    }

    @Transactional
    public boolean deleteVilleById(int id) {
        Ville ville = findVilleById(id);
        if (ville != null) {
            entityManager.remove(ville);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean existsByNom(String nom) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(v) FROM Ville v WHERE LOWER(v.nom) = LOWER(:nom)", Long.class);
        query.setParameter("nom", nom);
        return query.getSingleResult() > 0;
    }

    @Transactional
    public boolean existsById(int id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(v) FROM Ville v WHERE v.id = :id", Long.class);
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }

}
