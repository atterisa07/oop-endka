import java.util.List;
import java.util.Scanner;

public class LibraryApp {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentDAO studentDAO = new StudentDAO();
    private static BookDAO bookDAO = new BookDAO();
    private static LoanDAO loanDAO = new LoanDAO();

    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();
        
        System.out.println("Welcome to Endterm Project 2525");
        System.out.println("================================\n");
        
        boolean running = true;
        while (running) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    listAllStudents();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    findStudentById();
                    break;
                case 4:
                    listAllBooks();
                    break;
                case 5:
                    addBook();
                    break;
                case 6:
                    findBookById();
                    break;
                case 7:
                    listAllLoans();
                    break;
                case 8:
                    addLoan();
                    break;
                case 9:
                    returnBook();
                    break;
                case 10:
                    listStudentsWhoTookBook();
                    break;
                case 11:
                    listLoanedBooksByStudent();
                    break;
                case 12:
                    getTotalPagesRead();
                    break;
                case 13:
                    getMostPopularBook();
                    break;
                case 14:
                    getStudentWithMostBooks();
                    break;
                case 15:
                    getAverageLoanDuration();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using the Library System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        DatabaseConnection.closeConnection();
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("Level 1 - Students:");
        System.out.println("  1. List all students");
        System.out.println("  2. Add a new student");
        System.out.println("  3. Find a student by ID");
        System.out.println("\nLevel 1 - Books:");
        System.out.println("  4. List all books");
        System.out.println("  5. Add a new book");
        System.out.println("  6. Find a book by ID");
        System.out.println("\nLevel 2 - Loans:");
        System.out.println("  7. List all loans");
        System.out.println("  8. Add a new loan");
        System.out.println("  9. Return a loaned book");
        System.out.println("\nLevel 3 - Advanced Queries:");
        System.out.println("  10. List all students who ever took a particular book");
        System.out.println("  11. List all loaned but not yet returned books of a student");
        System.out.println("  12. Output the total number of pages a student has read");
        System.out.println("\nLevel 4 - Custom Commands:");
        System.out.println("  13. Find the most popular book (most loans)");
        System.out.println("  14. Find the student who loaned the most books");
        System.out.println("  15. Calculate average loan duration for a book");
        System.out.println("\n  0. Exit");
    }

    // Level 1 - Students
    private static void listAllStudents() {
        System.out.println("\n=== All Students ===");
        List<Student> students = studentDAO.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private static void addStudent() {
        System.out.println("\n=== Add New Student ===");
        int sid = getIntInput("Enter student ID: ");
        
        if (studentDAO.findStudentById(sid) != null) {
            System.out.println("Student with ID " + sid + " already exists!");
            return;
        }
        
        System.out.print("Enter first name: ");
        String firstname = scanner.nextLine().trim();
        System.out.print("Enter last name: ");
        String lastname = scanner.nextLine().trim();
        
        if (studentDAO.addStudent(sid, firstname, lastname)) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Failed to add student.");
        }
    }

    private static void findStudentById() {
        System.out.println("\n=== Find Student by ID ===");
        int sid = getIntInput("Enter student ID: ");
        Student student = studentDAO.findStudentById(sid);
        
        if (student != null) {
            System.out.println("Found: " + student);
        } else {
            System.out.println("Student with ID " + sid + " not found.");
        }
    }

    // Level 1 - Books
    private static void listAllBooks() {
        System.out.println("\n=== All Books ===");
        List<Book> books = bookDAO.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private static void addBook() {
        System.out.println("\n=== Add New Book ===");
        int bid = getIntInput("Enter book ID: ");
        
        if (bookDAO.findBookById(bid) != null) {
            System.out.println("Book with ID " + bid + " already exists!");
            return;
        }
        
        System.out.print("Enter title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter author: ");
        String author = scanner.nextLine().trim();
        int pages = getIntInput("Enter number of pages: ");
        
        if (bookDAO.addBook(bid, title, author, pages)) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Failed to add book.");
        }
    }

    private static void findBookById() {
        System.out.println("\n=== Find Book by ID ===");
        int bid = getIntInput("Enter book ID: ");
        Book book = bookDAO.findBookById(bid);
        
        if (book != null) {
            System.out.println("Found: " + book);
        } else {
            System.out.println("Book with ID " + bid + " not found.");
        }
    }

    // Level 2 - Loans
    private static void listAllLoans() {
        System.out.println("\n=== All Loans ===");
        List<Loan> loans = loanDAO.getAllLoans();
        if (loans.isEmpty()) {
            System.out.println("No loans found.");
        } else {
            for (Loan loan : loans) {
                System.out.println(loan);
            }
        }
    }

    private static void addLoan() {
        System.out.println("\n=== Add New Loan ===");
        int sid = getIntInput("Enter student ID: ");
        Student student = studentDAO.findStudentById(sid);
        if (student == null) {
            System.out.println("Student with ID " + sid + " not found!");
            return;
        }
        
        int bid = getIntInput("Enter book ID: ");
        Book book = bookDAO.findBookById(bid);
        if (book == null) {
            System.out.println("Book with ID " + bid + " not found!");
            return;
        }
        
        if (!bookDAO.isBookAvailable(bid)) {
            System.out.println("Book with ID " + bid + " is currently loaned out!");
            return;
        }
        
        System.out.print("Enter loan date (YYYY-MM-DD): ");
        String loanDate = scanner.nextLine().trim();
        
        if (loanDAO.addLoan(sid, bid, loanDate)) {
            System.out.println("Loan added successfully!");
        } else {
            System.out.println("Failed to add loan.");
        }
    }

    private static void returnBook() {
        System.out.println("\n=== Return a Book ===");
        int bid = getIntInput("Enter book ID: ");
        
        if (bookDAO.findBookById(bid) == null) {
            System.out.println("Book with ID " + bid + " not found!");
            return;
        }
        
        Loan loan = loanDAO.findActiveLoanByBookId(bid);
        if (loan == null) {
            System.out.println("Book with ID " + bid + " is not currently loaned out!");
            return;
        }
        
        System.out.print("Enter return date (YYYY-MM-DD): ");
        String returnDate = scanner.nextLine().trim();
        
        if (loanDAO.returnBookByBookId(bid, returnDate)) {
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Failed to return book.");
        }
    }

    // Level 3 - Advanced Queries
    private static void listStudentsWhoTookBook() {
        System.out.println("\n=== Students Who Took a Book ===");
        int bid = getIntInput("Enter book ID: ");
        
        if (bookDAO.findBookById(bid) == null) {
            System.out.println("Book with ID " + bid + " not found!");
            return;
        }
        
        List<Student> students = loanDAO.getStudentsWhoTookBook(bid);
        if (students.isEmpty()) {
            System.out.println("No students have loaned this book.");
        } else {
            System.out.println("Students who loaned book ID " + bid + ":");
            for (Student student : students) {
                System.out.println("  - " + student);
            }
        }
    }

    private static void listLoanedBooksByStudent() {
        System.out.println("\n=== Loaned Books by Student ===");
        int sid = getIntInput("Enter student ID: ");
        
        if (studentDAO.findStudentById(sid) == null) {
            System.out.println("Student with ID " + sid + " not found!");
            return;
        }
        
        List<Book> books = loanDAO.getLoanedBooksByStudent(sid);
        if (books.isEmpty()) {
            System.out.println("Student has no currently loaned books.");
        } else {
            System.out.println("Currently loaned books for student ID " + sid + ":");
            for (Book book : books) {
                System.out.println("  - " + book);
            }
        }
    }

    private static void getTotalPagesRead() {
        System.out.println("\n=== Total Pages Read by Student ===");
        int sid = getIntInput("Enter student ID: ");
        
        if (studentDAO.findStudentById(sid) == null) {
            System.out.println("Student with ID " + sid + " not found!");
            return;
        }
        
        int totalPages = loanDAO.getTotalPagesReadByStudent(sid);
        System.out.println("Total pages read by student ID " + sid + ": " + totalPages);
    }

    // Level 4 - Custom Commands
    private static void getMostPopularBook() {
        System.out.println("\n=== Most Popular Book ===");
        String sql = "SELECT b.bid, b.title, b.author, COUNT(l.lid) as loan_count " +
                     "FROM books b " +
                     "LEFT JOIN loans l ON b.bid = l.bid " +
                     "GROUP BY b.bid, b.title, b.author " +
                     "ORDER BY loan_count DESC " +
                     "LIMIT 1";
        
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                int bid = rs.getInt("bid");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int loanCount = rs.getInt("loan_count");
                
                System.out.println("Most popular book:");
                System.out.println("  Book ID: " + bid);
                System.out.println("  Title: " + title);
                System.out.println("  Author: " + author);
                System.out.println("  Total loans: " + loanCount);
            } else {
                System.out.println("No books found in the database.");
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error finding most popular book: " + e.getMessage());
        }
    }

    private static void getStudentWithMostBooks() {
        System.out.println("\n=== Student Who Loaned Most Books ===");
        String sql = "SELECT s.sid, s.firstname, s.lastname, COUNT(l.lid) as loan_count " +
                     "FROM students s " +
                     "LEFT JOIN loans l ON s.sid = l.sid " +
                     "GROUP BY s.sid, s.firstname, s.lastname " +
                     "ORDER BY loan_count DESC " +
                     "LIMIT 1";
        
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                int sid = rs.getInt("sid");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                int loanCount = rs.getInt("loan_count");
                
                System.out.println("Student who loaned the most books:");
                System.out.println("  Student ID: " + sid);
                System.out.println("  Name: " + firstname + " " + lastname);
                System.out.println("  Total loans: " + loanCount);
            } else {
                System.out.println("No students found in the database.");
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error finding student with most books: " + e.getMessage());
        }
    }

    private static void getAverageLoanDuration() {
        System.out.println("\n=== Average Loan Duration for a Book ===");
        int bid = getIntInput("Enter book ID: ");
        
        if (bookDAO.findBookById(bid) == null) {
            System.out.println("Book with ID " + bid + " not found!");
            return;
        }
        
        String sql = "SELECT AVG(julianday(return_date) - julianday(loan_date)) as avg_days " +
                     "FROM loans " +
                     "WHERE bid = ? AND return_date IS NOT NULL";
        
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, bid);
            java.sql.ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                double avgDays = rs.getDouble("avg_days");
                if (!rs.wasNull()) {
                    System.out.println("Average loan duration for book ID " + bid + ": " + 
                                     String.format("%.2f", avgDays) + " days");
                } else {
                    System.out.println("No returned loans found for this book.");
                }
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error calculating average loan duration: " + e.getMessage());
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }
}
