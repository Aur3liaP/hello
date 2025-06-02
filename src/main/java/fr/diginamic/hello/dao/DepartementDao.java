package fr.diginamic.hello.dao;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DepartementDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Departement> findAllDepartement() {
        TypedQuery<Departement> query = entityManager.createQuery("SELECT d FROM Departement d", Departement.class);
        return query.getResultList();
    }

    @Transactional
    public Departement findDepartementById(int id) {
        return entityManager.find(Departement.class, id);
    }

    @Transactional
    public Departement findDepartementByNom(String nom) {
        TypedQuery<Departement> query = entityManager.createQuery("SELECT d FROM Departement d WHERE LOWER(d.nom) = LOWER(:nom)", Departement.class);
        query.setParameter("nom", nom);
        return query.getSingleResult();
    }

    public Departement findDepartementByCode(String code) {
            TypedQuery<Departement> query = entityManager.createQuery(
                    "SELECT d FROM Departement d WHERE d.code = :code", Departement.class);
            query.setParameter("code", code);
            return query.getSingleResult();
    }


    @Transactional
    public Departement saveDepartement(Departement departement) {
        if (departement.getId() == 0) {
            entityManager.persist(departement);
            return departement;
        } else {
            return entityManager.merge(departement);
        }
    }

    @Transactional
    public boolean deleteById(int id) {
        Departement departement = findDepartementById(id);
        if (departement != null) {
            entityManager.remove(departement);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean existsByCode(String code) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(d) FROM Departement d WHERE d.code = :code", Long.class);
        query.setParameter("code", code);
        return query.getSingleResult() > 0;
    }

    @Transactional
    public List<Ville> findTopVillesByDepartement(int departementId, int limit) {
        TypedQuery<Ville> query = entityManager.createQuery(
                "SELECT v FROM Ville v WHERE v.departement.id = :departementId ORDER BY v.nbHabitants DESC",
                Ville.class);
        query.setParameter("departementId", departementId);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional
    public List<Ville> findVillesByDepartementAndPopulationRange(int departementId, int minPopulation, int maxPopulation) {
        TypedQuery<Ville> query = entityManager.createQuery(
                "SELECT v FROM Ville v WHERE v.departement.id = :departementId " +
                        "AND v.nbHabitants >= :minPop AND v.nbHabitants <= :maxPop " +
                        "ORDER BY v.nbHabitants DESC",
                Ville.class);
        query.setParameter("departementId", departementId);
        query.setParameter("minPop", minPopulation);
        query.setParameter("maxPop", maxPopulation);
        return query.getResultList();
    }
}
