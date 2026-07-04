# API Documentation

Base URL

```
http://localhost:8081
```

---

# Authentication

## Register

**POST**

```
/auth/register
```

Request

```json
{
  "name": "John",
  "email": "john@gmail.com",
  "password": "password",
  "role": "TENANT"
}
```

---

## Login

**POST**

```
/auth/login
```

Response

```json
{
  "token": "JWT_TOKEN"
}
```

---

# Tenant

## Create / Update Profile

**POST**

```
/tenant/profile
```

---

## Get Profile

**GET**

```
/tenant/profile
```

---

# Listings

## Create Listing

**POST**

```
/listings
```

---

## Get All Listings

**GET**

```
/listings
```

---

## Get My Listings

**GET**

```
/listings/me
```

---

## Mark Listing Filled

**PATCH**

```
/listings/{id}/filled
```

---

# AI Matching

## Get Matches

**GET**

```
/matches
```

Response

```json
[
  {
    "listingId": 1,
    "title": "Single Room",
    "score": 90,
    "explanation": "Good budget and location match."
  }
]
```

---

# Interest

## Send Interest

**POST**

```
/interests/{listingId}
```

---

## Owner Requests

**GET**

```
/interests/owner
```

---

## Accept Request

**PATCH**

```
/interests/{interestId}/accept
```

---

## Decline Request

**PATCH**

```
/interests/{interestId}/decline
```

---

# Chat

## Chat History

**GET**

```
/chat/{interestId}
```

---

## Send Message

WebSocket Destination

```
/app/send
```

Subscribe

```
/topic/chat/{interestId}
```

---

# Admin

## Dashboard

**GET**

```
/admin/dashboard
```

---

# Authentication

Except for Login and Register, every API requires a JWT token.

Header

```
Authorization: Bearer <JWT_TOKEN>
```
