# Game Data Management System

## Overview
A web-based database management system for online gaming platforms, featuring comprehensive player data tracking, character management, and inventory systems. Built with Java, MySQL, and JSP technologies.

## Project Structure
```
├── Milestone4_Group12.sql   # Database schema and queries
└── src/
    └── main/
        ├── java/
        │   └── Milestone4/
        │       ├── dal/      # Data Access Layer
        │       ├── model/    # Entity classes
        │       ├── servlet/  # Servlet controllers
        │       └── tools/    # Utility classes
        └── webapp/          # Web interface files
```

## Features
- Player account management
- Character attributes tracking
- Equipment and inventory system
- In-game currency management
- Job/role progression tracking

## Technical Stack
- **Backend**: Java, JDBC
- **Database**: MySQL
- **Frontend**: JSP
- **Architecture**: DAO Pattern
- **Security**: Prepared Statements, Input Validation

## Database Design
- 20+ interconnected entities modeling game elements
- BCNF normalized schema
- Comprehensive foreign key constraints
- Optimized for query performance

## Security Features
- SQL injection prevention
- Data validation rules
- Referential integrity constraints
- Secure CRUD operations

## Setup Instructions
1. Execute `Milestone4_Group12.sql` to create database schema
2. Configure JDBC connection parameters
3. Deploy web application to servlet container
4. Access system through web interface

## API Documentation
The DAO layer provides standardized methods for:
- Player management
- Character operations
- Inventory control
- Currency transactions
- Equipment management
