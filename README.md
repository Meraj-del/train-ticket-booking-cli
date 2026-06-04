# Train Ticket Booking System

A Java-based command-line train ticket booking system inspired by IRCTC workflows.

The application supports user registration, authentication, train search, ticket booking, cancellation, and booking history management while persisting all data locally using JSON files.

This project focuses on applying core Java concepts, object-oriented design principles, file-based persistence, and secure authentication without relying on external databases.

---

# Overview

The system simulates a simplified railway reservation platform where users can:

* Create an account
* Login securely
* Search trains by route
* Book tickets
* Cancel tickets
* View booking history

All application data is persisted locally using JSON files, making the project lightweight and easy to run without additional infrastructure.

---

# Architecture

```text
CLI Application
       │
       ▼
Application Layer
       │
       ▼
Service Layer
 ┌──────────────┬──────────────┐
 ▼              ▼
User Service   Train Service
 └──────────────┴──────────────┘
       │
       ▼
JSON Persistence Layer
 ┌──────────────┬──────────────┐
 ▼              ▼
users.json    trains.json
```

---

# Project Structure

```text
train-ticket-booking-system/
│
├── src/
│   ├── entity/
│   │   ├── User.java
│   │   ├── Train.java
│   │   └── Ticket.java
│   │
│   ├── service/
│   │   ├── UserBookingService.java
│   │   └── TrainService.java
│   │
│   ├── util/
│   │   └── UserServiceUtil.java
│   │
│   └── App.java
│
├── users.json
├── trains.json
└── README.md
```

---

# Features

## User Authentication

Supports:

* User Registration
* User Login
* BCrypt Password Hashing

Passwords are never stored in plain text.

Authentication is performed using BCrypt hash verification.

---

## Train Search

Users can search trains using:

* Source Station
* Destination Station

The system validates route direction to ensure the destination appears after the source station.

---

## Ticket Booking

Features:

* Automatic Seat Allocation
* Real-Time Seat Availability
* Ticket Generation
* Booking Persistence

The booking engine scans the seat matrix and allocates the first available seat.

---

## Ticket Cancellation

Users can:

* Cancel previously booked tickets
* Release allocated seats
* Update booking history automatically

---

## Booking History

Users can view all previously booked tickets associated with their account.

Booking information remains available across application restarts through JSON persistence.

---

# Data Persistence

The application uses JSON flat-file storage instead of a traditional database.

## User Storage

```text
users.json
```

Stores:

* User Information
* BCrypt Password Hashes
* Ticket History

---

## Train Storage

```text
trains.json
```

Stores:

* Train Details
* Routes
* Seat Availability
* Station Schedules

---

# Data Model

## User

Represents a registered customer.

Attributes:

* User ID
* Name
* Hashed Password
* Ticket History

---

## Train

Represents a train and its route.

Attributes:

* Train ID
* Train Number
* Station List
* Station Timings
* Seat Matrix

---

## Ticket

Represents a booking record.

Attributes:

* Ticket ID
* User ID
* Source Station
* Destination Station
* Travel Date
* Train Snapshot

---

# Security

Password security is implemented using BCrypt.

Benefits:

* One-way hashing
* Salt generation
* Protection against rainbow table attacks
* Industry-standard password storage approach

No user password is stored in plain text.

---

# Key Design Decisions

## JSON-Based Persistence

Chosen to:

* Avoid database setup
* Keep the application portable
* Focus on Java fundamentals

---

## Train Snapshot Storage

Each ticket stores a snapshot of the train at booking time.

Benefits:

* Historical booking integrity
* Tickets remain valid even if train information changes later

---

## Deterministic Seat Allocation

The booking engine uses a predictable allocation strategy.

```text
0 = Available
1 = Booked
```

The first available seat is assigned automatically.

---

## Route Validation

The booking system validates station ordering.

Example:

```text
Bangalore → Jaipur → Delhi
```

Valid:

```text
Bangalore → Delhi
```

Invalid:

```text
Delhi → Bangalore
```

This prevents reverse-direction bookings.

---

# Technology Stack

| Layer             | Technology       |
| ----------------- | ---------------- |
| Language          | Java 21          |
| Build Tool        | Gradle           |
| JSON Processing   | Jackson Databind |
| Password Security | jBCrypt          |
| Storage           | JSON Flat Files  |

---

# Running the Project

## Prerequisites

* Java 21
* Gradle

---

## Build

```bash
./gradlew build
```

---

## Run

```bash
./gradlew run
```

---

# CLI Flow

```text
Train Ticket Booking System

1. Sign Up
2. Login
3. Exit
```

After Login:

```text
1. Search Trains
2. Book Ticket
3. Cancel Ticket
4. View Bookings
5. Logout
```

---

# Skills Demonstrated

* Core Java
* Object-Oriented Programming
* File I/O
* JSON Serialization
* Authentication & Security
* BCrypt Password Hashing
* Data Modeling
* Service Layer Design
* Gradle Build Automation
* CLI Application Development

---

# Learning Progression

This project represents the beginning of my backend engineering journey.

## What I Learned

* Object-Oriented Design
* Service Layer Architecture
* JSON Persistence
* Authentication Fundamentals
* Secure Password Storage
* Java Application Structure

Key takeaway:

> Backend systems are more than business logic. They require proper data modeling, persistence strategies, and security practices.

---

## What Came Next

After building CRUD-style business applications, I focused on scalability and concurrency.

### Java Multithreaded Server

Built to understand:

* Socket Programming
* Multithreading
* Thread Pools
* ExecutorService
* Server Scalability
* Load Testing with Apache JMeter

Key takeaway:

> Efficient resource management is essential for building scalable backend systems.
