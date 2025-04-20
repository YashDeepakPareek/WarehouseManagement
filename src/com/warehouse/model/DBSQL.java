package com.warehouse.model;
import java.sql.*;

public class DBSQL {
    private Connection connection;

    // Constructor establishes the connection to the database.
    public DBSQL() {
        try {
            // Outdated: Using SQLite for simplicity. Replace the connection string if using another DB.
            // Updated: The connection line now refers to an MySQL DB.
            String url = "jdbc:mysql://localhost:3306/warehouse_db";
            String user = "warehouse_admin";
            String password = "admin123";
            connection = DriverManager.getConnection(url, user, password);

            if (connection == null) {
                System.out.println("Database connection failed!");
            } else {
                System.out.println("Connected to database successfully.");
            }
            // Create the Users table if it does not exist.
            createTable();
            createEmployeeTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create the Users table.
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "username TEXT UNIQUE NOT NULL, " + "password TEXT NOT NULL);";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert a sample user for testing purposes.
    public void insertUser(String username, String password) {
        String sql = "INSERT OR IGNORE INTO Users(username, password) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Validate user-provided credentials against those stored in the database.
    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM login WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true; // Valid login
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createEmployeeTable() {
        String sql = "CREATE TABLE employees ("
                   + "id INT PRIMARY KEY AUTO_INCREMENT, "
                   + "name VARCHAR(50) NOT NULL, "
                   + "role VARCHAR(50) NOT NULL, "
                   + "hourly_rate DECIMAL(10,2) NOT NULL"
                   + ");";
    
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'employees' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insertEmployee(int id, String name, String role, double hourlyRate) {
        String sql = "INSERT INTO employees (id, name, role, hourly_rate) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, role);
            pstmt.setDouble(4, hourlyRate);
            pstmt.executeUpdate();
            System.out.println("Employee added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(int id, String role, double hourlyRate) {
        String sql = "UPDATE employees SET role = ?, hourly_rate = ? WHERE id = ?";
    
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, role);
            pstmt.setDouble(2, hourlyRate);
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("No employee found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
    
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("No employee found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}