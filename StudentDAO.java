import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY sid";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("sid"),
                    rs.getString("firstname"),
                    rs.getString("lastname")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }
        
        return students;
    }

    public boolean addStudent(int sid, String firstname, String lastname) {
        String sql = "INSERT INTO students(sid, firstname, lastname) VALUES(?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, sid);
            pstmt.setString(2, firstname);
            pstmt.setString(3, lastname);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    public Student findStudentById(int sid) {
        String sql = "SELECT * FROM students WHERE sid = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, sid);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Student(
                    rs.getInt("sid"),
                    rs.getString("firstname"),
                    rs.getString("lastname")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error finding student: " + e.getMessage());
        }
        
        return null;
    }
}
