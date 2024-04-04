package pl.bgnat.hangman.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
		"pl.bgnat"
})
public class GameConfig {
	@Bean
	public GameService gameService(GameRepository gameRepository,
								   PasswordRepository passwordRepository){
		return new GameService(gameRepository, passwordRepository);
	}

	@Bean
	public GameController gameController(GameService gameService){
		return new GameController(gameService);
	}
}
