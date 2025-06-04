package fr.diginamic.hello.dto;

import fr.diginamic.hello.entities.Ville;

public class VilleDto {
    private int id;
    private String nom;
    private int nbHabitants;
    private DepartementDto departement;

    public VilleDto() {}

    public VilleDto(int id, String nom, int nbHabitants, DepartementDto departement) {
        this.id = id;
        this.nom = nom;
        this.nbHabitants = nbHabitants;
        this.departement = departement;
    }

    public VilleDto(Ville ville) {
        if (ville != null) {
            this.id = ville.getId();
            this.nom = ville.getNom();
            this.nbHabitants = ville.getNbHabitants();

            if (ville.getDepartement() != null) {
                this.departement = new DepartementDto(ville.getDepartement());
            }
        }
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

    public DepartementDto getDepartement() {
        return departement;
    }

    public void setDepartement(DepartementDto departement) {
        this.departement = departement;
    }
}
