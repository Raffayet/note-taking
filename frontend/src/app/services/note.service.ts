import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Note } from '../model/note';
import { PageRequest } from '../model/pageRequest';

@Injectable({
  providedIn: 'root',
})
export class NoteService {
  private apiUrl = `${environment.apiBaseUrl}/notes`;

  constructor(private http: HttpClient) {}

  getNotes(pageRequest: PageRequest): Observable<Note[]> {
    let queryParams = new HttpParams()
      .append('format', 'json')
      .append('page', pageRequest.page.toString())
      .append('size', pageRequest.size.toString());

    return this.http.get<Note[]>(this.apiUrl, { params: queryParams });
  }

  deleteNote(noteId: string) {
    return this.http.delete(`${this.apiUrl}/${noteId}`);
  }

  createNote(note: Note): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, note);
  }

  updateNote(noteId: string, note: Note): Observable<any> {
    return this.http.put(`${this.apiUrl}/${noteId}`, note);
  }
}
