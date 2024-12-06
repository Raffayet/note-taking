import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { LoginDto } from '../model/loginDto';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private apiUrl = `${environment.apiBaseUrl}/login`;

  constructor(private http: HttpClient) {}

  logIn(email: string, password: string, success: boolean): Observable<any> {
    let credentials: LoginDto = { email: email, password: password };
    return this.http.post(this.apiUrl, credentials);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('user');
  }

  saveToken(token: string): void {
    localStorage.setItem('jwtToken', token);
  }

  getToken(): string | null {
    return localStorage.getItem('jwtToken');
  }

  logout(): void {
    localStorage.removeItem('jwtToken');
  }
}
