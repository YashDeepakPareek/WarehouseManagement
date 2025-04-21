package com.warehouse.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import com.warehouse.model.DBSQL; 

public class LoginPage extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private DBSQL db;

    public LoginPage() {
        super("Warehouse Management Login");

        // Initialize the database connection and create a test user.
        db = new DBSQL();
        // db.insertUser("admin", "password123");  // Sample credentials for testing.

        // Configure the login form layout using GridBagLayout.
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label.
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);

        // Username text field.
        txtUsername = new JTextField(15);
        gbc.gridx = 1;
        add(txtUsername, gbc);

        // Password label.
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);

        // Password field.
        txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        add(txtPassword, gbc);

        // Login button.
        btnLogin = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnLogin, gbc);

        // Add action listener to handle login.
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the window.
        setVisible(true);
    }

    // Handle the login action and validate credentials.
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
    
        if (db.validateUser(username, password)) {
            // You can show a login-success message if desired...
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Open the Dashboard, passing the username (you might use this for personalization).
            new Dashboard(username);
            
            // Dispose of the login window so it no longer appears.
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method to launch the login page.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}