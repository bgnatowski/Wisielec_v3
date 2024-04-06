import {Component, Input} from '@angular/core';
import {GameService} from "../service/game.service";

@Component({
  selector: 'app-keyboard',
  template: `
    <div class="keyboard">
      <button type="button" class="btn btn-primary" *ngFor="let letter of letters" (click)="makeGuess(letter)">
        {{ letter }}
      </button>
    </div>
  `,
  styleUrls: ['./keyboard.component.css']
})
export class KeyboardComponent {
  letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZĄĆĘŁŃÓŚŹŻ'.split('');
  @Input() gameId: number;

  constructor(private gameService: GameService) {}

  makeGuess(letter: string) {
    if (this.gameId) {
      this.gameService.guessLetter(this.gameId, letter);
    } else {
      console.error('No game ID provided to KeyboardComponent');
    }
  }
}
