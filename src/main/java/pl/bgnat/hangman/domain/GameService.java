package pl.bgnat.hangman.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
class GameService {
	private final GameRepository gameRepository;
	private final Random random = new Random();
	private final PasswordRepository passwordRepository;

	public Game startNewGame() {
		Game newGame = new Game();
		long count = passwordRepository.count();
		Password password = passwordRepository.findById(random.nextLong(count + 1))
				.orElseThrow(() -> new RuntimeException("nie znaleziono hasła"));
		newGame.setPassword(password.getPassword().toUpperCase());
		newGame.setCategory(password.getCategory().toUpperCase());
		newGame.setObscuredPassword(newGame.getPassword().replaceAll(".", "-"));
		newGame.setWrongGuesses(0);
		return gameRepository.save(newGame);
	}

	@Transactional
	public GuessLetterResponse guessLetter(Long gameId, char letter) {
		Game game = gameRepository.findById(gameId).orElseThrow();

		if (game.getWrongGuesses() > 7){
			return GuessLetterResponse.builder()
					.password(game.getPassword())
					.category(game.getCategory())
					.wrongGuesses(game.getWrongGuesses())
					.build();
		}

		String password = game.getPassword().toUpperCase();
		char upperLetter = Character.toUpperCase(letter);
		boolean isCorrect = password.contains(String.valueOf(upperLetter));
		StringBuilder newObscuredPassword = new StringBuilder(game.getObscuredPassword());

		if (isCorrect) {
			for (int i = 0; i < password.length(); i++) {
				if (password.charAt(i) == upperLetter) {
					newObscuredPassword.setCharAt(i, upperLetter);
				}
			}
			game.setObscuredPassword(newObscuredPassword.toString());
		} else {
			game.setWrongGuesses(game.getWrongGuesses() + 1);
		}

		return GuessLetterResponse.builder()
				.password(newObscuredPassword.toString())
				.category(game.getCategory())
				.wrongGuesses(game.getWrongGuesses())
				.build();
	}

	public boolean guessPassword(Long gameId, String attempt) {
		Game game = gameRepository.findById(gameId).orElseThrow();
		boolean isCorrect = game.getPassword().equalsIgnoreCase(attempt);
		if (isCorrect) {
			game.setObscuredPassword(game.getPassword());
			gameRepository.save(game);
		}
		return isCorrect;
	}

	public String getObscuredPassword(Long gameId) {
		Game game = gameRepository.findById(gameId).orElseThrow();
		return game.getObscuredPassword();
	}
}
