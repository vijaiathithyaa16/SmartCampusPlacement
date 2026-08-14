package placement;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddCompanyPage extends JFrame {

    JTextField nameField, locationField;

    public AddCompanyPage() {
        setTitle("Add Company");
        setSize(400, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230, 240, 255));

        JLabel heading = new JLabel("Add Company", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 18));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        formPanel.add(new JLabel("Company Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Location:"));
        locationField = new JTextField();
        formPanel.add(locationField);

        add(formPanel, BorderLayout.CENTER);

        JButton saveBtn = new JButton("Save Company");
        saveBtn.setBackground(new Color(50, 120, 220));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setOpaque(true);
        saveBtn.setBorderPainted(false);
        saveBtn.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        buttonPanel.add(saveBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> saveCompany());
    }

    void saveCompany() {
        String name = nameField.getText();
        String location = locationField.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Company name is required.");
            return;
        }

        String sql = "INSERT INTO company (name, location) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, location);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Company added successfully!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}