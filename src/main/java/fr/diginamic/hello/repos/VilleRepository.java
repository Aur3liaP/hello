package fr.diginamic.hello.repos;

import fr.diginamic.hello.entities.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Integer> {

    @EntityGraph(attributePaths = {"departement"})
    Optional<Ville> findByNomIgnoreCase(String nom);

    // Recherche par nom qui commence par une chaîne
    @EntityGraph(attributePaths = {"departement"})
    List<Ville> findByNomStartingWithIgnoreCase(String prefix);

    // Recherche par population supérieure à min (ordre décroissant)
    @EntityGraph(attributePaths = {"departement"})
    List<Ville> findByNbHabitantsGreaterThanOrderByNbHabitantsDesc(int min);

    // Recherche par population entre min et max (ordre décroissant)
    @EntityGraph(attributePaths = {"departement"})
    List<Ville> findByNbHabitantsBetweenOrderByNbHabitantsDesc(int min, int max);

    // Recherche par département et population supérieure à min
    @EntityGraph(attributePaths = {"departement"})
    List<Ville> findByDepartement_IdAndNbHabitantsGreaterThanOrderByNbHabitantsDesc(int departementId, int min);

    // Recherche par département et population entre min et max
    @EntityGraph(attributePaths = {"departement"})
    List<Ville> findByDepartement_IdAndNbHabitantsBetweenOrderByNbHabitantsDesc(int departementId, int min, int max);

    // Recherche des n villes les plus peuplées d'un département
    @EntityGraph(attributePaths = {"departement"})
    @Query("SELECT v FROM Ville v WHERE v.departement.id = :departementId ORDER BY v.nbHabitants DESC")
    List<Ville> findTopVillesByDepartement(@Param("departementId") int departementId, Pageable pageable);

    // Alternative avec méthode de convention de nommage
    @EntityGraph(attributePaths = {"departement"})
    List<Ville> findTop10ByDepartement_IdOrderByNbHabitantsDesc(int departementId);

    // Pour la pagination des toutes les villes
    @EntityGraph(attributePaths = {"departement"})
    Page<Ville> findAll(Pageable pageable);

    // Vérifier l'existence par nom
    boolean existsByNomIgnoreCase(String nom);

    // Vérifier par nom et par département
    boolean existsByNomIgnoreCaseAndDepartementId(String nom, int departementId);

    boolean existsByNomIgnoreCaseAndDepartementIdAndIdNot(String nom, int departementId, int villeId);

    @EntityGraph(attributePaths = {"departement"})
    List<Ville> findVillesByDepartement_Code(@Param("departementCode") String departementCode);

}
