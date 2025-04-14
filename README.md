# 🚘 GoMechanic - Vehicle Breakdown & Scheduled Service Management System

GoMechanic is a backend application designed to help users quickly find and book nearby mechanics during vehicle breakdowns or schedule services in advance. 
It includes dedicated roles for Users, Service Providers, and Admins, with features like robust JWT-based authentication, real-time location-aware service discovery, email/OTP verification, PDF receipt generation, and automated maintenance operations.
---

## 📌 Features

### 👤 User Module
- Register and login securely
- Book **On-Demand** (Emergency) or **Scheduled** service
- Get matched with nearby verified mechanics
- View service history and download service receipts
- OTP-based email & SMS verification during registration

### 🔧 Service Provider Module
- Register and manage services
- Accept/Decline new requests
- Get email alerts for immediate and scheduled services
- Generate and download PDF-based receipts

### 🛠 Admin Module
- View all users, providers, and service requests
- Secure admin registration via backend-only control

---

## 🏗 Tech Stack

| Layer          | Technology Used                              |
|----------------|-----------------------------------------------|
|                |
| Backend        | Java, Spring Boot                             |
| Database       | MongoDB, MongoDB Atlas                        |
| PDF Generator  | iText 7 (Formatted service receipts)          |
| Email Services | Spring Mail (JavaMailSender)                  |
| OTP Handling   | Custom OTP logic + Redis (future integration) |
| Location APIs  | Google Maps API (future planned integration)         |

---

## ⚙️ Modules Overview

### Service Booking:
- Real-time service request matching based on user's live location
- DBRef used to maintain entity relationships

### Service Scheduling:
- Allows users to schedule services for a future date and time
- Email notifications sent to both users and providers

### Receipt Generation:
- PDF receipts with logos, service details, and formatting
- Download and Email support for receipts

### Email & SMS OTP Verification:
- OTP sent during registration (email & SMS)
- Verified before saving user details

---

## 🔐 Security
- JWT Authentication & Authorization for all APIs
- Role-based access control (`user`, `service_provider`, `admin`)
- Admin cannot be created directly via frontend

---

🔄 Future Enhancements
Redis integration for OTP expiry and rate limiting

Live map tracking and service area selection

Chat between user and provider

Push notifications for status updates

🧪 Testing & Tools
Postman for API testing

MongoDB Compass for DB inspection

IntelliJ IDEA as primary IDE

📌 Future Enhancements
Add React frontend

Integrate Redis for OTP storage with expiry

Rate limit OTP generation and add audit logs

Add email templating (Thymeleaf) for rich email UIs

Add map-based service area selector with Google Maps API

Deploy on Render/Heroku or AWS


👨‍💻 Developer

Yash Gupta

Backend Developer | Spring Boot | MongoDB

📧 Email: guptay264@gmail.com

📱 Phone: +91 7748965563

🔗 LinkedIn: www.linkedin.com/in/yash-gupta-7b3b262b9

💻 GitHub: [github.com/yash264](https://github.com/yash-264)




