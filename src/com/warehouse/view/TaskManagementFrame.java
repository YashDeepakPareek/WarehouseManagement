package com.warehouse.view;
import com.warehouse.model.DBSQL;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManagementFrame extends JFrame {
    private List<String> tasks;
    private DefaultTableModel addTableModel;
    private JComboBox<String> updateDropdown;
    private DBSQL db;

    
    public TaskManagementFrame() {
        super("Task Management");
        tasks = new ArrayList<>();

        db = new DBSQL();
        db.createTaskTable();

        // Create Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add Task Tab
        JPanel addPanel = new JPanel(new BorderLayout());
        addTableModel = new DefaultTableModel(new String[]{"Index", "Task"}, 0);
        JTable addTable = new JTable(addTableModel);
        JScrollPane addScrollPane = new JScrollPane(addTable);

        // Task Dropdown
        String[] taskOptions = {"Stowing", "Inducting", "Down Stacking"};
        JComboBox<String> taskDropdown = new JComboBox<>(taskOptions);

        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(e -> {
            String selectedTask = (String) taskDropdown.getSelectedItem();
            addTask(selectedTask);
        });
        
        String title = (taskDropdown.getSelectedItem()).toString();
        JPanel addControlPanel = new JPanel();
        addControlPanel.add(new JLabel("Select Task:"));
        addControlPanel.add(taskDropdown);
        addControlPanel.add(addButton);
        db.insertTask(2,title);
        addPanel.add(addControlPanel, BorderLayout.NORTH);
        addPanel.add(addScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Add Tasks", addPanel);

        // Update Task Tab
        JPanel updatePanel = new JPanel(new BorderLayout());

        updateDropdown = new JComboBox<>();
        refreshUpdateDropdown(); // Populate dropdown initially

        JButton updateButton = new JButton("Update Task");
        updateButton.addActionListener(e -> {
            if (tasks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No tasks available to update.", "Update Task", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int taskIndex = updateDropdown.getSelectedIndex();
            if (taskIndex >= 0 && taskIndex < tasks.size()) {
                String updatedTask = (String) JOptionPane.showInputDialog(this,
                        "Select the updated task:", "Update Task", JOptionPane.PLAIN_MESSAGE,
                        null, taskOptions, taskOptions[0]);

                if (updatedTask != null) {
                    updateTask(taskIndex, updatedTask);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid task selection.", "Update Task", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel updateControlPanel = new JPanel();
        updateControlPanel.add(new JLabel("Select Task to Update:"));
        updateControlPanel.add(updateDropdown);
        updateControlPanel.add(updateButton);

        updatePanel.add(updateControlPanel, BorderLayout.NORTH);
        tabbedPane.addTab("Update Tasks", updatePanel);

        // Delete Task Tab
        JPanel deletePanel = new JPanel(new BorderLayout());
        JButton deleteButton = new JButton("Delete Task");
        deleteButton.addActionListener(e -> {
            if (tasks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No tasks available to delete.", "Delete Task", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String taskIndexStr = JOptionPane.showInputDialog(this, "Enter the index of the task to delete (starting at 1):");
            try {
                int taskIndex = Integer.parseInt(taskIndexStr) - 1;
                if (taskIndex >= 0 && taskIndex < tasks.size()) {
                    int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this task?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        deleteTask(taskIndex);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid task index.", "Delete Task", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a number.", "Delete Task", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel deleteControlPanel = new JPanel();
        deleteControlPanel.add(deleteButton);
        deletePanel.add(deleteControlPanel, BorderLayout.NORTH);
        tabbedPane.addTab("Delete Tasks", deletePanel);

        // Add the Tabbed Pane to the Frame
        add(tabbedPane, BorderLayout.CENTER);

        // Set frame properties
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Method to refresh the update dropdown
    private void refreshUpdateDropdown() {
        updateDropdown.removeAllItems();
        for (String task : tasks) {
            updateDropdown.addItem(task);
        }
    }

    // Methods for task management
    public void addTask(String task) {
        tasks.add(task);
        addTableModel.addRow(new Object[]{tasks.size(), task});
        refreshUpdateDropdown(); // Update dropdown list
    }

    public void updateTask(int index, String newTask) {
        tasks.set(index, newTask);

        // Update displayed list of tasks in the Add Table
        addTableModel.setRowCount(0);
        for (int i = 0; i < tasks.size(); i++) {
            addTableModel.addRow(new Object[]{i + 1, tasks.get(i)});
        }

        refreshUpdateDropdown(); // Update dropdown list with new value
    }

    public void deleteTask(int index) {
        tasks.remove(index);

        // Update displayed list of tasks in the Add Table
        addTableModel.setRowCount(0);
        for (int i = 0; i < tasks.size(); i++) {
            addTableModel.addRow(new Object[]{i + 1, tasks.get(i)});
        }

        refreshUpdateDropdown(); // Remove deleted task from dropdown
    }

    // Main method for testing independently
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskManagementFrame().setVisible(true));
    }
}