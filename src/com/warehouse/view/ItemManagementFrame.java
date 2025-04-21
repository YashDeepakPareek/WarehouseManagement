package com.warehouse.view;

import javax.swing.*;

import com.warehouse.control.ItemManagment;
import com.warehouse.model.DBSQL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ItemManagementFrame extends JFrame {
    public ItemManagementFrame() {
        super("Item Management");

        DBSQL db = new DBSQL();
        ItemManagment itemManagment = new ItemManagment(db);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel createPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblDescription = new JLabel("Description:");
        JTextField txtDescription = new JTextField(20);

        JLabel lblHeight = new JLabel("Height:");
        JTextField txtHeight = new JTextField(10);

        JLabel lblWidth = new JLabel("Width:");
        JTextField txtWidth = new JTextField(10);

        JLabel lblDepth = new JLabel("Depth:");
        JTextField txtDepth = new JTextField(10);

        JLabel lblVolume = new JLabel("Volume:");
        JTextField txtVolume = new JTextField(10);
        txtVolume.setEditable(false);

        JLabel lblLocation = new JLabel("Location:");
        JTextField txtLocation = new JTextField(15);

        JLabel lblQuantity = new JLabel("Quantity in Stock:");
        JTextField txtQuantity = new JTextField(5);

        JLabel lblCategory = new JLabel("Category:");
        JComboBox<String> cmbCategory = new JComboBox<>();

        List<String> categories = db.getAllCategories();
        if (categories.isEmpty()) {
            categories.add("General");
        }
        for (String cat : categories) {
            cmbCategory.addItem(cat);
        }

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        createPanel.add(lblDescription, gbc);
        gbc.gridx = 1;
        createPanel.add(txtDescription, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        createPanel.add(lblHeight, gbc);
        gbc.gridx = 1;
        createPanel.add(txtHeight, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        createPanel.add(lblWidth, gbc);
        gbc.gridx = 1;
        createPanel.add(txtWidth, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        createPanel.add(lblDepth, gbc);
        gbc.gridx = 1;
        createPanel.add(txtDepth, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        createPanel.add(lblVolume, gbc);
        gbc.gridx = 1;
        createPanel.add(txtVolume, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        createPanel.add(lblLocation, gbc);
        gbc.gridx = 1;
        createPanel.add(txtLocation, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        createPanel.add(lblQuantity, gbc);
        gbc.gridx = 1;
        createPanel.add(txtQuantity, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        createPanel.add(lblCategory, gbc);
        gbc.gridx = 1;
        createPanel.add(cmbCategory, gbc);

        JButton btnAdd = new JButton("Add Item");
        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        createPanel.add(btnAdd, gbc);

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double height = Double.parseDouble(txtHeight.getText());
                    double width = Double.parseDouble(txtWidth.getText());
                    double depth = Double.parseDouble(txtDepth.getText());
                    double volume = height * width * depth;
                    txtVolume.setText(String.valueOf(volume));

                    String description = txtDescription.getText();
                    String location = txtLocation.getText();
                    int quantity = Integer.parseInt(txtQuantity.getText());
                    String category = cmbCategory.getSelectedItem().toString();

                    itemManagment.AddItem(height, width, depth, volume, description, location, quantity, category);

                    JOptionPane.showMessageDialog(createPanel, "Item added:\n" +
                        "Description: " + description + "\n" +
                        "Volume: " + volume + "\n" +
                        "Location: " + location + "\n" +
                        "Quantity: " + quantity + "\n" +
                        "Category: " + category);

                    txtDescription.setText("");
                    txtHeight.setText("");
                    txtWidth.setText("");
                    txtDepth.setText("");
                    txtVolume.setText("");
                    txtLocation.setText("");
                    txtQuantity.setText("");
                    cmbCategory.setSelectedIndex(0);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(createPanel, "Please enter valid numeric values for height, width, depth, and quantity.");
                }
            }
        });

        tabbedPane.addTab("Add", createPanel);
        tabbedPane.addTab("Search", new JPanel(new BorderLayout()));
        tabbedPane.addTab("Update", new JPanel(new BorderLayout()));
        tabbedPane.addTab("Remove", new JPanel(new BorderLayout()));

        add(tabbedPane, BorderLayout.CENTER);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ItemManagementFrame().setVisible(true));
    }
}
