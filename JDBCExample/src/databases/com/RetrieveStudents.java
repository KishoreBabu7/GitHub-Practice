package databases.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetrieveStudents {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/books";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Sireesha@146";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");

            
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

           
            String selectQuery = "SELECT * FROM booksdetails";
            pstmt = conn.prepareStatement(selectQuery);

            
            rs = pstmt.executeQuery();

           
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                String price = rs.getString("price");

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Author: " + author);
                System.out.println("Price: " + price);
                System.out.println("------------");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
           
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

