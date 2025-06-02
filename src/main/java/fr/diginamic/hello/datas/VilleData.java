package fr.diginamic.hello.datas;

import fr.diginamic.hello.dao.DepartementDao;
import fr.diginamic.hello.entities.Departement;
import fr.diginamic.hello.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.diginamic.hello.dao.VilleDao;

@Component
public class VilleData implements CommandLineRunner {

    @Autowired
    private VilleDao villeDao;
    @Autowired
    private DepartementDao departementDao;


    @Override
    public void run(String... args) throws Exception {
        if (villeDao.findAll().isEmpty()) {
            Departement parisDept = new Departement("75", "Paris");
            Departement bouchesDuRhone = new Departement("13", "Bouches-du-Rhône");
            Departement rhone = new Departement("69", "Rhône");
            Departement hauteGaronne = new Departement("31", "Haute-Garonne");
            Departement alpesMaritimes = new Departement("06", "Alpes-Maritimes");
            Departement loireAtlantique = new Departement("44", "Loire-Atlantique");
            Departement herault = new Departement("34", "Hérault");
            Departement basRhin = new Departement("67", "Bas-Rhin");
            Departement gironde = new Departement("33", "Gironde");
            Departement nord = new Departement("59", "Nord");

            departementDao.save(parisDept);
            departementDao.save(bouchesDuRhone);
            departementDao.save(rhone);
            departementDao.save(hauteGaronne);
            departementDao.save(alpesMaritimes);
            departementDao.save(loireAtlantique);
            departementDao.save(herault);
            departementDao.save(basRhin);
            departementDao.save(gironde);
            departementDao.save(nord);

            villeDao.save(new Ville("Paris", 2161000, parisDept));
            villeDao.save(new Ville("Marseille", 861635, bouchesDuRhone));
            villeDao.save(new Ville("Lyon", 513275, rhone));
            villeDao.save(new Ville("Toulouse", 471941, hauteGaronne));
            villeDao.save(new Ville("Nice", 342522, alpesMaritimes));
            villeDao.save(new Ville("Nantes", 309346, loireAtlantique));
            villeDao.save(new Ville("Montpellier", 285121, herault));
            villeDao.save(new Ville("Strasbourg", 277270, basRhin));
            villeDao.save(new Ville("Bordeaux", 252040, gironde));
            villeDao.save(new Ville("Lille", 232741, nord));

            System.out.println("Base de données initialisée avec 10 villes et leurs départements.");
        }
    }
}
