# Address Book Backend Application

This Java backend application is designed to allow both individuals and businesses to manage their address book. The application provides CRUD (Create, Read, Update, Delete) operations for managing contacts, and it implements basic authentication and role-based authorization.

## Table of Contents

- [Requirements](#requirements)
- [Technology Stack](#technology-stack)
- [Data Model](#data-model)
- [Endpoints](#endpoints)
- [Authentication and Authorization](#authentication-and-authorization)
- [Database](#database)
- [Bonus Features](#bonus-features)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)
- [License](#license)

## Requirements

### Data Model

The data model for contacts includes the following fields:

- **ID**: Auto-generated unique identifier.
- **First Name**
- **Last Name**
- **Address**
- **Mobile Phone Number**
- **VAT (Value Added Tax)**

### Endpoints

RESTful API endpoints have been implemented for the following operations:

- Create a new contact
- Read a contact by ID
- Update an existing contact by ID
- Delete a contact by ID
- List all contacts
- Register user
- Read contact own info

### Authentication and Authorization

- Basic authentication using username and password.
- Role-based authorization with two roles: "individual" and "business."
- Individuals can only access and manage their own contacts.
- Businesses can access and manage all contacts associated with their business.

### Database

- A relational database is used to store contact data.
- The database schema is designed to accommodate both individual and business contacts.

### Bonus Features (Optional)

- Search functionality to search for contacts by name or mobile phone number.

## Technology Stack

This backend application is built using the following technologies:

- **Spring Boot**: A lightweight Java framework for building microservices and applications.
- **Hibernate**: An object-relational mapping (ORM) library for Java.
- **PostgreSQL**: A powerful, open-source relational database system.
- **API**: RESTful API for communication.

## Data Model

The data model consists of a `Contact` entity with fields as described in the requirements. The entity is mapped to the database using JPA (Java Persistence API).

## Endpoints

The RESTful API endpoints for contact management include:

- `POST /createContact`: Create a new contact.
- `POST /register`: Create a new user.
- `GET /contacts/{id}`: Read a contact by ID.
- `PATCH /contacts/{id}`: Update an existing contact by ID.
- `DELETE /contacts/{id}`: Delete a contact by ID.
- `GET /contact/all`: List all contacts.
- `GET /contact/profile`: Read contact own info.

## Authentication and Authorization

This application implements basic authentication using username and password. It also enforces role-based authorization:

- **Individual**: Can access and manage their own contacts.
- **Business**: Can access and manage all contacts associated with their business.

## Database

A PostgreSQL database is used to store contact data. The database schema is designed to accommodate both individual and business contacts. If needed, Liquibase can be used for database schema management.

## Bonus Features (Optional)

If you choose to implement bonus features:

- **Search**: Implement a search functionality to search for contacts by name or mobile phone number, enhancing the user experience.

## Getting Started

To get started with this application, follow these steps:

1. Clone the repository: `git clone https://github.com/BojanVrshkovski/CatoriTech-Assessment.git`
2. Configure the database settings in `application.properties` or `.yml` using the DATABASE.sql file in the resource folder.
3. Build and run the application using your chosen framework (Spring Boot).
4. Use the API endpoints to manage contacts.

## API Documentation

Comprehensive API documentation, including endpoint descriptions and request/response examples, can be found in the [API Documentation](/docs/API_DOCUMENTATION.md) file.

## Contributing

Contributions to this project are welcome. To contribute, please follow the guidelines outlined in the [CONTRIBUTING.md](CONTRIBUTING.md) file.

## License

This project is licensed under the [MIT License](LICENSE). Feel free to use, modify, and distribute it as needed.

---

Thank you for using the Address Book Backend Application! If you have any questions or need further assistance, please don't hesitate to contact us.
