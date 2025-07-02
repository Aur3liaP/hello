package fr.diginamic.hello.controleurs;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import fr.diginamic.hello.dto.DepartementDto;
import fr.diginamic.hello.dto.VilleDto;
import fr.diginamic.hello.exceptions.ExceptionFonctionnelle;
import fr.diginamic.hello.services.DepartementService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departements")
public class DepartementControleur implements IDepartementControleur {

    @Autowired
    private DepartementService departementService;

    //GET
    @GetMapping
    @Override
    public ResponseEntity<List<DepartementDto>> getAllDepartements() {
        List<DepartementDto> departements = departementService.getAllDepartements();
        return ResponseEntity.ok(departements);
    }

    // GET par id
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getDepartementById(@PathVariable int id) {
        Optional<DepartementDto> departement = departementService.getDepartementById(id);
        if (departement.isPresent()) {
            return ResponseEntity.ok(departement.get());
        }
        return ResponseEntity.status(404).body("Departement non trouvé");

    }

    //GET par code
    @GetMapping("/code/{code}")
    @Override
    public ResponseEntity<?> getDepartementByCode(@PathVariable String code) {
        Optional<DepartementDto> departement = departementService.getDepartementByCode(code);
        if (departement.isPresent()) {
            return ResponseEntity.ok(departement.get());
        } else {
            return ResponseEntity.status(404).body("Departement non trouvé");
        }
    }

    //POST
    @PostMapping
    @Override
    public ResponseEntity<?> createDepartement(@RequestBody DepartementDto departement) throws ExceptionFonctionnelle {
        DepartementDto saved = departementService.insertDepartement(departement);
        return ResponseEntity.ok(saved);
    }

    //PUT
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> updateDepartement(@PathVariable int id, @RequestBody DepartementDto departementModifie) throws ExceptionFonctionnelle{
        DepartementDto updated = departementService.modifierDepartement(id, departementModifie);
        return ResponseEntity.ok(updated);
    }

    //DELETE
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> deleteDepartement(@PathVariable int id) throws ExceptionFonctionnelle{
        departementService.deleteDepartement(id);
        return ResponseEntity.ok("Département Supprimé");
    }

    // GET : villes les plus peuplées d’un département (avec limit)
    @GetMapping("/{id}/villes/top")
    @Override
    public ResponseEntity<?> getTopVilles(@PathVariable int id, @RequestParam(defaultValue = "5") int limit) throws ExceptionFonctionnelle{
        List<VilleDto> villes = departementService.getTopVillesByDepartement(id, limit);
        return ResponseEntity.ok(villes);
    }

    // GET : villes par tranche de population
    @GetMapping("/{id}/villes")
    @Override
    public ResponseEntity<?> getVillesByPopulationRange(@PathVariable int id, @RequestParam int min, @RequestParam int max) throws ExceptionFonctionnelle{
        List<VilleDto> villes = departementService.getVillesByDepartementAndPopulationRange(id, min, max);
        return ResponseEntity.ok(villes);
    }

    // Export PDF
    @GetMapping("/export/{code}")
    public void exportDepartement(@PathVariable String code, HttpServletResponse response) throws IOException,
            DocumentException, ExceptionFonctionnelle {
        DepartementDto departement = departementService.getDepartementByCode(code)
                .orElseThrow(() -> new ExceptionFonctionnelle("Département non trouvé avec le code : " + code));

        List<VilleDto> villes = departementService.getAllVillesByDepartementCode(code);

        response.setHeader("Content-Disposition", "attachment; filename=\"export_departement.pdf\"");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titreFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.DARK_GRAY);
        Paragraph titre = new Paragraph("Département : " + departement.getCode(), titreFont);
        titre.setAlignment(Element.ALIGN_CENTER);
        titre.setSpacingAfter(20f);
        document.add(titre);


        Font infoFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
        document.add(new Paragraph("Code département : " + departement.getCode(), infoFont));
        document.add(new Paragraph("Nom département : " + departement.getNom(), infoFont));
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 2});

        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell;

        cell = new PdfPCell(new Phrase("Nom de la ville", headerFont));
        cell.setBackgroundColor(BaseColor.GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Population", headerFont));
        cell.setBackgroundColor(BaseColor.GRAY);
        table.addCell(cell);

        Font rowFont = new Font(Font.FontFamily.HELVETICA, 12);
        for (VilleDto ville : villes) {
            table.addCell(new Phrase(ville.getNom(), rowFont));
            table.addCell(new Phrase(String.valueOf(ville.getNbHabitants()), rowFont));
        }

        document.add(table);
        document.close();
        response.flushBuffer();
    }
}
