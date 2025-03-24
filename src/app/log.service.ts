import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  private apiUrl = 'http://localhost:8080/logs';  // URL to the Spring Boot logs endpoint

  constructor(private http: HttpClient) {}

  getLogs(): Observable<string[]> {
    return this.http.get<string[]>(this.apiUrl);
  }
}
