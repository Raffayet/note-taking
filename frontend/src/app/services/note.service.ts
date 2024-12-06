import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Note } from '../model/note';

@Injectable({
  providedIn: 'root',
})
export class NoteService {
  private apiUrl = `${environment.apiBaseUrl}/notes`;

  constructor(private http: HttpClient) {}

  getNotes(): Observable<Note[]> {
    console.log(this.apiUrl);
    return this.http.get<Note[]>(this.apiUrl);
  }
}
