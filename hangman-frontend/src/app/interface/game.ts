
export interface Game {
  id: number;
  category: string;
  password: string;
  obscuredPassword: string;
  wrongGuesses: number;
}
