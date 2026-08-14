package placement;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterPage extends JFrame {

    JTextField nameField, emailField, deptField, yearField, cgpaField, skillsField;
    JPasswordField passField;

    public RegisterPage() {
        setTitle("Student Registration");
        setSize(480, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 255, 240));

        JLabel heading = new JLabel("Create Student Account", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 12));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Department:"));
        deptField = new JTextField();
        formPanel.add(deptField);

        formPanel.add(new JLabel("Year (1-4):"));
        yearField = new JTextField();
        formPanel.add(yearField);

        formPanel.add(new JLabel("CGPA (0-10):"));
        cgpaField = new JTextField();
        formPanel.add(cgpaField);

        formPanel.add(new JLabel("Skills (comma separated):"));
        skillsField = new JTextField();
        formPanel.add(skillsField);

        formPanel.add(new JLabel("Password:"));
        passField = new JPasswordField();
        formPanel.add(passField);

        add(formPanel, BorderLayout.CENTER);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(40, 160, 90));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        registerBtn.setOpaque(true);
        registerBtn.setBorderPainted(false);
        registerBtn.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        buttonPanel.add(registerBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        registerBtn.addActionListener(e -> register());
    }

    void register() {
        String name = nameField.getText();
        String email = emailField.getText();
        String department = deptField.getText();
        String password = new String(passField.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name, Email and Password are required.");
            return;
        }

        int year;
        double cgpa;
        try {
            year = Integer.parseInt(yearField.getText());
            cgpa = Double.parseDouble(cgpaField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Year must be a whole number, CGPA must be a number (e.g. 8.5).");
            return;
        }

        String sql = "INSERT INTO student (name, email, password, department, year, cgpa, skills) VALUES (?,?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, department);
            ps.setInt(5, year);
            ps.setDouble(6, cgpa);
            ps.setString(7, skillsField.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}