package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.entities.Ville;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @GetMapping
    public List<Ville> getVilles() {
        List<Ville> villes = new ArrayList<>();

        villes.add(new Ville("Paris", 2161000));
        villes.add(new Ville("Marseille", 861635));
        villes.add(new Ville("Lyon", 513275));
        villes.add(new Ville("Toulouse", 471941));
        villes.add(new Ville("Nice", 342522));
        villes.add(new Ville("Nantes", 309346));
        villes.add(new Ville("Montpellier", 285121));
        villes.add(new Ville("Strasbourg", 277270));
        villes.add(new Ville("Bordeaux", 252040));
        villes.add(new Ville("Lille", 232741));

        return villes;
    }
}
