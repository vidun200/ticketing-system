import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { interval, Subscription } from 'rxjs';

@Component({
  selector: 'app-terminal',
  templateUrl: './terminal.component.html',
  styleUrls: ['./terminal.component.css'],
})
export class TerminalComponent implements OnInit {
  messages: string[] = [];
  pollingSubscription!: Subscription;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    // Start periodic polling
    this.pollingSubscription = interval(2000).subscribe(() => {
      this.fetchLogs();
    });
  }

  fetchLogs() {
    this.http.get<string[]>('http://localhost:8080/cli/logs').subscribe(
      (data) => {
        this.messages = data;
      },
      (error) => {
        console.error('Error fetching logs:', error);
      }
    );
  }

  ngOnDestroy() {
    // Stop polling when the component is destroyed
    if (this.pollingSubscription) {
      this.pollingSubscription.unsubscribe();
    }
  }
}
