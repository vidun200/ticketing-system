import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { interval } from 'rxjs';
import { CommonModule } from '@angular/common';  // Ensure CommonModule is imported

@Component({
  selector: 'app-log-display',
  standalone: true,
  imports: [CommonModule],  // Add CommonModule here
  templateUrl: './log-display.component.html',
  styleUrls: ['./log-display.component.css'],
})
export class LogDisplayComponent implements OnInit {
  logs: string[] = []; // Stores logs fetched from the backend
  private backendLogsEndpoint = 'http://localhost:8080/cli/logs'; // Adjust as per your backend URL

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    // Poll logs every 2 seconds
    interval(2000).subscribe(() => {
      this.fetchLogs();
    });
  }

  fetchLogs(): void {
    try {
      this.http.get<string[]>(this.backendLogsEndpoint).subscribe(
        (data) => {
          this.logs = data;
        },
        (error) => {
          // This error handler will catch any error from the HTTP request
          console.error('Error fetching logs:', error);
        }
      );
    } catch (error) {
      // This will handle any other potential synchronous errors
      console.error('Unexpected error:', error);
    }
  }
}