# 2023-java-gent15

This system is designed to efficiently manage the logistics operations of a company, focusing on the administration and
coordination of carriers, orders, boxes, and associated companies. The application uses the latest best practices for
efficient and scalable application development.
Description

The Logistics Management System is a Java-based application developed with a focus on:

    Processing and managing Orders
    Viewing Customers
    Managing Carriers
    Managing Boxes
    Managing Employees

The system also provides an administrator interface that allows efficient management and coordination of the various
moving parts.
Technical Stack

    JavaFx
    MySQL Database
    Hibernate
    Mockito & JUnit for Unit Testing

Getting Started
Dependencies

    Java 17 or later.
    A compatible IDE, e.g., IntelliJ IDEA.
    A MySQL Database.
    Maven.

Installing

    Clone the repository 
    Configure the database connection in the .env file.

.env template

```properties
# Database
DB_PASSWORD=# Password for the database
DB_USER=# Username for the database
DB_URL=# URL for the database
```

Add this to your run configuration (VM options) to suppress the log messages from the database driver

```properties
-Djava.util.logging.config.file="/path/to/loggin.properties"
```

Testing

The application includes unit tests written using the Mockito testing framework. To run these tests, use the command mvn
test.
Contributors

Feel free to report issues and submit pull requests. Contributions, issues, and feature requests are welcome.
