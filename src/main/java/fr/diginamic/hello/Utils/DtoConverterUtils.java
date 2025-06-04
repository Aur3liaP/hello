package fr.diginamic.hello.Utils;

import fr.diginamic.hello.dto.DepartementDto;
import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import fr.diginamic.hello.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello.repos.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DtoConverterUtils {

    @Autowired
    private DepartementRepository departementRepository;

    public Departement convertirDepartementDtoVersEntite(DepartementDto departementDto) {
        Departement departement = new Departement();
        departement.setCode(departementDto.getCode());
        departement.setNom(departementDto.getNom());
        return departement;
    }

    public Ville convertirVilleDtoVersEntite(VilleDto villeDto) throws ExceptionFonctionnelle {
        Ville ville = new Ville();
        ville.setNom(villeDto.getNom());
        ville.setNbHabitants(villeDto.getNbHabitants());

        if (villeDto.getDepartement() != null && villeDto.getDepartement().getId() > 0) {
            Departement departement = departementRepository.findById(villeDto.getDepartement().getId())
                    .orElseThrow(() -> new ExceptionFonctionnelle("Département non trouvé avec l'ID : " + villeDto.getDepartement().getId()));
            ville.setDepartement(departement);
        } else {
            throw new ExceptionFonctionnelle("Un département valide doit être spécifié");
        }

        return ville;
    }
}

