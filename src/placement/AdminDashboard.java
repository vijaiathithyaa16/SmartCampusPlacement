package placement;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 250, 240));

        JLabel heading = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));
        heading.setBorder(BorderFactory.createEmptyBorder(25, 0, 15, 0));
        add(heading, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 30, 60));

        JButton addCompanyBtn = new JButton("Add Company");
        JButton addJobBtn = new JButton("Add Job");
        JButton logoutBtn = new JButton("Logout");

        for (JButton b : new JButton[]{addCompanyBtn, addJobBtn, logoutBtn}) {
            b.setFont(new Font("SansSerif", Font.BOLD, 14));
            b.setBackground(new Color(230, 150, 40));
            b.setForeground(Color.WHITE);
            b.setOpaque(true);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
        }

        buttonPanel.add(addCompanyBtn);
        buttonPanel.add(addJobBtn);
        buttonPanel.add(logoutBtn);

        add(buttonPanel, BorderLayout.CENTER);

        addCompanyBtn.addActionListener(e -> new AddCompanyPage().setVisible(true));
        addJobBtn.addActionListener(e -> new AddJobPage().setVisible(true));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginPage().setVisible(true);
        });
    }
}