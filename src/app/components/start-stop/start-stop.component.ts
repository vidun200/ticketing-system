import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-start-stop',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="start-stop">
      <button (click)="startSystem()" [disabled]="systemRunning || loading">Start</button>
      <button (click)="stopSystem()" [disabled]="!systemRunning || loading">Stop</button>
      <div *ngIf="loading" class="spinner">Loading...</div>
    </div>
    <p *ngIf="responseMessage" class="response-message">{{ responseMessage }}</p>
  `,
  styleUrls: ['./start-stop.component.css']
})
export class StartStopComponent {
  responseMessage: string | null = null;
  systemRunning: boolean = false; // Tracks system state
  loading: boolean = false; // Tracks loading state

  constructor(private http: HttpClient) {}

  /**
   * Starts the system by sending a POST request.
   */
  startSystem() {
    this.setLoading(true);
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    // Prepare the configuration object to send
    const config = {
      ticketReleaseRate: 10,  // Example positive values
      customerRetrievalRate: 5,
      maxTicketCapacity: 100,
      totalTickets: 50
    };

    this.http.post('http://localhost:8080/cli/start', config, { headers, responseType: 'text' })
      .subscribe({
        next: (response) => {
          this.responseMessage = response || "System started successfully."; // Handle empty responses
          this.systemRunning = true;
          this.setLoading(false);
        },
        error: (err) => {
          this.responseMessage = `Error starting system: ${err.message}`;
          this.setLoading(false);
        }
      });
  }

  /**
   * Stops the system by sending a POST request.
   */
  stopSystem() {
    this.setLoading(true);
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    this.http.post('http://localhost:8080/cli/stop', {}, { headers, responseType: 'text' })
      .subscribe({
        next: (response) => {
          this.responseMessage = response || "System stopped successfully."; // Handle empty responses
          this.systemRunning = false;
          this.setLoading(false);
        },
        error: (err) => {
          this.responseMessage = `Error stopping system: ${err.message}`;
          this.setLoading(false);
        }
      });
  }

  /**
   * Toggles the loading state.
   * @param state true to enable loading, false to disable.
   */
  private setLoading(state: boolean) {
    this.loading = state;
  }
}