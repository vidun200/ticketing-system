# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.5/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.5/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.3.5/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.3.5/reference/data/sql.html#data.sql.jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.


Event Ticketing System

Introduction

The Event Ticketing System is a web-based application designed to manage ticket sales, configurations, and logs for event organizers. It includes an intuitive user interface for configuring system settings, starting and stopping ticketing operations, and viewing system logs. The system provides real-time updates and ensures seamless operation of ticketing activities.

Setup Instructions

Prerequisites

To run the application, ensure the following software is installed on your system:

Setup Instructions

Prerequisites

Backend (Spring Boot):

Java Development Kit (JDK) 17 or higher
Maven 3.8.0 or higher


Frontend (Angular):

Node.js 16 or higher
npm (comes with Node.js)
Angular CLI 15 or higher

Configuration File

Create a configuration JSON file (e.g., ticketconfig.json) with the following structure:

{
"maxTicketCapacity": 100,
"totalTickets": 50,
"ticketReleaseRate": 10,
"customerRetrievalRate": 5
}

Building and Running the Application

Frontend (Angular)

Navigate to the frontend directory.

Install dependencies:
   npm install

Start the development server:
    ng serve


Access the application at http://localhost:4200/.

Backend (Spring Boot)

Navigate to the backend directory.

Build the application:

    mvn clean install

Run the application:

     he backend server runs on http://localhost:8080/

Usage Instructions

System Configuration

Open the application in your browser (http://localhost:4200/).

Navigate to the System Configuration form.

Input configuration values or ensure the JSON file is correctly configured.

Max Ticket Capacity: Maximum number of tickets the system can manage.

Total Tickets: Initial number of tickets available.

Ticket Release Rate: Rate at which tickets are released.

Customer Retrieval Rate: Rate at which tickets are retrieved by customers.

Click Update Configuration to save changes.

Start and Stop System

Use the Start System button to initiate ticketing operations.

Use the Stop System button to halt operations.

Log Monitoring

View real-time logs in the Log Display section below the configuration form.

Logs are fetched from the backend at regular intervals.

Additional Notes

Ensure that the JSON configuration file is accessible to the backend application.

Customize the polling interval for logs in the frontend by modifying the setInterval function.

For any issues, consult the log display for debugging information.





