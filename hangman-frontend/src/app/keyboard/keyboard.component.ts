// keyboard.component.ts

import {Component, HostListener, Input} from '@angular/core';
import {GameService} from "../service/game.service";
import {GuessLetterResponse} from "../interface/guess-letter-response";

@Component({
  selector: 'app-keyboard',
  templateUrl: './keyboard.component.html',
  styleUrls: ['./keyboard.component.css']
})
export class KeyboardComponent {
  @Input() gameId: number;
  private MAX_WRONG_GUESSES: number = 7;
  guessedLetters: { [key: string]: string } = {};
  letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZĄĆĘŁŃÓŚŹŻ'.split('');
  keyRows = [
    ['Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'],
    ['A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L'],
    ['Z', 'X', 'C', 'V', 'B', 'N', 'M'],
    ['Ą', 'Ć', 'Ę', 'Ł', 'Ń', 'Ó', 'Ś', 'Ź', 'Ż']
  ];
  gameEnded: boolean = false;

  constructor(private gameService: GameService) {
    this.gameService.gameEnded$.subscribe(ended => {
      this.gameEnded = ended;
    });
  }

  makeGuess(letter: string): void {
    this.gameService.guessLetter(this.gameId, letter).subscribe(
      (response: GuessLetterResponse) => {
        // console.log(response);
        const isGoodGuess = response.password.includes(letter);
        this.guessedLetters[letter] = isGoodGuess ? 'good-guess' : 'wrong-guess';

        this.gameService.updateObscuredPassword(response.password);

        if (!response.password.includes('-')) {
          this.gameService.updateImage("assets/wygrana.jpg");
          this.gameService.endGame();
        } else if (response.wrongGuesses == this.MAX_WRONG_GUESSES) {
          this.gameService.updateImage("assets/s8.jpg");
          this.gameService.endGame();
        } else {
          this.gameService.updateWrongGuesses(response.wrongGuesses);
          this.gameService.updateImage(`assets/s${response.wrongGuesses}.jpg`);
        }
      },
      (error: any) => {
        console.error('Error guessing letter:', error);
      }
    );
  }

  @HostListener('window:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    if(!this.gameEnded){
      const key = event.key.toUpperCase();
      if (this.letters.includes(key)) {
        this.makeGuess(key);
      }
    }
  }
}
