// game.component.ts

import { Component, OnInit } from '@angular/core';
import { GameService } from '../service/game.service';
import {Game} from "../interface/game";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {
  game: Game;
  imageSrc: string = 'assets/s0.jpg';

  constructor(private gameService: GameService) {}

  ngOnInit() {
    this.gameService.startNewGame().subscribe(
      newGame => {
        this.game = newGame;
        console.log(this.game);
        this.imageSrc = 'assets/s0.jpg'; // Możesz zaktualizować obrazek na podstawie odpowiedzi
      },
      error => {
        console.error('Error starting new game:', error);
      }
    );
  }
}
