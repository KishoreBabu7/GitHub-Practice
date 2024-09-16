package databases.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertMultipleStudents {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/student";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Sireesha@146";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");

           
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            String[][] studentsData = {
                {"146", "M.Sireesha", "19", "220101120146@cutm.ac.in"},
                {"149", "P.Gayatri", "19", "220101120149@cutm.ac.in"},
                {"155", "T.Kishore Babu", "19", "220101120155@cutm.ac.in"}
            };

            
            String insertQuery = "INSERT INTO studentinfo (id, name, age, email) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertQuery);

            
            for (String[] student : studentsData) {
                pstmt.setInt(1, Integer.parseInt(student[0]));
                pstmt.setString(2, student[1]);
                pstmt.setInt(3, Integer.parseInt(student[2]));
                pstmt.setString(4, student[3]);
                pstmt.addBatch(); 
            }

            
            int[] affectedRecords = pstmt.executeBatch();
            System.out.println("Inserted " + affectedRecords.length + " records successfully.");

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
