# Hibernate Connectivity Example

This project is a Java-based demonstration of **Hibernate ORM (Object-Relational Mapping)** featuring a **Bi-directional One-to-One (1:1) Association** between an `Employee` and their assigned `Laptop`. It is built with Maven, Java 21, Hibernate 7, and MySQL.

---

## Features

1. **Automatic Database Creation**: A JDBC helper ensures that the target MySQL database is automatically created if it does not already exist, before Hibernate attempts to initialize the `SessionFactory`.
2. **Bi-directional 1:1 Mapping**: Showcases proper entity design where the `Employee` entity owns the relationship (`@JoinColumn`), and the `Laptop` entity is mapped bidirectionally (`mappedBy`).
3. **Cascading Lifecycle Operations**: Demostrates the use of `CascadeType.PERSIST` and `CascadeType.MERGE` to coordinate lifecycle states between associated entities.
4. **Clean CRUD Layer**: A dedicated services layer containing structured CRUD and mapping management operations.
5. **Robust Exception Handling**: Custom database exceptions handle missing records gracefully.

---

## Project Directory Structure

Below is the structured layout of the project. Click on any file path to navigate directly to it:

*   [pom.xml](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/pom.xml) — Maven Project Object Model with dependencies for Hibernate Core and MySQL Connector/J.
*   `src/main/resources/`
    *   [db.properties](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/resources/db.properties) — Database connection credentials and base URL.
    *   [hibernate.cfg.xml](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/resources/hibernate.cfg.xml) — Configuration settings for Hibernate Session Factory, including dialect, formatting, and update behavior.
*   `src/main/java/org/lazyCoder/`
    *   [Main.java](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/Main.java) — Application entry point demonstrating services and operations.
    *   `config/`
        *   [HibernateConnect.java](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/config/HibernateConnect.java) — Utility class to initialize, retrieve, and close the Hibernate `SessionFactory`.
        *   [PropertyLoader.java](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/config/PropertyLoader.java) — Utility to read database connectivity configurations from resources.
    *   `db/`
        *   [DBManage.java](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/db/DBManage.java) — Standard JDBC utility to bootstrap the database creation.
    *   `exception/`
        *   [NoEmployeeFoundException.java](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/exception/NoEmployeeFoundException.java) — Custom runtime exception for missing employee entities.
    *   `model/`
        *   [Employee.java](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/model/Employee.java) — The JPA entity representing the `employee` table.
        *   [Laptop.java](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/model/Laptop.java) — The JPA entity representing the `laptop` table.
    *   `service/`
        *   [Services.java](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/service/Services.java) — Implements Hibernate-based operations (CRUD, assign, unassign).

---

## Configuration & Prerequisites

### 1. Prerequisites
- **Java SDK**: Version 21 or later.
- **Maven**: Ensure Maven is installed and added to the system `PATH`.
- **MySQL Database Server**: Up and running on `localhost:3306`.

### 2. Configuration Settings

> [!IMPORTANT]
> Make sure to update the database credentials to match your local MySQL configuration.

*   Open [db.properties](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/resources/db.properties) and set your database connection details:
    ```properties
    db.url=jdbc:mysql://localhost:3306/
    db.username=YOUR_MYSQL_USERNAME
    db.password=YOUR_MYSQL_PASSWORD
    ```
*   Open [hibernate.cfg.xml](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/resources/hibernate.cfg.xml) and set the database name:
    ```xml
    <property name="hibernate.connection.url">
        jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME
    </property>
    <property name="hibernate.connection.username">YOUR_MYSQL_USERNAME</property>
    <property name="hibernate.connection.password">YOUR_MYSQL_PASSWORD</property>
    ```

---

## Technical Details

### 1. Database Creation Hook
To avoid errors if the database is not pre-created, [DBManage.java](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/db/DBManage.java) provides the [createDB](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/db/DBManage.java#L17-L30) method. It reads properties using [PropertyLoader](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/config/PropertyLoader.java), establishes a direct JDBC connection, and executes a `CREATE DATABASE IF NOT EXISTS` statement.

### 2. Entity Mappings
- **[Employee](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/model/Employee.java)**:
  - Contains a `laptop` relationship annotated with `@OneToOne` and `@JoinColumn(name = "lapId")`.
  - Cascades PERSIST and MERGE operations to the laptop entity.
- **[Laptop](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/model/Laptop.java)**:
  - Contains an `employee` relationship annotated with `@OneToOne(mappedBy = "laptop")`.
  - Cascades PERSIST and MERGE operations to the employee entity.

### 3. Services Layer
The [Services](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/service/Services.java) class manages the transaction boundaries and wraps standard database queries. Notable methods include:
- **Employee CRUD**: [createEmployee](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/service/Services.java#L18-L27), `getEmployeeById`, `updateEmployee`, and `deleteEmployee`.
- **Laptop CRUD**: `createLap`, `getLaptopById`, `updateLaptop`, and `deleteLaptop`.
- **Relationships Assignment**:
  - [assignLap](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/service/Services.java#L165-L188): Links an employee with a laptop and persists changes.
  - [unassignLap](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/service/Services.java#L191-L211): Severs the relationship by setting the association to `null` on the owning entity side.

---

## How to Build & Run

### 1. Build the Project
Use Maven to clean the directory and compile the source code:
```bash
mvn clean compile
```

### 2. Run the Application
You can run the driver application using Maven:
```bash
mvn exec:java -Dexec.mainClass="org.lazyCoder.Main"
```

> [!TIP]
> The [main](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/Main.java#L9-L55) method in [Main.java](file:///c:/Users/Abhaykumar/Downloads/HibernateConnection/HibernateConnectivity/src/main/java/org/lazyCoder/Main.java) has commented-out example routines. 
> - For the **first run**, uncomment the `DBManage` code lines to create the database.
> - For **subsequent runs**, comment out the database creation lines, and uncomment other CRUD actions (creating/updating/deleting/assigning employee or laptop) to test the operations.
