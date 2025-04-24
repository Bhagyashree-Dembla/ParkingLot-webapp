# ParkingLot-webapp
The Parking Lot Management System is a Java-based web application designed to simulate and manage the functioning of a real-world parking lot. It enables efficient allocation, tracking, and billing for vehicles in a multi-slot, multi-floor parking environment.

🛠️ Features
Vehicle Entry & Exit

Automatically assigns an available parking slot based on vehicle type (car, bike, truck, etc.)

Issues parking tickets with entry time, slot number, and vehicle details

Calculates total time parked and generates a bill at exit

Slot Management

Supports different types of parking slots (e.g., Compact, Bike, Large)

Tracks available, occupied, and total slots dynamically

Multi-floor support for scalability

Pricing & Billing

Configurable hourly rate based on vehicle type

Rounds time to the nearest hour for fair billing

Generates total charges on exit with invoice details

-> Tech Stack

Java (Core)

Maven for dependency management and build

JUnit 5 for unit testing

Designed to be extendable for RESTful APIs or web UI

📦 Folder Structure (High-Level)
swift
Copy
Edit
src/
├── main/
│   ├── java/com/parkinglot/          <-- Main logic: models, services
│   ├── webapp/WEB-INF/               <-- web.xml (for web-based version)
│   └── resources/
└── test/java/com/parkinglot/         <-- JUnit tests

🧪 Test Cases
The system includes unit tests covering:

Parking and unparking vehicles

Slot availability under load

Billing calculations and edge cases

🚀 Use Cases
Urban smart parking systems

Commercial malls or building parking solutions

Backend service for a mobile parking app
