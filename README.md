# E-commerce Backend API

Backend API cho hệ thống E-commerce, xây dựng bằng **Spring Boot**, áp dụng **JWT Authentication**, **Spring Security**, và kiến trúc nhiều layer (Controller – Service – Repository).


---

##  Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Security + JWT**
- **Spring Data JPA (Hibernate)**
- **MySQL**
- **Maven**
- **Docker / Docker Compose**

---
##  Project Structure

```text
src/main/java/com/example/demo
│
├── controller        # REST Controllers
├── service           # Business logic (interfaces)
│   └── impl          # Service implementations
├── repository        # JPA Repositories
├── model             # Entity classes
├── dto               # Data Transfer Objects
├── mapper            # Entity ↔ DTO mapping
├── security          # JWT, Security config, filters
├── exception         # Custom exceptions & global handler
└── DemoApplication   # Main application
```

---

##  Authentication & Authorization

- Sử dụng **JWT (JSON Web Token)** cho authentication
- Spring Security filter chain được cấu hình để:
  - Cho phép đăng ký / đăng nhập
  - Bảo vệ các API khác
- Custom `JwtAuthenticationFilter` để:
  - Lấy token từ header
  - Validate token
  - Set Authentication vào SecurityContext

### Auth Flow

```text
Client
 → Login API
   → AuthenticationManager
     → UserDetailsService
       → JWT generated
 → Client gửi JWT trong Authorization Header
 → JwtAuthenticationFilter validate token
 → Controller
```

---

##  Main Features

###  User
- Register
- Login (JWT)
- Get user information

###  Product
- Create product
- Get all products
- Update product
- Delete product

###  Order
- Create order
- Add product to order
- Validate order item quantity

---

##  API Example

### Login
```http
POST /api/auth/login
```

Request body:
```json
{
  "email": "user@gmail.com",
  "password": "123456"
}
```

Response:
```json
{
  "token": "jwt-token-here"
}
```

---

##  Exception Handling

- Custom exceptions cho từng nghiệp vụ:
  - User not found
  - Product already exists
  - Order over item limit
- Global exception handler để trả response thống nhất cho client

---

## Run with Docker

```bash
docker-compose up -d
```

---

## 🛠 Future Improvements

- Pagination & sorting
- Role-based authorization
- Refresh token
- Unit test & integration test
- Improve RESTful API naming

---

