
# VetCare

![ariana_cat](https://github.com/user-attachments/assets/9e0388ab-fd62-478e-8324-dabe44b361d6)

VetCare is a comprehensive **web-based platform** designed to revolutionize **veterinary services** by providing a seamless and efficient **system for pet owners, veterinary clinics, and related stakeholders**. 

This full-stack application aims to simplify the management of pet healthcare through features such as **appointment scheduling**, **secure access to medical records**, **prescription management**, and **educational resources**. 

By leveraging modern web technologies and a cloud-based approach, VetCare enhances the delivery of veterinary services, improves the efficiency of veterinary practices, and provides pet owners with easy access to high-quality care and information.




## Features

- **Appointment Management:** Users can easily book, reschedule, or cancel veterinary appointments through an intuitive calendar interface. Real-time availability tracking ensures accurate scheduling.

- **Medical Records Access:** Secure viewing, management, and sharing of pets' medical history, vaccination records, and treatment plans. Pet owners can download records in CSV format.

- **Prescription Management:** Request prescription refills and access detailed medication information. Includes options for pickup/delivery location selection and online payment.

- **Educational Resources:** A rich library of articles, videos, and guides on pet care, accessible via a dedicated resources page with search and filter functionalities.

- **User Management:** Comprehensive system for managing user roles, permissions, and access levels for pet owners, veterinarians, clinic staff, system administrators, IT support, and pharmacy staff.

- **Billing and Invoicing:** Handles billing, payments, invoicing, and insurance claims management.

- **Inventory Management:** Tracks and manages clinic inventory, including medications, supplies, and equipment.

- **Reporting and Analytics:** Generates reports and analytics to monitor clinic performance and patient outcomes.

- **User-Friendly Interface:** An intuitive and responsive platform ensuring a seamless user experience across various devices (desktops, laptops, tablets, and mobile phones).

- **Reminders and Notifications:** Alerts for upcoming appointments and medication schedules.

- **Online Payment:** Secure payment processing for bookings and purchases.
## Tech Stack
**Frontend**
- Bootstrap
- HTML5
- CSS3

**Backend**
- Java
- Spring Boot
- Apache Maven
- JDBC

**Database**
- MySQL
- Flyway (Migration)

**DevOps & Tools**
- Docker
- GitHub Actions (CI/CD)

**Testing**
- JUnit5
## Installation and Setup

**Prerequisites**

- Java Development Kit (JDK) 17 or later
- Apache Maven
- MySQL Server
- Docker (optional, for containerized deployment)
- Git

**Backend Setup** 

**1. Clone the repository**

```bash
git clone https://github.com/02siri/VetCare.git
cd VetCare
```

**2. Configure MySQL Database**

- Create a new MySQL database for VetCare (e.g., ***'vetcare_db'***).
- Update the database connection properties in ***'src/main/resources/application.properties'*** (or ***'application.yml'***) with your MySQL credentials and database name. 

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/vetcare_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
**3. Run Flyway Migrations** 

Flyway will automatically manage database migrations when the Spring Boot application starts, based on the SQL scripts in ***'src/main/resources/db/migration'***. 

Ensure your database is accessible.

**4. Build the Spring Boot application**

```bash
mvn clean install
```

**5. Run the Spring Boot application**

```bash
mvn spring-boot:run
```

The backend server will start

**Frontend Setup**

The frontend is integrated with the Spring Boot application. 

Once the backend is running, you can access the web application through your browser.

**Docker Deployment (Optional)**

**Build Docker image**
```bash
docker build -t vetcare-app .
```

**Run Docker container**

```bash
docker run -p 8080:8080 vetcare-app
```
The application will be accessible via http://localhost:8080.

    
## Usage/Examples
- **Home Page:** Browse new products and pet-relevant articles.
- **Login/Sign Up:** Create an account or log in to access personalized features.
- **Appointment:** Schedule, reschedule, or cancel appointments.
- **Medical Record:** View your pet's medical history, vaccination records, and treatment plans. Download records as CSV.
- **Prescription:** Request prescription refills and access medication instructions.
- **Resources:** Explore educational articles and videos on pet care.
- **Profile:** Manage personal and pet information, view notifications, orders, and appointment history.
## Agile Methodologies
This project was developed using Agile Methodologies, emphasizing iterative development and continuous feedback. 

Our process included:
- **Regular Stand-up Meetings:** Daily brief meetings to synchronize activities and address impediments.
- **Sprint Planning:** Collaborative sessions at the beginning of each sprint to define goals and tasks.
- **Sprint Review Meetings:** Demonstrations of completed work and discussions with stakeholders to gather feedback.
- **Maintenance of Sprint Backlogs:** Continuously refined and prioritized lists of features and tasks.
## Contributers

- Srishti Khosla
- Rydham Oza
- Jason Quach
- Adison Ng
- Lee Wei Lik


