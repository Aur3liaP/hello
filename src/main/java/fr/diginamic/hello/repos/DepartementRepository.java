package fr.diginamic.hello.repos;

import fr.diginamic.hello.entities.Departement;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    @EntityGraph(attributePaths = {"villes"})
    Optional<Departement> findByCode(String code);

    @EntityGraph(attributePaths = {"villes"})
    Optional<Departement> findByNomIgnoreCase(String nom);

    @EntityGraph(attributePaths = {"villes"})
    boolean existsByCode(String code);

    boolean existsByNomIgnoreCase(String nom);

    boolean existsByCodeAndIdNot(String code, int id);

}
