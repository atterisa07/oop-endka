import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    
    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans ORDER BY lid";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                loans.add(new Loan(
                    rs.getInt("lid"),
                    rs.getInt("sid"),
                    rs.getInt("bid"),
                    rs.getString("loan_date"),
                    rs.getString("return_date")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving loans: " + e.getMessage());
        }
        
        return loans;
    }

    public boolean addLoan(int sid, int bid, String loanDate) {
        String sql = "INSERT INTO loans(sid, bid, loan_date, return_date) VALUES(?, ?, ?, NULL)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, sid);
            pstmt.setInt(2, bid);
            pstmt.setString(3, loanDate);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding loan: " + e.getMessage());
            return false;
        }
    }

    public boolean returnBook(int lid, String returnDate) {
        String sql = "UPDATE loans SET return_date = ? WHERE lid = ? AND return_date IS NULL";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, returnDate);
            pstmt.setInt(2, lid);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error returning book: " + e.getMessage());
            return false;
        }
    }

    public List<Student> getStudentsWhoTookBook(int bid) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT DISTINCT s.sid, s.firstname, s.lastname " +
                     "FROM students s " +
                     "INNER JOIN loans l ON s.sid = l.sid " +
                     "WHERE l.bid = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, bid);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("sid"),
                    rs.getString("firstname"),
                    rs.getString("lastname")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving students who took book: " + e.getMessage());
        }
        
        return students;
    }

    public List<Book> getLoanedBooksByStudent(int sid) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.bid, b.title, b.author, b.pages " +
                     "FROM books b " +
                     "INNER JOIN loans l ON b.bid = l.bid " +
                     "WHERE l.sid = ? AND l.return_date IS NULL";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, sid);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("bid"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("pages")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving loaned books: " + e.getMessage());
        }
        
        return books;
    }

    public int getTotalPagesReadByStudent(int sid) {
        String sql = "SELECT COALESCE(SUM(b.pages), 0) as total_pages " +
                     "FROM books b " +
                     "INNER JOIN loans l ON b.bid = l.bid " +
                     "WHERE l.sid = ? AND l.return_date IS NOT NULL";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, sid);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total_pages");
            }
        } catch (SQLException e) {
            System.err.println("Error calculating total pages: " + e.getMessage());
        }
        
        return 0;
    }

    public Loan findLoanById(int lid) {
        String sql = "SELECT * FROM loans WHERE lid = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, lid);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Loan(
                    rs.getInt("lid"),
                    rs.getInt("sid"),
                    rs.getInt("bid"),
                    rs.getString("loan_date"),
                    rs.getString("return_date")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error finding loan: " + e.getMessage());
        }
        
        return null;
    }

    public Loan findActiveLoanByBookId(int bid) {
        String sql = "SELECT * FROM loans WHERE bid = ? AND return_date IS NULL";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, bid);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Loan(
                    rs.getInt("lid"),
                    rs.getInt("sid"),
                    rs.getInt("bid"),
                    rs.getString("loan_date"),
                    rs.getString("return_date")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error finding active loan: " + e.getMessage());
        }
        
        return null;
    }

    public boolean returnBookByBookId(int bid, String returnDate) {
        String sql = "UPDATE loans SET return_date = ? WHERE bid = ? AND return_date IS NULL";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, returnDate);
            pstmt.setInt(2, bid);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error returning book: " + e.getMessage());
            return false;
        }
    }
}
