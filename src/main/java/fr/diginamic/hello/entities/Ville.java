package fr.diginamic.hello.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ville")
public class Ville {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom", nullable = false)
    @NotNull(message = "Le nom de la ville ne peut pas être nul")
    @Size(min = 2, message = "Le nom de la ville doit avoir au moins 2 caractères")
    private String nom;

    @Column(name = "nb_habitants", nullable = false)
    @Min(value = 1, message = "Le nombre d'habitants doit être supérieur ou égal à 1")
    private int nbHabitants;

    @ManyToOne
    @JoinColumn(name = "departement_id", nullable = false)
    @NotNull(message = "Le département est obligatoire")
    @JsonBackReference
    private Departement departement;

    public Ville() {
    }

    public Ville(int id, String nom, int nbHabitants, Departement departement) {
        this.id = id;
        this.nom = nom;
        this.nbHabitants = nbHabitants;
        this.departement = departement;
    }

    public Ville(String nom, int nbHabitants, Departement departement) {
        this.nom = nom;
        this.nbHabitants = nbHabitants;
        this.departement = departement;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbHabitants() {
        return nbHabitants;
    }

    public void setNbHabitants(int nbHabitants) {
        this.nbHabitants = nbHabitants;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ville{");
        sb.append("id=").append(id);
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", nbHabitants=").append(nbHabitants);
        sb.append('}');
        return sb.toString();
    }
}
