package databases.com;

import java.sql.*;
import java.util.Scanner;

public class JDBCExample {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String user = "root";
        String password = "Sireesha@146";

        try (Scanner scanner = new Scanner(System.in)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                
            	
                System.out.print("Enter Registration Number: ");
                int regId;
                if (scanner.hasNextInt()) {
                    regId = scanner.nextInt();
                    scanner.nextLine(); 
                } else {
                    System.out.println("Invalid input for Registration Number.");
                    return; 
                }

                System.out.print("Enter Name: ");
                String name = scanner.nextLine();

                System.out.print("Enter Age: ");
                int age;
                if (scanner.hasNextInt()) {
                    age = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    System.out.println("Invalid input for Age.");
                    return; 
                }

                System.out.print("Enter Branch: ");
                String branch = scanner.nextLine();

                System.out.print("Enter Semester: ");
                int sem;
                if (scanner.hasNextInt()) {
                    sem = scanner.nextInt();
                    scanner.nextLine(); 
                } else {
                    System.out.println("Invalid input for Semester.");
                    return;
                }

                System.out.print("Enter Address: ");
                String address = scanner.nextLine();

               
                String insertSql = "INSERT INTO selfdetails (regId, Name, Age, Branch, Sem, Address) VALUES (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    pstmt.setInt(1, regId);
                    pstmt.setString(2, name);
                    pstmt.setInt(3, age);
                    pstmt.setString(4, branch);
                    pstmt.setInt(5, sem);
                    pstmt.setString(6, address);

                    
                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("A new record has been inserted.");
                    } else {
                        System.out.println("Failed to insert a new record.");
                    }
                }

                
                String selectSql = "SELECT * FROM selfdetails";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(selectSql)) {
                    while (rs.next()) {
                        int id = rs.getInt("regId");
                        String db_name = rs.getString("Name");
                        int db_age = rs.getInt("Age");
                        String db_branch = rs.getString("Branch");
                        int db_sem = rs.getInt("Sem");
                        String db_address = rs.getString("Address");

                        System.out.println("Id: " + id + ", Name: " + db_name + ", Age: " + db_age +
                                ", Branch: " + db_branch + ", Semester: " + db_sem +
                                ", Address: " + db_address);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
