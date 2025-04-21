package com.warehouse.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame {

    public Dashboard(String username) {
        super("Warehouse Management Dashboard");

        // Set the layout
        setLayout(new BorderLayout());

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(welcomeLabel, BorderLayout.NORTH);

        // Panel that holds the functionality buttons
        JPanel functionalityPanel = new JPanel();
        functionalityPanel.setLayout(new GridLayout(2, 2, 10, 10));

        // Create buttons for different functionalities
        JButton inventoryButton = new JButton("Employee Management");
        JButton ordersButton = new JButton("Item Management");
        JButton shipmentsButton = new JButton("Supply Management");
        JButton reportsButton = new JButton("Task Management");

        functionalityPanel.add(inventoryButton);
        functionalityPanel.add(ordersButton);
        functionalityPanel.add(shipmentsButton);
        functionalityPanel.add(reportsButton);
        functionalityPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(functionalityPanel, BorderLayout.CENTER);

        // Window settings
        setSize(500, 400);
        setLocationRelativeTo(null); // Centers the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // -------------------------------
        // Attach Event Listeners to Buttons
        // -------------------------------

        // Inventory Management button
        inventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new InventoryManagementFrame().setVisible(true);
            }
        });

        // Item Management button - Alexandre
        ordersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ItemManagementFrame().setVisible(true);
            }
        });

        // Shipment Tracking button
        shipmentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ShipmentTrackingFrame().setVisible(true);
            }
        });

        // Reports button
        reportsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TaskManagementFrame().setVisible(true);
            }
        });
    }
}