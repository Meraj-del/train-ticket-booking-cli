Train Ticket Booking System — CLI Application

A command-line IRCTC-style train ticket booking system built in Java. Supports user registration, login, train search, seat booking, ticket cancellation, and booking history — all persisted locally via JSON flat-file storage.

Features

User signup and login with BCrypt password hashing
Search trains by source and destination
View available seats in real time
Book tickets with automatic seat allocation
Cancel booked tickets
View all bookings for logged-in user
JSON-based local persistence — no database required


Architecture
CLI (App.java)
     ↓
UserBookingService          TrainService
     ↓                           ↓
user.json (flat file)     trains.json (flat file)
Components
Entities

User — stores userId, name, hashed password, list of booked tickets
Train — stores trainId, trainNo, seat matrix (2D list), station list, station times
Ticket — stores ticketId, userId, source, destination, travel date, train snapshot

Services

UserBookingService — handles signup, login, ticket booking, cancellation, booking history
TrainService — handles train loading, seat availability search, seat booking and persistence

Utility

UserServiceUtil — BCrypt password hashing and verification

Storage

trains.json — train data with seat matrix and station schedules
user.json — user data with embedded ticket history


Tech Stack
LayerTechnologyLanguageJava 21Build ToolGradleJSON ParsingJackson Databind 2.18.2Password HashingjBCrypt 0.4StorageJSON flat files (local filesystem)

Data Model
Train JSON Structure
json{
  "train_id": "bacs1",
  "train_no": "12345",
  "seats": [[1,0,0,0,0,0],[0,0,0,0,0,0],[0,0,0,0,0,0],[0,0,0,0,0,0]],
  "stations": ["Bangalore", "Jaipur", "Delhi"],
  "station_times": {
    "Bangalore": "13:50:00",
    "Jaipur": "15:50:00",
    "Delhi": "18:50:00"
  }
}
Seat matrix uses 0 = available, 1 = booked. The booking engine scans row by row and allocates the first available seat.
User JSON Structure
json{
  "user_id": "uuid-here",
  "name": "Meraj",
  "hashed_password": "$2a$10$...",
  "tickets_booked": []
}
Ticket JSON Structure
json{
  "ticketId": "uuid-here",
  "userId": "uuid-here",
  "source": "Bangalore",
  "destination": "Delhi",
  "dateOfTravel": 1768916279279,
  "train": { ... }
}

How to Run
Prerequisites

Java 21
Gradle

Setup
1. Clone the repository
bashgit clone https://github.com/Meraj-del/train-ticket-booking-cli.git
cd train-ticket-booking-cli
2. Build the project
bash./gradlew build
3. Run the application
bash./gradlew run

CLI Menu Flow
=== Train Ticket Booking System ===
1. Sign Up
2. Login
3. Exit

After login:
1. Search Trains
2. Book Ticket
3. Cancel Ticket
4. View Bookings
5. Logout

Sample Trains (Pre-loaded)
Train IDTrain NoRouteDeparturebacs112345Bangalore → Jaipur → Delhi13:50bacs212346Mumbai → Delhi14:00

Key Design Decisions
BCrypt password hashing — passwords are never stored in plaintext. jBCrypt hashes on signup and verifies on login without ever decrypting.
Train snapshot in ticket — each ticket stores a snapshot of the train at booking time. This preserves the booking record even if train data changes later.
JSON flat-file storage — chosen deliberately to keep the project dependency-free and portable. No database setup required to run.
Seat allocation algorithm — linear scan of the 2D seat matrix. First 0 found gets marked 1. Simple and deterministic.
Station order validation — validTrain() checks that source appears before destination in the station list. Prevents reverse-direction bookings.

Limitations and Future Improvements

Single-seat booking only — multi-passenger booking not yet supported
No seat selection — system auto-assigns first available seat
No date-based filtering — trains show regardless of travel date
Concurrent booking safety — no locking on JSON file writes (fine for single-user CLI)
Could be extended with Spring Boot REST API, MySQL, and JWT authentication


Author
Md Meraj · Java Backend Developer
GitHub

Part of my Java backend development portfolio — built to practice OOP, file I/O, and CLI application design.
