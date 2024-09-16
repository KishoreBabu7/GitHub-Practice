package databases.com;

import java.sql.*;
import java.util.Scanner;

public class JDBCUpdateExample {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String user = "root";
        String password = "Sireesha@146";

        try (Scanner scanner = new Scanner(System.in)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                // Display existing data
                System.out.println("Existing Data in selfdetails Table:");
                displaySelfDetails(conn);

                // Prompt user for update
                System.out.print("\nEnter Registration Number to update: ");
                int regIdToUpdate = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over after nextInt()

                // Validate if the record exists before proceeding to update
                if (!recordExists(conn, regIdToUpdate)) {
                    System.out.println("Record with Registration Number " + regIdToUpdate + " does not exist.");
                    return;
                }

                // Accepting user input for update
                System.out.print("Enter new Name (leave blank to keep current): ");
                String name = scanner.nextLine();

                System.out.print("Enter new Age (-1 to keep current): ");
                int age = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over after nextInt()

                System.out.print("Enter new Branch (leave blank to keep current): ");
                String branch = scanner.nextLine();

                System.out.print("Enter new Semester (-1 to keep current): ");
                int sem = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over after nextInt()

                System.out.print("Enter new Address (leave blank to keep current): ");
                String address = scanner.nextLine();

                // Constructing the SQL update statement based on user input
                StringBuilder updateSql = new StringBuilder("UPDATE selfdetails SET ");

                if (!name.isEmpty()) {
                    updateSql.append("Name = ?, ");
                }
                if (age != -1) {
                    updateSql.append("Age = ?, ");
                }
                if (!branch.isEmpty()) {
                    updateSql.append("Branch = ?, ");
                }
                if (sem != -1) {
                    updateSql.append("Sem = ?, ");
                }
                if (!address.isEmpty()) {
                    updateSql.append("Address = ?, ");
                }

                // Remove the last comma and space if needed
                if (updateSql.charAt(updateSql.length() - 2) == ',') {
                    updateSql.setLength(updateSql.length() - 2);
                }

                updateSql.append(" WHERE regId = ?");

                try (PreparedStatement pstmt = conn.prepareStatement(updateSql.toString())) {
                    int parameterIndex = 1;

                    if (!name.isEmpty()) {
                        pstmt.setString(parameterIndex++, name);
                    }
                    if (age != -1) {
                        pstmt.setInt(parameterIndex++, age);
                    }
                    if (!branch.isEmpty()) {
                        pstmt.setString(parameterIndex++, branch);
                    }
                    if (sem != -1) {
                        pstmt.setInt(parameterIndex++, sem);
                    }
                    if (!address.isEmpty()) {
                        pstmt.setString(parameterIndex++, address);
                    }

                    // Set the Registration Number parameter for WHERE clause
                    pstmt.setInt(parameterIndex, regIdToUpdate);

                    // Execute the update statement
                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Record with Registration Number " + regIdToUpdate + " has been updated.");
                    } else {
                        System.out.println("Failed to update record with Registration Number " + regIdToUpdate);
                    }
                }

                // Display updated data
                System.out.println("\nUpdated Data in selfdetails Table:");
                displaySelfDetails(conn);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to display existing data from selfdetails table
    private static void displaySelfDetails(Connection conn) throws SQLException {
        String selectSql = "SELECT * FROM selfdetails";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSql)) {
            while (rs.next()) {
                int id = rs.getInt("regId");
                String name = rs.getString("Name");
                int age = rs.getInt("Age");
                String branch = rs.getString("Branch");
                int sem = rs.getInt("Sem");
                String address = rs.getString("Address");

                System.out.println("Id: " + id + ", Name: " + name + ", Age: " + age +
                        ", Branch: " + branch + ", Semester: " + sem +
                        ", Address: " + address);
            }
        }
    }

    // Helper method to check if a record with given regId exists
    private static boolean recordExists(Connection conn, int regId) throws SQLException {
        String selectSql = "SELECT regId FROM selfdetails WHERE regId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
            pstmt.setInt(1, regId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // true if record exists, false otherwise
            }
        }
    }
}
