# 💈 Barbershop Booking System

A comprehensive web-based booking system for barbershops built with Java servlets, JSP, and Oracle Database.

## ✨ Features

### For Customers
- 🔐 User registration and login
- 📅 Online appointment booking
- 👤 Profile management with picture upload
- 🏆 Loyalty points system
- 📜 Appointment history
- 🧾 Digital receipts
- ✏️ Appointment modification

### For Staff/Barbers
- 📋 Appointment management
- 👥 Customer information access
- 📊 Service status tracking

### For Administrators
- 📊 Dashboard with business statistics
- 👥 Customer management
- 👨‍💼 Staff management
- 💰 Sales and transaction reporting
- 📈 Business analytics

## 🛠️ Technology Stack

- **Backend**: Java Servlets, JSP
- **Frontend**: HTML, CSS (Tailwind), JavaScript
- **Database**: Oracle Database
- **Server**: Apache Tomcat
- **Architecture**: MVC Pattern
- **Security**: Session-based authentication

## 🚀 Quick Start

### Prerequisites
- Java 11+
- Apache Tomcat 9.0+
- Oracle Database 19c+
- IDE with Java EE support

### Installation
1. **Clone the repository**
   ```bash
   git clone https://github.com/Adam-Muqrish/BarbershopBookingSystem.git
   cd BarbershopBookingSystem
   ```

2. **Set up the database**
   - Create Oracle database schema
   - Run the SQL scripts to create tables
   - Update database configuration in `DBUtil.java`

3. **Deploy to Tomcat**
   - Import project into IDE
   - Configure Tomcat server
   - Deploy and run the application

4. **Access the application**
   - Main site: `http://localhost:8080/barbershop-booking-system-2/`
   - Admin dashboard: `http://localhost:8080/barbershop-booking-system-2/adminIndex.jsp`

For detailed setup instructions, see [DEVELOPMENT_SETUP.md](DEVELOPMENT_SETUP.md).

## 📚 Documentation

- **[Repository Capabilities](REPOSITORY_CAPABILITIES.md)** - Comprehensive analysis of features and possibilities
- **[Development Setup](DEVELOPMENT_SETUP.md)** - Step-by-step setup guide
- **[API Documentation](docs/API.md)** - (Coming soon)

## 🏗️ Architecture

```
├── Customer Module
│   ├── Registration/Login
│   ├── Profile Management
│   ├── Appointment Booking
│   └── Payment Processing
├── Staff Module
│   ├── Appointment Management
│   └── Customer Service
├── Admin Module
│   ├── Dashboard
│   ├── User Management
│   └── Reporting
└── Common Components
    ├── Authentication
    ├── Database Access
    └── Utilities
```

## 🔧 Key Components

### Models
- `Customer` - Customer information and loyalty points
- `Staff` - Barber/staff information
- `Appointment` - Booking details and status
- `Payment` - Payment records and receipts

### Controllers
- `AppointmentManagement` - Booking operations
- `ReceiptController` - Payment receipts
- `AppointmentHistoryController` - Historical data

### Database Access
- `CustomerDAO` - Customer data operations
- `StaffDAO` - Staff data operations
- `AppointmentDAO` - Appointment data operations
- `PaymentDAO` - Payment data operations

## 🌟 What Can You Do?

This repository offers extensive possibilities:

### 💻 Development
- Learn Java web development
- Understand MVC architecture
- Practice database design
- Implement new features

### 🚀 Business Use
- Deploy for real barbershop operations
- Customize for other service industries
- Integrate with payment gateways
- Add mobile app support

### 🔧 Enhancement
- Add SMS/email notifications
- Implement real-time availability
- Create mobile apps
- Add advanced analytics

### 🎓 Learning
- Study servlet/JSP development
- Learn database integration
- Practice authentication systems
- Understand session management

## 📈 Potential Improvements

- **Technical**: Unit testing, API layer, caching, microservices
- **Business**: Multi-location support, promotions, reviews, waitlists
- **Security**: OAuth2, 2FA, enhanced validation
- **Mobile**: Progressive Web App, native mobile apps
- **Analytics**: Business intelligence, customer insights

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 👥 Team

- **Adam Muqrish** - Initial development and architecture

## 🆘 Support

For questions, issues, or feature requests:
- Create an issue in the repository
- Check the documentation files
- Review the code comments

## 📱 Screenshots

*Coming soon - UI screenshots and demo videos*

---

⭐ If you find this project useful, please give it a star!

💡 Have ideas for improvements? Open an issue or submit a pull request!