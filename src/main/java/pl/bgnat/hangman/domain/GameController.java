package pl.bgnat.hangman.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/games/hangman")
@RequiredArgsConstructor
class GameController {
	private final GameService gameService;

	@PostMapping("/start")
	public Game startNewGame() {
		return gameService.startNewGame();
	}

	@PostMapping("/{gameId}/guess/letter")
	public GuessLetterResponse guessLetter(@PathVariable Long gameId, @RequestBody GuessLetterRequest guessLetterRequest) {
		return gameService.guessLetter(gameId, guessLetterRequest.letter());
	}

	@PostMapping( "/{gameId}/guess/password")
	public boolean guessPassword(@PathVariable Long gameId, @RequestBody PasswordRequest passwordRequest) {
		return gameService.guessPassword(gameId, passwordRequest.password());
	}

	@GetMapping("/{gameId}")
	public String getObscuredPassword(@PathVariable Long gameId){
		return gameService.getObscuredPassword(gameId);
	}
}
