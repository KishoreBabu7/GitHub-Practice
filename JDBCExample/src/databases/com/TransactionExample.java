package databases.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String password = "Sireesha@146";

        Connection connection = null;
        PreparedStatement updateBalanceStmt = null;
        PreparedStatement insertTransactionStmt = null;

        try {
            // Step 1: Load the JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Establish a Connection
            connection = DriverManager.getConnection(url, user, password);

            // Step 3: Disable Auto-commit Mode
            connection.setAutoCommit(false);

            // Step 4: Create SQL statements
            String updateBalanceSQL = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
            String insertTransactionSQL = "INSERT INTO transactions (from_account, to_account, amount) VALUES (?, ?, ?)";

            // Step 5: Create PreparedStatement objects
            updateBalanceStmt = connection.prepareStatement(updateBalanceSQL);
            insertTransactionStmt = connection.prepareStatement(insertTransactionSQL);

            // Transfer details
            int fromAccount = 1;
            int toAccount = 2;
            double amount = 100.0;

            // Step 6: Perform the money transfer
            // Subtract money from the source account
            updateBalanceStmt.setDouble(1, -amount);
            updateBalanceStmt.setInt(2, fromAccount);
            updateBalanceStmt.executeUpdate();

            // Add money to the destination account
            updateBalanceStmt.setDouble(1, amount);
            updateBalanceStmt.setInt(2, toAccount);
            updateBalanceStmt.executeUpdate();

            // Record the transaction
            insertTransactionStmt.setInt(1, fromAccount);
            insertTransactionStmt.setInt(2, toAccount);
            insertTransactionStmt.setDouble(3, amount);
            insertTransactionStmt.executeUpdate();

            // Step 7: Commit the transaction
            connection.commit();

            System.out.println("Transaction completed successfully!");

        } catch (ClassNotFoundException | SQLException e) {
            // Step 8: Rollback the transaction in case of errors
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Transaction rolled back due to error: " + e.getMessage());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            // Step 9: Close the resources
            try {
                if (updateBalanceStmt != null) updateBalanceStmt.close();
                if (insertTransactionStmt != null) insertTransactionStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

