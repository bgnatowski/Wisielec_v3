import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import {Game} from "../interface/game";
import {GuessLetterResponse} from "../interface/guess-letter-response"; // Załóżmy, że mamy plik środowiskowy

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private apiUrl = '/api/games/hangman';

  private obscuredPasswordSubject = new BehaviorSubject<string>('');
  private wrongGuessesSubject = new BehaviorSubject<number>(0);
  private gameImageSubject = new BehaviorSubject<string>("assets/s0.jpg");

  private gameEndedSubject = new Subject<boolean>();
  public gameImage$ = this.gameImageSubject.asObservable();
  public gameEnded$ = this.gameEndedSubject.asObservable();

  constructor(private http: HttpClient) {}

  startNewGame(): Observable<Game> {
    // console.log("Starting new game");
    return this.http.post<Game>(`${this.apiUrl}/start`, {});
  }

  guessLetter(gameId: number, letter: string): Observable<GuessLetterResponse> {
    // console.log("Guessing letter: " + letter + " for gameId: " + gameId);
    const guessLetterRequest = { letter: letter };
    return this.http.post<GuessLetterResponse>(`${this.apiUrl}/${gameId}/guess/letter`, guessLetterRequest);
  }

  get obscuredPassword$(): Observable<string> {
    return this.obscuredPasswordSubject.asObservable();
  }

  updateObscuredPassword(obscuredPassword: string): void {
    this.obscuredPasswordSubject.next(obscuredPassword);
  }

  updateWrongGuesses(wrongGuesses: number): void {
    this.wrongGuessesSubject.next(wrongGuesses);
  }

  updateImage(imageSrc: string): void {
    this.gameImageSubject.next(imageSrc);
  }

  endGame() {
    this.gameEndedSubject.next(true);
  }
}
