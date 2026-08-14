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
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230, 240, 255));

        // ---- Heading ----
        JLabel heading = new JLabel("Campus Placement Portal", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(heading, BorderLayout.NORTH);

        // ---- Form fields ----
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        formPanel.setOpaque(false); // lets the frame's background color show through
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        formPanel.add(new JLabel("Login as:"));
        roleBox = new JComboBox<>(new String[]{"Admin", "Student"});
        formPanel.add(roleBox);

        formPanel.add(new JLabel("Username / Email:"));
        userField = new JTextField();
        formPanel.add(userField);

        formPanel.add(new JLabel("Password:"));
        passField = new JPasswordField();
        formPanel.add(passField);

        add(formPanel, BorderLayout.CENTER);

        // ---- Buttons ----
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        JButton loginBtn = new JButton("Login");
        styleButton(loginBtn);
        JButton exitBtn = new JButton("Exit");
        styleButton(exitBtn);
        JButton registerBtn = new JButton("Register");
        styleButton(registerBtn);

        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);
        buttonPanel.add(exitBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> login());
        exitBtn.addActionListener(e -> System.exit(0));
        registerBtn.addActionListener(e -> new RegisterPage().setVisible(true));
    }

    void styleButton(JButton btn) {
        btn.setBackground(new Color(50, 120, 220));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
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
                if (role.equals("Admin")) {
                    new AdminDashboard().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Login Successful! Welcome, " + role);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoginPage page = new LoginPage();
        page.setVisible(true);
    }
}