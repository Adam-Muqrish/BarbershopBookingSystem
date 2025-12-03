# Project Structure Overview

## Directory Structure
```
BarbershopBookingSystem/
├── barbershop-booking-system-2/
│   ├── .project                           # Eclipse project configuration
│   ├── .settings/                         # Eclipse settings
│   ├── .gitignore                         # Git ignore rules
│   └── src/
│       ├── index.html                     # Static landing page
│       └── main/
│           ├── java/
│           │   └── com/hugi/barbershop/
│           │       ├── common/            # Shared components
│           │       │   ├── dao/           # Data Access Objects
│           │       │   │   ├── AppointmentDAO.java
│           │       │   │   ├── CustomerDAO.java
│           │       │   │   ├── FeedbackDAO.java
│           │       │   │   ├── PaymentDAO.java
│           │       │   │   └── StaffDAO.java
│           │       │   ├── filter/        # Security filters
│           │       │   │   └── AuthenticationFilter.java
│           │       │   └── util/          # Utility classes
│           │       │       └── DBUtil.java
│           │       ├── customer/          # Customer module
│           │       │   ├── controller/    # Customer controllers
│           │       │   │   ├── AppointmentHistoryController.java
│           │       │   │   ├── AppointmentManagement.java
│           │       │   │   └── ReceiptController.java
│           │       │   ├── model/         # Customer models
│           │       │   │   ├── Appointment.java
│           │       │   │   ├── Cash.java
│           │       │   │   ├── Customer.java
│           │       │   │   ├── Feedback.java
│           │       │   │   ├── OnlinePayment.java
│           │       │   │   └── Payment.java
│           │       │   ├── filter/        # Customer filters
│           │       │   └── listener/      # Customer listeners
│           │       └── staff/             # Staff module
│           │           ├── controller/    # Staff controllers
│           │           │   └── listBarber.java
│           │           └── model/         # Staff models
│           │               └── Staff.java
│           └── webapp/
│               ├── META-INF/              # Web application metadata
│               ├── WEB-INF/
│               │   ├── web.xml            # Web application configuration
│               │   └── views/             # JSP view templates
│               │       ├── admin/         # Admin views
│               │       │   └── listBarber.jsp
│               │       ├── customer/      # Customer views
│               │       │   ├── appointment-history.jsp
│               │       │   ├── appointment-management.jsp
│               │       │   ├── error.jsp
│               │       │   └── receipt.jsp
│               │       └── includes/      # Shared view components
│               │           ├── adminNav.jsp
│               │           ├── footer.jsp
│               │           ├── header.jsp
│               │           └── nav.jsp
│               ├── resources/             # Static resources
│               │   ├── css/              # Stylesheets
│               │   ├── js/               # JavaScript files
│               │   └── uploads/          # File uploads
│               ├── adminIndex.jsp         # Admin dashboard
│               ├── dbTest.jsp            # Database test page
│               └── index.jsp             # Main entry point
├── README.md                             # Project overview
├── REPOSITORY_CAPABILITIES.md            # Detailed capabilities analysis
├── DEVELOPMENT_SETUP.md                  # Setup instructions
└── PROJECT_STRUCTURE.md                 # This file
```

## Component Relationships

### MVC Architecture Flow
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│      View       │    │   Controller    │    │      Model      │
│   (JSP Pages)   │◄──►│   (Servlets)    │◄──►│   (POJOs)       │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Static HTML   │    │   HTTP Request  │    │   Business      │
│   CSS/JS        │    │   Processing    │    │   Logic         │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### Database Layer
```
┌─────────────────┐
│   Controllers   │
└─────────────────┘
         │
         ▼
┌─────────────────┐
│      DAOs       │
└─────────────────┘
         │
         ▼
┌─────────────────┐
│     DBUtil      │
└─────────────────┘
         │
         ▼
┌─────────────────┐
│  Oracle Database│
└─────────────────┘
```

## Key Files Explained

### Configuration Files
- **web.xml**: Web application configuration, servlet mappings
- **.project**: Eclipse project settings
- **DBUtil.java**: Database connection configuration

### Core Controllers
- **AppointmentManagement.java**: Handles appointment booking/modification
- **ReceiptController.java**: Generates payment receipts
- **AppointmentHistoryController.java**: Displays appointment history
- **listBarber.java**: Lists available barbers

### Data Access Layer
- **CustomerDAO.java**: Customer CRUD operations
- **StaffDAO.java**: Staff/barber management
- **AppointmentDAO.java**: Appointment operations
- **PaymentDAO.java**: Payment processing
- **FeedbackDAO.java**: Customer feedback handling

### Models
- **Customer.java**: Customer data structure
- **Staff.java**: Staff/barber information
- **Appointment.java**: Appointment details
- **Payment.java**: Payment information
- **Feedback.java**: Customer feedback

### Security
- **AuthenticationFilter.java**: Protects secured pages
- Session management in controllers

### Views
- **index.jsp**: Main landing page
- **adminIndex.jsp**: Admin dashboard
- **includes/**: Reusable JSP components
- **customer/**: Customer-specific views
- **admin/**: Admin-specific views

## Development Workflow

### 1. Request Flow
```
User Request → Filter → Controller → DAO → Database
                ↓
Response ← View ← Controller ← DAO ← Database
```

### 2. Authentication Flow
```
User → Login → Session Created → Access Granted
  ↓
Protected Resource → Filter Check → Allow/Deny
```

### 3. Appointment Flow
```
Customer → Booking Form → Validation → Database
    ↓
Confirmation → Payment → Receipt → Email/SMS
```

## File Naming Conventions

- **Controllers**: `*Controller.java` or descriptive names
- **DAOs**: `*DAO.java`
- **Models**: `*.java` (entity names)
- **JSPs**: `*.jsp` (lowercase with hyphens)
- **Filters**: `*Filter.java`
- **Utilities**: `*Util.java`

## Dependencies

### Required Libraries
- Jakarta Servlet API
- Jakarta JSP API
- Oracle JDBC Driver
- JSTL (JavaServer Pages Standard Tag Library)

### Optional Libraries (for enhancements)
- Apache Commons (utilities)
- Jackson (JSON processing)
- Log4j (logging)
- JUnit (testing)

## Database Schema

### Tables
- **CUSTOMERS**: User account information
- **STAFF**: Barber/staff details
- **APPOINTMENTS**: Booking records
- **PAYMENTS**: Transaction history
- **FEEDBACK**: Customer reviews

### Relationships
- Customer → Appointments (One-to-Many)
- Staff → Appointments (One-to-Many)
- Appointments → Payments (One-to-One)
- Customer → Feedback (One-to-Many)

This structure provides a solid foundation for a scalable barbershop booking system with clear separation of concerns and maintainable code organization.