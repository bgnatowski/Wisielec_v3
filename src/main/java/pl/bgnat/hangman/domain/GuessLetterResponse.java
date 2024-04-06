package pl.bgnat.hangman.domain;

import lombok.Builder;

@Builder
public record GuessLetterResponse(
		String category,
		String password,
		int wrongGuesses,
		boolean gameWon
) {
}
