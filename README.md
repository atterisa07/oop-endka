# OOP Endterm Project 2525 - University Library System

## Introduction
This is a command-line Java application that models a simple university library system where students can loan books. The application uses JDBC to interact with a SQLite database and demonstrates Object-Oriented Programming principles.

## Project Structure
The project follows a flat structure (no packages) as required:

- `DatabaseConnection.java` - Handles database connection and initialization
- `Student.java` - Student model class
- `Book.java` - Book model class
- `Loan.java` - Loan model class
- `StudentDAO.java` - Data Access Object for Student operations
- `BookDAO.java` - Data Access Object for Book operations
- `LoanDAO.java` - Data Access Object for Loan operations
- `LibraryApp.java` - Main application class with menu system
- `README.md` - This file

## Database Schema
The application uses SQLite database (`library.db`) with three tables:

### Students Table
```sql
CREATE TABLE students(
    sid INT PRIMARY KEY, 
    firstname VARCHAR(50), 
    lastname VARCHAR(50)
)
```

### Books Table
```sql
CREATE TABLE books(
    bid INT PRIMARY KEY, 
    title VARCHAR(50), 
    author VARCHAR(50), 
    pages INT
)
```

### Loans Table
```sql
CREATE TABLE loans(
    lid INTEGER PRIMARY KEY AUTOINCREMENT, 
    sid INT, 
    bid INT, 
    loan_date DATE, 
    return_date DATE,
    FOREIGN KEY (sid) REFERENCES students(sid), 
    FOREIGN KEY (bid) REFERENCES books(bid)
)
```

## Requirements
- Java JDK 8 or higher
- SQLite JDBC Driver (sqlite-jdbc.jar)

## Setup Instructions

1. **Download SQLite JDBC Driver**
   - Download `sqlite-jdbc.jar` from https://github.com/xerial/sqlite-jdbc/releases
   - Place it in the project directory

2. **Compile the project**
   ```bash
   javac -cp sqlite-jdbc.jar *.java
   ```

3. **Run the application**
   ```bash
   java -cp .;sqlite-jdbc.jar LibraryApp
   ```
   (On Linux/Mac, use `:` instead of `;` in the classpath)

## Features

### Level 1 (Basic CRUD Operations)
- **Students:**
  - List all students
  - Add a new student
  - Find a student by ID

- **Books:**
  - List all books
  - Add a new book
  - Find a book by ID

### Level 2 (Loan Management)
- List all loans
- Add a new loan (with validation: student and book must exist, book must be available)
- Return a loaned book

### Level 3 (Advanced Queries)
- List all students who ever took a particular book
- List all loaned but not yet returned books of a student
- Output the total number of pages a student has read (from returned books)

### Level 4 (Custom Commands)
- Find the most popular book (book with the most loans)
- Find the student who loaned the most books
- Calculate average loan duration for a specific book

## Usage Example

```
Welcome to Endterm Project 2525
================================

=== MAIN MENU ===
Level 1 - Students:
  1. List all students
  2. Add a new student
  3. Find a student by ID

Level 1 - Books:
  4. List all books
  5. Add a new book
  6. Find a book by ID

Level 2 - Loans:
  7. List all loans
  8. Add a new loan
  9. Return a loaned book

Level 3 - Advanced Queries:
  10. List all students who ever took a particular book
  11. List all loaned but not yet returned books of a student
  12. Output the total number of pages a student has read

Level 4 - Custom Commands:
  13. Find the most popular book (most loans)
  14. Find the student who loaned the most books
  15. Calculate average loan duration for a book

  0. Exit
Enter your choice:
```

## Design Patterns and Principles

- **DAO Pattern**: Separate data access logic from business logic
- **Single Responsibility**: Each class has a clear, single purpose
- **Error Handling**: Graceful error handling with try-catch blocks
- **Database Connection Management**: Proper connection handling and resource cleanup

## Notes

- The database file (`library.db`) is created automatically on first run
- Date format for loans: YYYY-MM-DD (e.g., 2025-02-13)
- A book can only be loaned to one student at a time
- When returning a book, you need to provide the book ID
- The application validates that students and books exist before creating loans

## Author
Created for OOP Endterm Project 2525
