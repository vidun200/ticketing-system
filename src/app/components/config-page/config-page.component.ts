import { Component } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http'; // Import HttpClient
import { FormsModule } from '@angular/forms'; // Import FormsModule

@Component({
  selector: 'app-config-page',
  standalone: true,
  imports: [FormsModule, HttpClientModule], // Ensure HttpClientModule is imported
  templateUrl: './config-page.component.html',
  styleUrls: ['./config-page.component.css'],
})
export class ConfigPageComponent {
stopSystem() {
throw new Error('Method not implemented.');
}
isSystemRunning: any;
startSystem() {
throw new Error('Method not implemented.');
}
  maxTicketCapacity: string = "";  // Make sure this is initialized properly
  totalTickets: string = "";
  ticketReleaseRate: string = "";
  customerRetrievalRate: string = "";
  

  constructor(private http: HttpClient) {}

  // Method to update and send the form data to the backend
  updateConfig() {
    console.log('Configuration Updated:');
    const ticketConfig = {
      maxTicketCapacity: this.maxTicketCapacity,
      totalTickets: this.totalTickets,
      ticketReleaseRate: this.ticketReleaseRate,
      customerRetrievalRate: this.customerRetrievalRate,
    };

    this.http.post('http://localhost:8080/ticket/save', ticketConfig, {
      headers: { 'Content-Type': 'application/json' },
      responseType: 'text',
    }).subscribe(
      (response) => {
        console.log('Response:', response);
        alert('Configuration saved successfully!');
      },
      (error) => {
        console.error('Error:', error);
        if (error.status === 0) {
          alert('Failed to connect to the backend. Please check your server.');
        } else if (error.error) {
          alert(`Error: ${error.error}`);
        } else {
          alert('Failed to save configuration. Please try again later.');
        }
      }
    );
  }

  // Method to load the configuration data from the backend
  loadConfig() {
    this.http.get<any>('http://localhost:8080/ticket/load').subscribe(
      (data) => {
        console.log('Loaded Data:', data);
        // Ensure proper assignment of properties to avoid undefined or null
        this.maxTicketCapacity = data.maxTicketCapacity || "";
        this.totalTickets = data.totalTickets || "";
        this.ticketReleaseRate = data.ticketReleaseRate || "";
        this.customerRetrievalRate = data.customerRetrievalRate || "";
        alert('Configuration loaded successfully!');
      },
      (error) => {
        console.error('Error:', error);
        alert('Failed to load configuration. Please try again later.');
      }
    );
  }
}