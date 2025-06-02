package fr.diginamic.hello.dao;

import fr.diginamic.hello.entities.Ville;
import org.springframework.stereotype.Repository;

@Repository
public class VilleDao extends AbstractDao<Ville, Integer>{

    @Override
    protected Class<Ville> getEntityClass() {
        return Ville.class;
    }

}
