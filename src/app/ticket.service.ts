import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private http: HttpClient) { }

  saveTicketConfig(ticketConfig: any): Observable<any> {
    return this.http.post('http://localhost:8080/ticket/save', ticketConfig);
  }
}