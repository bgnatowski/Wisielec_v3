export interface GuessLetterResponse {
  category: string,
  password: string,
  wrongGuesses: number,
  gameWon: boolean
}
