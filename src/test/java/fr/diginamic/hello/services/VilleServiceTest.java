package fr.diginamic.hello.services;

import fr.diginamic.hello.dto.DepartementDto;
import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.exceptions.ExceptionFonctionnelle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class VilleServiceTest {


    @Autowired
    private VilleService villeService;

    @Test
    void testRecuperationVille() {
        Optional<VilleDto> villeOpt = villeService.extractVille("Bordeaux");
        assertTrue(villeOpt.isPresent(), "La ville Bordeaux doit être présente dans la base de test");

        VilleDto ville = villeOpt.get();
        assertEquals("33", ville.getDepartement().getCode());
        assertEquals("Bordeaux", ville.getNom());
    }

    @Test
    public void testVerifierCreationSansDept() {
        VilleDto dto = new VilleDto();
        dto.setNom("Châlons-en-Champagne");
        dto.setNbHabitants(50000);

        DepartementDto deptDto = new DepartementDto();
        deptDto.setCode(null);
        deptDto.setNom("Marne");
        dto.setDepartement(deptDto);

        ExceptionFonctionnelle exception = assertThrows(ExceptionFonctionnelle.class, () -> {
            villeService.insertVille(dto);
        });

        String message = exception.getMessage().toLowerCase();
        System.out.println("Message erreur : " + message);
        assertTrue(message.contains("département"), "Le message d'erreur doit mentionner 'département'");
    }
}