package placement;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginPage extends JFrame {

    JComboBox<String> roleBox;
    JTextField userField;
    JPasswordField passField;

    public LoginPage() {
        setTitle("Campus Placement - Login");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(new JLabel("Login as:"));
        roleBox = new JComboBox<>(new String[] { "Admin", "Student" });
        add(roleBox);

        add(new JLabel("Username / Email:"));
        userField = new JTextField();
        add(userField);

        add(new JLabel("Password:"));
        passField = new JPasswordField();
        add(passField);

        JButton loginBtn = new JButton("Login");
        add(loginBtn);
        JButton exitBtn = new JButton("Exit");
        add(exitBtn);
        JButton registerBtn = new JButton("New Student? Register");
        add(registerBtn);

        loginBtn.addActionListener(e -> login());
        exitBtn.addActionListener(e -> System.exit(0));
        registerBtn.addActionListener(e -> new RegisterPage().setVisible(true));
    }

    void login() {
        String role = (String) roleBox.getSelectedItem();
        String username = userField.getText();
        String password = new String(passField.getPassword());

        String table = role.equals("Admin") ? "admin" : "student";
        String column = role.equals("Admin") ? "username" : "email";

        String sql = "SELECT * FROM " + table + " WHERE " + column + " = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful! Welcome, " + role);

            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LoginPage page = new LoginPage();
        page.setVisible(true);
    }
}