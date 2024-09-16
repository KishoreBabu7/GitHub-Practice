package databases.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MysqlCon {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/books";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Sireesha@146";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
          
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            
            String[][] booksData = {
                {"1", "Java Programming", "John Doe", "Rs.290.99"},
                {"2", "Python Basics", "Jane Smith", "Rs.159.99"},
                {"3", "SQL Fundamentals", "David Lee", "Rs.124.99"}
            };

            String insertQuery = "INSERT INTO booksdetails (id, name, author, price) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertQuery);
          
            for (String[] book : booksData) {
                pstmt.setInt(1, Integer.parseInt(book[0])); 
                pstmt.setString(2, book[1]); 
                pstmt.setString(3, book[2]); 
                pstmt.setString(4, book[3]); 
                pstmt.executeUpdate(); 
            }

            System.out.println("Data inserted successfully.");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
