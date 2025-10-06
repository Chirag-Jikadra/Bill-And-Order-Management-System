# Bill-And-Order-Management-System

A **Spring Boot–based billing and order management system** that allows admins to manage products, generate bills, and send notifications via **Email, SMS, and WhatsApp** using Twilio API integration.

---

## 🚀 Features

- **Product Management**
  - Add, update, and list products
  - Auto-generate product stock CSV reports
  - Email product reports with threshold alerts

- **Order Management**
  - Place customer orders with multiple products
  - Auto-calculate GST and total amount
  - Update product stock automatically after order

- **Notification System**
  - Sends real-time SMS and WhatsApp messages via Twilio
  - Sends email reports with CSV attachments

- **Error Handling**
  - Validates customer IDs and product quantities
  - Throws appropriate exceptions for invalid or missing data

---

## 🏗️ Tech Stack

| Layer | Technology |
|-------|-------------|
| **Backend** | Java 17, Spring Boot |
| **Database** | MySQL / PostgreSQL (configurable) |
| **Notifications** | Twilio API (SMS & WhatsApp) |
| **Email** | Spring Mail + JavaMail API |
| **Reports** | Apache Commons CSV |
| **Build Tool** | Maven |

---

## 📁 Project Structure


