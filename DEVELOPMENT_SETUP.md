# Development Setup Guide

## Prerequisites
- Java 11 or higher
- Apache Tomcat 9.0 or higher
- Oracle Database 19c or higher (or Oracle Database Express Edition)
- IDE with Java EE support (Eclipse, IntelliJ IDEA)
- Git for version control

## Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/Adam-Muqrish/BarbershopBookingSystem.git
cd BarbershopBookingSystem
```

### 2. Database Setup
1. **Install Oracle Database** (or use Oracle XE for development)
2. **Create a new database schema** named `project`
3. **Update database configuration** in `barbershop-booking-system-2/src/main/java/com/hugi/barbershop/common/util/DBUtil.java`:
   ```java
   private static final String DB_URL = "jdbc:oracle:thin:@//localhost:1521/FREEPDB1";
   private static final String DB_USER = "project";
   private static final String DB_PASSWORD = "oracle";
   ```

### 3. Create Database Tables
Execute the following SQL scripts to create the required tables:

```sql
-- CUSTOMERS table
CREATE TABLE CUSTOMERS (
    CUSTID VARCHAR2(50) PRIMARY KEY,
    CUSTNAME VARCHAR2(100) NOT NULL,
    CUSTEMAIL VARCHAR2(100) UNIQUE NOT NULL,
    CUSTPASSWORD VARCHAR2(100) NOT NULL,
    CUSTPHONENUMBER VARCHAR2(20),
    CUSTPICTURE VARCHAR2(255),
    LOYALTYPOINTS NUMBER DEFAULT 0
);

-- STAFF table
CREATE TABLE STAFF (
    STAFFID VARCHAR2(50) PRIMARY KEY,
    STAFFNAME VARCHAR2(100) NOT NULL,
    STAFFEMAIL VARCHAR2(100) UNIQUE NOT NULL,
    STAFFPASSWORD VARCHAR2(100) NOT NULL,
    STAFFPHONENUMBER VARCHAR2(20),
    STAFFPICTURE VARCHAR2(255),
    DESCRIPTION VARCHAR2(500),
    STAFFROLE VARCHAR2(50) DEFAULT 'BARBER',
    ADMINID VARCHAR2(50)
);

-- APPOINTMENTS table
CREATE TABLE APPOINTMENTS (
    APPOINTMENTID VARCHAR2(50) PRIMARY KEY,
    CUSTBOOKFOR VARCHAR2(100),
    APPOINTMENTDATE DATE NOT NULL,
    APPOINTMENTTIME VARCHAR2(20) NOT NULL,
    CUSTTYPE VARCHAR2(50),
    PAYMENTSTATUS VARCHAR2(20) DEFAULT 'PENDING',
    SERVICESTATUS VARCHAR2(20) DEFAULT 'PENDING',
    CUSTID VARCHAR2(50) NOT NULL,
    STAFFID VARCHAR2(50) NOT NULL,
    VALUELOYALTY NUMBER DEFAULT 0,
    CONSTRAINT FK_APPOINTMENT_CUSTOMER FOREIGN KEY (CUSTID) REFERENCES CUSTOMERS(CUSTID),
    CONSTRAINT FK_APPOINTMENT_STAFF FOREIGN KEY (STAFFID) REFERENCES STAFF(STAFFID)
);

-- PAYMENTS table
CREATE TABLE PAYMENTS (
    PAYMENTID VARCHAR2(50) PRIMARY KEY,
    PAYMENTDATE DATE DEFAULT SYSDATE,
    PAYMENTAMOUNT NUMBER(10,2) NOT NULL,
    PAYMENTTYPE VARCHAR2(50),
    APPOINTMENTID VARCHAR2(50) NOT NULL,
    CONSTRAINT FK_PAYMENT_APPOINTMENT FOREIGN KEY (APPOINTMENTID) REFERENCES APPOINTMENTS(APPOINTMENTID)
);

-- FEEDBACK table
CREATE TABLE FEEDBACK (
    FEEDBACKID VARCHAR2(50) PRIMARY KEY,
    CUSTID VARCHAR2(50) NOT NULL,
    FEEDBACKTEXT VARCHAR2(1000),
    RATING NUMBER(1) CHECK (RATING BETWEEN 1 AND 5),
    FEEDBACKDATE DATE DEFAULT SYSDATE,
    CONSTRAINT FK_FEEDBACK_CUSTOMER FOREIGN KEY (CUSTID) REFERENCES CUSTOMERS(CUSTID)
);
```

### 4. IDE Setup (Eclipse)
1. **Import the project**:
   - File → Import → Existing Projects into Workspace
   - Select the `barbershop-booking-system-2` folder
   
2. **Configure Build Path**:
   - Right-click project → Properties → Java Build Path
   - Add external JARs: Oracle JDBC driver, Servlet API, JSP API
   
3. **Configure Tomcat Server**:
   - Window → Preferences → Server → Runtime Environments
   - Add Tomcat 9.0 installation
   
4. **Deploy and Run**:
   - Right-click project → Run As → Run on Server
   - Choose Tomcat server

### 5. Access the Application
- **Main Application**: `http://localhost:8080/barbershop-booking-system-2/`
- **Admin Dashboard**: `http://localhost:8080/barbershop-booking-system-2/adminIndex.jsp`

## File Structure
```
barbershop-booking-system-2/
├── src/
│   ├── index.html                 # Static landing page
│   └── main/
│       ├── java/
│       │   └── com/hugi/barbershop/
│       │       ├── common/        # Shared components
│       │       ├── customer/      # Customer-related code
│       │       └── staff/         # Staff-related code
│       └── webapp/
│           ├── WEB-INF/
│           │   ├── web.xml        # Web application configuration
│           │   └── views/         # JSP view templates
│           ├── resources/         # CSS, JS, images
│           ├── index.jsp          # Main entry point
│           └── adminIndex.jsp     # Admin dashboard
└── .project                       # Eclipse project file
```

## Common Issues & Solutions

### Database Connection Issues
- Ensure Oracle Database is running
- Verify connection parameters in `DBUtil.java`
- Check if Oracle JDBC driver is in classpath

### Compilation Errors
- Ensure all required JARs are in build path
- Check Java version compatibility
- Verify servlet API and JSP API are available

### Deployment Issues
- Ensure Tomcat is properly configured
- Check if project is properly deployed to webapps folder
- Verify web.xml syntax

## Development Tips

### 1. Database Development
- Use Oracle SQL Developer for database management
- Create sample data for testing
- Use database transactions appropriately

### 2. Frontend Development
- Tailwind CSS is used for styling
- JSP includes are used for code reuse
- Consider using browser developer tools for debugging

### 3. Backend Development
- Follow MVC pattern consistently
- Use proper exception handling
- Implement proper logging

### 4. Testing
- Test database operations thoroughly
- Use browser developer tools for frontend debugging
- Test different user scenarios

## Next Steps
1. **Set up the development environment**
2. **Explore the codebase** to understand the architecture
3. **Create sample data** for testing
4. **Run the application** and test basic functionality
5. **Start implementing** new features or improvements

## Getting Help
- Review the main documentation in `REPOSITORY_CAPABILITIES.md`
- Check the code comments for implementation details
- Use the issue tracker for bug reports and feature requests