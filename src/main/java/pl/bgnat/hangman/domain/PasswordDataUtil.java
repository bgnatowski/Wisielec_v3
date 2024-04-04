package pl.bgnat.hangman.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
class PasswordDataUtil implements CommandLineRunner {
	private final PasswordRepository passwordRepository;
	private final ObjectMapper objectMapper;

	private final static String data = """
			[
			  {"category": "zwierzęta", "password": "kot brytyjski"},
			  {"category": "zwierzęta", "password": "salamandra plamista"},
			  {"category": "zwierzęta", "password": "tukan"},
			  {"category": "zwierzęta", "password": "różowa pantera"},
			  {"category": "zwierzęta", "password": "pies berneński pasterski"},
			  {"category": "zwierzęta", "password": "sowa śnieżna"},
			  {"category": "zwierzęta", "password": "niedźwiedź polarny"},
			  {"category": "zwierzęta", "password": "papuga"},
			  {"category": "zwierzęta", "password": "miś koala"},
			  {"category": "zwierzęta", "password": "gepard"},
			  {"category": "potrawy", "password": "pierogi ruskie"},
			  {"category": "potrawy", "password": "spaghetti bolognese"},
			  {"category": "potrawy", "password": "rosół z kury"},
			  {"category": "potrawy", "password": "szakszuka"},
			  {"category": "potrawy", "password": "kebab na ostrym z baraniną"},
			  {"category": "potrawy", "password": "zupa z trupa"},
			  {"category": "potrawy", "password": "kluski śląskie"},
			  {"category": "potrawy", "password": "sernik na zimno"},
			  {"category": "potrawy", "password": "smoothie truskawkowe"},
			  {"category": "potrawy", "password": "makrela wędzona"},
			  {"category": "rośliny", "password": "róża"},
			  {"category": "rośliny", "password": "tulipan"},
			  {"category": "rośliny", "password": "modrzew"},
			  {"category": "rośliny", "password": "brzoza"},
			  {"category": "rośliny", "password": "chryzantema"},
			  {"category": "rośliny", "password": "storczyk"},
			  {"category": "rośliny", "password": "przebiśniegi"},
			  {"category": "rośliny", "password": "baobab afrykański"},
			  {"category": "rośliny", "password": "dąb Bartek"},
			  {"category": "rośliny", "password": "sekwoja wieczniezielona"},
			  {"category": "sport", "password": "skok w zwyż"},
			  {"category": "sport", "password": "siatkówka"},
			  {"category": "sport", "password": "hokej"},
			  {"category": "sport", "password": "skoki narciarskie"},
			  {"category": "sport", "password": "kajakarstwo"},
			  {"category": "sport", "password": "piłka nożna"},
			  {"category": "sport", "password": "wspinaczka górska"},
			  {"category": "sport", "password": "tenis ziemny"},
			  {"category": "sport", "password": "spadochroniarstwo"},
			  {"category": "sport", "password": "taekwondo"},
			  {"category": "muzyka", "password": "quebonafide zorza"},
			  {"category": "muzyka", "password": "taco hemingway leci nowy future"},
			  {"category": "muzyka", "password": "kartky dom na skraju niczego"},
			  {"category": "muzyka", "password": "lil peep life is beautiful"},
			  {"category": "muzyka", "password": "happysad taką wodą być"},
			  {"category": "muzyka", "password": "dżem wehikuł czasu"},
			  {"category": "muzyka", "password": "lady pank zawsze tam gdzie ty"},
			  {"category": "muzyka", "password": "bring me the horizon amo"},
			  {"category": "muzyka", "password": "meek oh why zachód"},
			  {"category": "muzyka", "password": "young igi każdy nowy dzień"},
			  {"category": "gry", "password": "wisielec"},
			  {"category": "gry", "password": "wiedźmin"},
			  {"category": "gry", "password": "pac man"},
			  {"category": "gry", "password": "mario"},
			  {"category": "gry", "password": "the last of us"},
			  {"category": "gry", "password": "grand theft auto"},
			  {"category": "gry", "password": "counter strike global offensive"},
			  {"category": "gry", "password": "league of legends"},
			  {"category": "gry", "password": "minecraft"},
			  {"category": "gry", "password": "the binding of isaac"},
			  {"category": "gry", "password": "fifa"},
			  {"category": "przysłowia", "password": "Bez pracy nie ma kołaczy"},
			  {"category": "przysłowia", "password": "Darowanemu koniowi w zęby się nie zagląda"},
			  {"category": "przysłowia", "password": "Fortuna kołem się toczy"},
			  {"category": "przysłowia", "password": "Nie chwal dnia przed zachodem słonca"},
			  {"category": "przysłowia", "password": "Lepszy wróbel w garści niż gołąb na dachu"},
			  {"category": "przysłowia", "password": "Apetyt rośnie w miarę jedzenia"},
			  {"category": "przysłowia", "password": "Co ma wisieć nie utonie"},
			  {"category": "przysłowia", "password": "Dzieci i ryby głosu nie mają"},
			  {"category": "przysłowia", "password": "Grosz do grosza, a będzie kokosza"},
			  {"category": "przysłowia", "password": "Gdzie kucharek sześć, tam nie ma co jeść"}
			]
			""";
	@Override
	public void run(String... args) throws Exception {
		System.out.printf(String.valueOf(passwordRepository.count()));
		if(passwordRepository.count()==0){
			List<Password> passwords = objectMapper.readValue(data, new TypeReference<>() {});
			passwords.stream()
					.map(password -> {
						password.setCategory(password.getCategory().toUpperCase());
						password.setPassword(password.getPassword().toUpperCase());
						return password;
					})
					.collect(Collectors.toList())
					.forEach(passwordRepository::save);
		}
	}
}
