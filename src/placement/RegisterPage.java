package placement;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterPage extends JFrame {

    JTextField nameField, emailField, deptField, yearField, cgpaField, skillsField;
    JPasswordField passField;

    public RegisterPage() {
        setTitle("Student Registration");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // closes only this window, not the whole app
        setLayout(new GridLayout(8, 2, 10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Department:"));
        deptField = new JTextField();
        add(deptField);

        add(new JLabel("Year (1-4):"));
        yearField = new JTextField();
        add(yearField);

        add(new JLabel("CGPA (0-10):"));
        cgpaField = new JTextField();
        add(cgpaField);

        add(new JLabel("Skills (comma separated):"));
        skillsField = new JTextField();
        add(skillsField);

        add(new JLabel("Password:"));
        passField = new JPasswordField();
        add(passField);

        JButton registerBtn = new JButton("Register");
        add(registerBtn);
        registerBtn.addActionListener(e -> register());
    }

    void register() {
        String name = nameField.getText();
        String email = emailField.getText();
        String department = deptField.getText();
        String password = new String(passField.getPassword());

        // basic validation - beginner style: just check the fields aren't empty
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

            ps.executeUpdate(); // for INSERT/UPDATE/DELETE, we use executeUpdate() not executeQuery()

            JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.");
            dispose(); // closes this registration window

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new RegisterPage().setVisible(true);
    }
}