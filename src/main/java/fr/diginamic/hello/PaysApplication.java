package fr.diginamic.hello;

import fr.diginamic.hello.dto.PaysDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class PaysApplication implements CommandLineRunner {

	private static final String API_URL = "https://restcountries.com/v3.1/all";

	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(PaysApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("=== Démarrage de l'appel API ===");

		try {
			RestTemplate restTemplate = new RestTemplate();
			String jsonResponse = restTemplate.getForObject(API_URL, String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			PaysDto[] paysArray = objectMapper.readValue(jsonResponse, PaysDto[].class);
			List<PaysDto> pays = Arrays.asList(paysArray);

			System.out.println("Nombre de pays récupérés: " + pays.size());
			System.out.println("\n=== Premiers 10 pays ===");

			// Affichage des 10 premiers pays pour vérifier la cohérence
			pays.stream()
					.limit(10)
					.forEach(System.out::println);

		} catch (Exception e) {
			System.err.println("Erreur lors de l'appel API: " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("\n=== Fin du traitement ===");
	}
}
