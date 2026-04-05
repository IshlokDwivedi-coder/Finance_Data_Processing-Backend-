# Finance Data Processing Backend

## My Approach : 
As a backend developer learning best practices, my primary goal for this assignment was to write clean, understandable, and logical code. Instead of over-complicating the system, I focused on proper separation of concerns (Controller -> Service -> Repository) and making sure the business logic solves the exact requirements given.

### Technology Used
* **Java 17 & Spring Boot:** Used for fast setup and excellent dependency injection.
* **H2 In-Memory Database:** I chose this so that anyone evaluating my code can run it instantly without needing to configure a local MySQL server or handle credentials.
* **Spring Security & JWT:** Implemented for Role-Based Access Control.

### Features Implemented
1. **User & Role Management:**
    - An Admin can create users and assign roles (`VIEWER`, `ANALYST`, `ADMIN`) and set statuses (`ACTIVE`, `INACTIVE`).
2. **Financial Records CRUD:**
    - Full Create, Read, Update, Delete functionality.
    - Added validation to ensure Amount, Type, and Date are never empty.
    - Filtering support added for `type` and `category`.
3. **Dashboard Summary Logic:**
    - Instead of relying on complex database queries, I wrote a logical Java algorithm in `DashboardService.java` that iterates through the records to calculate Total Income, Total Expenses, Net Balance, and Category-wise groupings.
4. **Error Handling:**
    - Used a `GlobalExceptionHandler` so that if a user submits bad data, they receive a clean `400 Bad Request` with a helpful text message rather than a crashed server trace.

### Roles and Permissions
* **ADMIN:** Can create users, and has full CRUD over records.
* **ANALYST:** Can view records and view the dashboard summary.
* **VIEWER:** Can only view the dashboard summary.

### API Flow for Testing (Postman)
1. **Login:** `POST /api/auth/login` with admin credentials 
2. **Create a Record:** `POST /api/records` 
3. **View Dashboard:** `GET /api/dashboard/summary`.
