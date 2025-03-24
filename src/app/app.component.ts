import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subscription, interval } from 'rxjs';
import { CommonModule } from '@angular/common';  // Import CommonModule for *ngIf
import { LogDisplayComponent } from './log-display/log-display.component';  // Import LogDisplayComponent
import { HttpClientModule } from '@angular/common/http';
import { ConfigPageComponent } from "./components/config-page/config-page.component";
import { StartStopComponent } from "./components/start-stop/start-stop.component";
import { TotalTicketComponent } from "./components/total-ticket/total-ticket.component";  // Import HttpClientModule for HTTP requests

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule, // Include CommonModule for *ngIf and other directives
    HttpClientModule, // For making HTTP requests
    LogDisplayComponent // Include LogDisplayComponent here
    ,
    ConfigPageComponent,
    StartStopComponent,
    TotalTicketComponent
],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'Event Ticketing System';
  logs: string[] = [];
  systemConfig = {
    maxTicketCapacity: '',
    totalTickets: '',
    ticketReleaseRate: '',
    customerRetrievalRate: ''
  };
  pollingSubscription: Subscription | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    console.log('App initialized.');
  }

  updateConfig() {
    this.http.post('http://localhost:8080/cli/logs', this.systemConfig)
      .subscribe(
        () => {
          console.log('Configuration updated');
        },
        (error) => {
          console.error('Error updating configuration:', error);
        }
      );
  }

  startSystem() {
    this.http.post('http://localhost:8080/cli/logst', {}).subscribe(
      () => {
        console.log('System started');
        this.startPolling();
      },
      (error) => {
        console.error('Error starting system:', error);
      }
    );
  }

  stopSystem() {
    this.http.post('http://localhost:8080/cli/logs', {}).subscribe(
      () => {
        console.log('System stopped');
        this.stopPolling();
      },
      (error) => {
        console.error('Error stopping system:', error);
      }
    );
  }

  startPolling() {
    this.pollingSubscription = interval(1000).subscribe(() => {
      this.http.get<string[]>('http://localhost:8080/cli/logs').subscribe(
        (data) => {
          this.logs = [...this.logs, ...data.filter(log => !this.logs.includes(log))];
        },
        (error) => {
          console.error('Error fetching logs:', error);
        }
      );
    });
  }

  stopPolling() {
    if (this.pollingSubscription) {
      this.pollingSubscription.unsubscribe();
      this.pollingSubscription = null;
    }
  }

  ngOnDestroy() {
    this.stopPolling();
  }
}