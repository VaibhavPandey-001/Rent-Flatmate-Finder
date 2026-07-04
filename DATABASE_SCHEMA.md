# Database Schema

The application uses PostgreSQL.

## Tables

### User

Stores login and user information.

Fields

- id
- name
- email
- password
- role

---

### TenantProfile

Stores tenant preferences.

Fields

- id
- preferredLocation
- minBudget
- maxBudget
- moveInDate

Relationship

- One TenantProfile belongs to one User.

---

### Listing

Stores room listings created by owners.

Fields

- id
- title
- description
- location
- rent
- availableFrom
- roomType
- furnishing
- status

Relationship

- One Listing belongs to one Owner(User).

---

### Match

Stores AI compatibility results.

Fields

- id
- score
- explanation

Relationship

- One Match belongs to one TenantProfile.
- One Match belongs to one Listing.

---

### Interest

Stores requests sent by tenants.

Fields

- id
- status

Relationship

- One Interest belongs to one Tenant.
- One Interest belongs to one Listing.

---

### ChatMessage

Stores chat messages.

Fields

- id
- message
- sentAt

Relationship

- One ChatMessage belongs to one Interest.
- One ChatMessage belongs to one User.

---

# Entity Relationships

```
User
 ├── TenantProfile
 ├── Listing
 └── ChatMessage

TenantProfile
 └── Match

Listing
 ├── Match
 └── Interest

Interest
 └── ChatMessage
```

---

# Notes

- Passwords are stored in encrypted form using BCrypt.
- JWT is used for authentication.
- AI match results are stored so they do not need to be generated every time.
- Chat messages are saved permanently in the database.
