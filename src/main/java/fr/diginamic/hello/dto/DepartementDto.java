package fr.diginamic.hello.dto;

import fr.diginamic.hello.entities.Departement;

public class DepartementDto {
    private int id;
    private String code;
    private String nom;

    public DepartementDto() {}

    public DepartementDto(int id, String code, String nom) {
        this.id = id;
        this.code = code;
        this.nom = nom;
    }

    public DepartementDto(Departement departement) {
        if (departement != null) {
            this.id = departement.getId();
            this.code = departement.getCode();
            this.nom = departement.getNom();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
