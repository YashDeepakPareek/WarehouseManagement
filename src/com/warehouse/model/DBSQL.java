package com.warehouse.model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT category FROM ItemCategory";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                categories.add(resultSet.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public void insertItemCategory(String category) {
        String insertCategorySQL = "INSERT OR IGNORE INTO ItemCategory(category) VALUES(?)";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(insertCategorySQL)) {
            preparedStatement.setString(1, category);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertItem(String description, double height, double width, double depth, double volume, String location, int quantity_in_stock, String category) {
        insertItemCategory(category);

        int categoryId = getCategoryId(category);

        String insertItemSQL = "INSERT INTO Item(description, height, width, depth, volume, location, quantity_in_stock, category_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(insertItemSQL)) {
        preparedStatement.setString(1, description);
        preparedStatement.setDouble(2, height);
        preparedStatement.setDouble(3, width);
        preparedStatement.setDouble(4, depth);
        preparedStatement.setDouble(5, volume);
        preparedStatement.setString(6, location);
        preparedStatement.setInt(7, quantity_in_stock);
        preparedStatement.setInt(8, categoryId);
        preparedStatement.executeUpdate();
        } catch (SQLException e) {
        e.printStackTrace();
        }
    }

    private int getCategoryId(String category) {
        String query = "SELECT id FROM ItemCategory WHERE category = ?";
        int categoryId = -1;

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                categoryId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryId;
    }
}