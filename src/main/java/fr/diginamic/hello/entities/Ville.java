package fr.diginamic.hello.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Ville {
    @Min(value = 1, message = "L'ID doit être strictement positif")
    private int id;

    @NotNull(message = "Le nom de la ville ne peut pas être nul")
    @Size(min = 2, message = "Le nom de la ville doit avoir au moins 2 caractères")
    private String nom;

    @Min(value = 1, message = "Le nombre d'habitants doit être supérieur ou égal à 1")
    private int nbHabitants;

    public Ville() {
    }

    public Ville(int id, String nom, int nbHabitants) {
        this.id = id;
        this.nom = nom;
        this.nbHabitants = nbHabitants;
    }

    public Ville(String nom, int nbHabitants) {
        this.nom = nom;
        this.nbHabitants = nbHabitants;
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
}
