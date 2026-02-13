import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY bid";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("bid"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("pages")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
        
        return books;
    }

    public boolean addBook(int bid, String title, String author, int pages) {
        String sql = "INSERT INTO books(bid, title, author, pages) VALUES(?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, bid);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setInt(4, pages);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding book: " + e.getMessage());
            return false;
        }
    }

    public Book findBookById(int bid) {
        String sql = "SELECT * FROM books WHERE bid = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, bid);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Book(
                    rs.getInt("bid"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("pages")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error finding book: " + e.getMessage());
        }
        
        return null;
    }

    public boolean isBookAvailable(int bid) {
        String sql = "SELECT COUNT(*) FROM loans WHERE bid = ? AND return_date IS NULL";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, bid);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking book availability: " + e.getMessage());
        }
        
        return false;
    }
}
