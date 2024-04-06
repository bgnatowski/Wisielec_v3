// game.component.ts

import {Component, OnInit} from '@angular/core';
import {GameService} from '../service/game.service';
import {Game} from "../interface/game";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {
  game: Game;
  imageSrc: string

  constructor(private gameService: GameService) {
  }

  ngOnInit() {
    this.gameService.startNewGame().subscribe(newGame => {
      this.game = newGame;
      this.gameService.updateObscuredPassword(newGame.obscuredPassword)
      this.subscribeToGameUpdates();
    });
  }

  subscribeToGameUpdates() {
    this.gameService.obscuredPassword$.subscribe(password => {
      this.game.obscuredPassword = password;
    });

    this.gameService.gameImage$.subscribe(imageSrc => {
      this.imageSrc = imageSrc;
    });
  }
}
