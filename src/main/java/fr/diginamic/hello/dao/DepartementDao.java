package fr.diginamic.hello.dao;

import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DepartementDao extends AbstractDao<Departement, Integer> {

    @Override
    protected Class<Departement> getEntityClass() {
        return Departement.class;
    }


    public Departement findByCode(String code) {
            TypedQuery<Departement> query = entityManager.createQuery(
                    "SELECT d FROM Departement d WHERE d.code = :code", Departement.class);
            query.setParameter("code", code);
            return query.getSingleResult();
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
