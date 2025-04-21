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
            createShiftTable();
            createTaskTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create the Users table.
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (" + "id INTEGER PRIMARY KEY AUTO_INCREMENT, " + "username TEXT UNIQUE NOT NULL, " + "password TEXT NOT NULL);";
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
    
    public List<Object[]> loadEmployees() {
        List<Object[]> employees = new ArrayList<>();
        String sql = "SELECT id, name, role, hourly_rate FROM employees";
    
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String role = rs.getString("role");
                double hourlyRate = rs.getDouble("hourly_rate");
                employees.add(new Object[]{id, name, role, hourlyRate});
            }
            System.out.println("Employee data loaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
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

    public void createShiftTable() {
        String sql = "CREATE TABLE IF NOT EXISTS shifts ("
                   + "shift_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "employee_id INTEGER NOT NULL, "
                   + "shift_duration INTEGER CHECK (shift_duration IN (5, 10)), "
                   + "FOREIGN KEY (employee_id) REFERENCES employees(id)"
                   + ");";
    
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'shifts' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertShift(int employeeId, int shiftDuration) {
        String sql = "INSERT INTO shifts (employee_id, shift_duration) VALUES (?, ?)";
    
        // Validate shift duration before inserting
        if (shiftDuration != 5 && shiftDuration != 10) {
            System.out.println("Invalid shift duration! Only 5 or 10 hours are allowed.");
            return;
        }
    
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            pstmt.setInt(2, shiftDuration);
            pstmt.executeUpdate();
            System.out.println("Shift added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> loadShifts() {
        List<Object[]> shifts = new ArrayList<>();
        String sql = "SELECT shift_id, employee_id, shift_duration FROM shifts";
    
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            while (rs.next()) {
                int shiftId = rs.getInt("shift_id");
                int employeeId = rs.getInt("employee_id");
                int shiftDuration = rs.getInt("shift_duration");
    
                shifts.add(new Object[]{shiftId, employeeId, shiftDuration});
            }
            System.out.println("Shift data loaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shifts;
    }

    public Object[] getEmployeeDetails(int employeeId) {
        String sql = "SELECT id, name, role, hourly_rate FROM employees WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
    
            if (rs.next()) {
                return new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("role"),
                    rs.getDouble("hourly_rate")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Object[]> getShiftsForEmployee(int employeeId) {
        List<Object[]> shifts = new ArrayList<>();
        String sql = "SELECT shift_id, employee_id, shift_duration FROM shifts WHERE employee_id = ?";
    
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                shifts.add(new Object[]{rs.getInt("shift_id"), rs.getInt("employee_id"), rs.getInt("shift_duration")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shifts;
    }

    public void createTaskTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tasks ("
                   + "task_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "title TEXT NOT NULL"
                   + ");";
    
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'tasks' created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTask(int id ,String title) {
        String sql = "INSERT INTO tasks (task_id ,title) VALUES (?,?)";
    
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, title);
            pstmt.executeUpdate();
            System.out.println("Task added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> loadTasks() {
        List<Object[]> tasks = new ArrayList<>();
        String sql = "SELECT task_id, title FROM tasks";
    
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            while (rs.next()) {
                tasks.add(new Object[]{
                    rs.getInt("task_id"),
                    rs.getString("title")

                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void updateTask(int taskId, String title) {
        String sql = "UPDATE tasks SET title = ? WHERE task_id = ?";
    
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setInt(5, taskId);
            int rowsAffected = pstmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Task updated successfully!");
            } else {
                System.out.println("No task found with ID: " + taskId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE task_id = ?";
    
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            int rowsAffected = pstmt.executeUpdate();
    
            if (rowsAffected > 0) {
                System.out.println("Task deleted successfully!");
            } else {
                System.out.println("No task found with ID: " + taskId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}