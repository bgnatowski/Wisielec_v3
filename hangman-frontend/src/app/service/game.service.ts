import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import {Game} from "../interface/game";
import {GuessLetterResponse} from "../interface/guess-letter-response"; // Załóżmy, że mamy plik środowiskowy

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private obscuredPasswordSubject = new BehaviorSubject<string>('');
  private categorySubject = new BehaviorSubject<string>('');
  private gameStateChanged = new BehaviorSubject<{ hasWon: boolean; isGameOver: boolean }>({ hasWon: false, isGameOver: false });
  private wrongGuessesSubject = new BehaviorSubject<number>(0);

  wrongGuessesChanged = this.wrongGuessesSubject.asObservable();
  hasWon: boolean = false;
  isGameOver: boolean = false;
  apiUrl = 'http://localhost:8080/api/games/hangman';
  private MAX_WRONG_GUESSES: number = 8;

  constructor(private http: HttpClient) {}

  startNewGame(): Observable<Game> {
    console.log("Starting new game");
    return this.http.post<Game>(`${this.apiUrl}/start`, {});
  }

  guessLetter(gameId: number, letter: string): void {
    const guessLetterRequest = { letter: letter };
    this.http.post<GuessLetterResponse>(`${this.apiUrl}/${gameId}/guess/letter`, guessLetterRequest)
      .subscribe(response => {
        this.obscuredPasswordSubject.next(response.obscuredPassword);
        this.categorySubject.next(response.category);
        this.wrongGuessesSubject.next(response.wrongGuesses);
        this.hasWon = !response.obscuredPassword.includes('-');
        this.isGameOver = response.wrongGuesses >= this.MAX_WRONG_GUESSES;
        this.gameStateChanged.next({ hasWon: this.hasWon, isGameOver: this.isGameOver });
      }, error => {
        console.error('An error occurred while guessing a letter:', error);
        // Tutaj można obsłużyć błąd, np. wyświetlając alert dla użytkownika.
      });
  }

  guessPassword(gameId: number, password: string): Observable<boolean> {
    const passwordRequest = { password: password };
    return this.http.post<boolean>(`${this.apiUrl}/${gameId}/guess/password`, passwordRequest);
  }


  getObscuredPassword(gameId: number): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/api/games/hangman/${gameId}`);
  }

  get obscuredPassword$(): Observable<string> {
    return this.obscuredPasswordSubject.asObservable();
  }

  get category$(): Observable<string> {
    return this.categorySubject.asObservable();
  }

  get gameStateChanged$(): Observable<{ hasWon: boolean; isGameOver: boolean }> {
    return this.gameStateChanged.asObservable();
  }
}
