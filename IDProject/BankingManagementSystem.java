import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingManagementSystem {
    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/BankingManagementSystem";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            // Register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to MySQL database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            Scanner scanner = new Scanner(System.in);

            // Example usage of methods
            showAccountDetails(connection, scanner);
            showLoanDetails(connection, scanner);
            depositMoney(connection, scanner);
            withdrawMoney(connection, scanner);

            // Close the connection
            connection.close();
            scanner.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showAccountDetails(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter customer ID to view account details: ");
        int customerId = scanner.nextInt();
        String query = "SELECT * FROM accounts WHERE customer_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, customerId);
        ResultSet resultSet = pstmt.executeQuery();

        while (resultSet.next()) {
            int accountId = resultSet.getInt("account_id");
            double balance = resultSet.getDouble("balance");
            System.out.println("Account ID: " + accountId + ", Balance: " + balance);
        }

        pstmt.close();
    }

    private static void showLoanDetails(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter customer ID to view loan details: ");
        int customerId = scanner.nextInt();
        String query = "SELECT * FROM loans WHERE customer_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, customerId);
        ResultSet resultSet = pstmt.executeQuery();

        while (resultSet.next()) {
            int loanId = resultSet.getInt("loan_id");
            double amount = resultSet.getDouble("amount");
            System.out.println("Loan ID: " + loanId + ", Amount: " + amount);
        }

        pstmt.close();
    }

    private static void depositMoney(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter account ID to deposit money: ");
        int accountId = scanner.nextInt();
        System.out.println("Enter deposit amount: ");
        double amount = scanner.nextDouble();

        String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(depositQuery);
        pstmt.setDouble(1, amount);
        pstmt.setInt(2, accountId);
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Deposited " + amount + " into account " + accountId);
        } else {
            System.out.println("Failed to deposit money into account " + accountId);
        }

        pstmt.close();
    }

    private static void withdrawMoney(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter account ID to withdraw money: ");
        int accountId = scanner.nextInt();
        System.out.println("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();

        double balance = 0.0;
        String balanceQuery = "SELECT balance FROM accounts WHERE account_id = ?";
        PreparedStatement balanceStmt = connection.prepareStatement(balanceQuery);
        balanceStmt.setInt(1, accountId);
        ResultSet resultSet = balanceStmt.executeQuery();
        if (resultSet.next()) {
            balance = resultSet.getDouble("balance");
        }
        balanceStmt.close();

        if (balance >= amount) {
            String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
            PreparedStatement withdrawStmt = connection.prepareStatement(withdrawQuery);
            withdrawStmt.setDouble(1, amount);
            withdrawStmt.setInt(2, accountId);
            int rowsAffected = withdrawStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Withdrawn " + amount + " from account " + accountId);
            } else {
                System.out.println("Failed to withdraw money from account " + accountId);
            }
            withdrawStmt.close();
        } else {
            System.out.println("Insufficient balance in account " + accountId);
        }
    }
}
