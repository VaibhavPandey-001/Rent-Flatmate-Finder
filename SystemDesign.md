# System Design

## Overview

The project is divided into two parts:

- React Frontend
- Spring Boot Backend

The frontend communicates with the backend using REST APIs. Authentication is handled using JWT. PostgreSQL is used to store all application data.

For real-time chat, WebSocket with STOMP is used.

---

## Authentication

Users can register and login as:

- Tenant
- Owner
- Admin

After login, the backend generates a JWT token.

The frontend stores the token in local storage and sends it with every request using an Axios interceptor.

---

## Compatibility Matching

When a tenant opens the dashboard, the backend loads all available listings.

Each listing is sent to Groq AI along with the tenant profile.

The AI returns:

- Compatibility Score
- Short Explanation

The result is stored in the Match table so the same match does not need to be generated again.

If the AI service is unavailable, the application uses a simple fallback algorithm.

The fallback compares:

- Preferred Location
- Budget Range

and generates a score.

---

## Interest Flow

1. Tenant sends an interest request.
2. Owner can view all requests.
3. Owner accepts or declines the request.
4. Once accepted, chat becomes available.

---

## Chat

Chat is implemented using Spring WebSocket and STOMP.

Messages are stored in PostgreSQL before being sent to connected users.

Whenever a user opens the chat page, previous messages are loaded from the database.

This allows chat history to remain available even after restarting the application.

---

## Database Design

The main tables are:

- User
- TenantProfile
- Listing
- Match
- Interest
- ChatMessage

These tables are connected using JPA relationships.

---

## AI Integration

Groq API is used for compatibility scoring.

The backend creates a prompt using:

- Tenant preferences
- Listing details

Groq returns JSON containing:

- score
- explanation

The response is parsed and stored in the database.

---

## Notification Flow

Currently, chat messages use WebSocket.

The project structure also supports adding real-time notifications for new interests and status updates in the future.

---

## Future Improvements

- Image upload for listings
- Better notification system
- Online deployment
- Profile editing
- Better frontend UI
