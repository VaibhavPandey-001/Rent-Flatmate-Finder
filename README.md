# Rent-Flatmate-Finder

## Project Overview

AI Flatmate Finder is a web application that helps tenants find suitable rooms based on their preferences.

Owners can create room listings, tenants can view AI-based matches, send interest requests, and chat with the owner after the request is accepted.

The project uses Groq LLM for compatibility scoring. If the AI service is unavailable, the application automatically uses a simple rule-based matching algorithm.

---

## Features

### Authentication

- User Registration
- User Login
- JWT Authentication
- Role Based Access
    - Tenant
    - Owner
    - Admin

---

### Owner

- Create Listing
- View Own Listings
- Mark Listing as Filled
- View Interest Requests
- Accept Interest
- Decline Interest
- Chat with Tenant

---

### Tenant

- Create Tenant Profile
- View AI Matches
- Send Interest
- Chat with Owner

---

### Admin

- View Dashboard Statistics

---

### AI Matching

The application sends tenant and listing information to Groq AI.

The AI returns

- Compatibility Score
- Short Explanation

If Groq is unavailable, the application calculates the score using location and budget matching.

---

### Chat

After an owner accepts a tenant's request, both users can exchange messages.

Messages are

- stored in PostgreSQL
- loaded as chat history
- sent using WebSocket (STOMP)

---

## Tech Stack

### Backend

- Java 22
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL
- WebSocket (STOMP)
- Maven

### Frontend

- React
- Vite
- React Router
- Axios

### AI

- Groq API
- Llama 3.3 70B Versatile

---

## Project Structure

```
Flatmate Project
│
├── flatmate-backend
└── flatmate-frontend
```

---

## Setup

### Backend

1. Open the backend project.

2. Create PostgreSQL database

```
flatmate_db
```

3. Update

```
application.properties
```

with your database credentials, JWT secret, Groq API key and email credentials.

4. Run

```
mvn spring-boot:run
```

Backend runs on

```
http://localhost:8081
```

---

### Frontend

Install packages

```
npm install
```

Run

```
npm run dev
```

Frontend runs on

```
http://localhost:5173
```

---

## AI Prompt

The application sends tenant and listing details to Groq AI and expects JSON like:

```json
{
  "score": 87,
  "explanation": "Location and budget are a good match."
}
```

---

## Fallback Logic

If AI is unavailable, the application compares

- Preferred Location
- Budget Range

and calculates a compatibility score.

---

## Future Improvements

- Image Upload
- Better Notification System
- Online Deployment
- Better UI Design

---

## Author

Vaibhav Pandey
