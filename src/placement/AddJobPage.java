package placement;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AddJobPage extends JFrame {

    JComboBox<String> companyBox;
    Map<String, Integer> companyMap = new HashMap<>();
    JTextField roleField, packageField, cgpaField, deptField, skillsField;

    public AddJobPage() {
        setTitle("Add Job");
        setSize(450, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230, 240, 255));

        JLabel heading = new JLabel("Add Job", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 18));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 12));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        formPanel.add(new JLabel("Company:"));
        companyBox = new JComboBox<>();
        loadCompanies();
        formPanel.add(companyBox);

        formPanel.add(new JLabel("Role:"));
        roleField = new JTextField();
        formPanel.add(roleField);

        formPanel.add(new JLabel("Package (LPA):"));
        packageField = new JTextField();
        formPanel.add(packageField);

        formPanel.add(new JLabel("Min CGPA:"));
        cgpaField = new JTextField();
        formPanel.add(cgpaField);

        formPanel.add(new JLabel("Department (or 'Any'):"));
        deptField = new JTextField("Any");
        formPanel.add(deptField);

        formPanel.add(new JLabel("Required Skills (comma separated):"));
        skillsField = new JTextField();
        formPanel.add(skillsField);

        add(formPanel, BorderLayout.CENTER);

        JButton saveBtn = new JButton("Save Job");
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

        saveBtn.addActionListener(e -> saveJob());
    }

    void loadCompanies() {
        String sql = "SELECT id, name FROM company";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                companyMap.put(name, id);
                companyBox.addItem(name);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not load companies: " + ex.getMessage());
        }
    }

    void saveJob() {
        if (companyBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Add a company first (there are none yet).");
            return;
        }

        String selectedCompany = (String) companyBox.getSelectedItem();
        int companyId = companyMap.get(selectedCompany);

        double packageLpa, minCgpa;
        try {
            packageLpa = Double.parseDouble(packageField.getText());
            minCgpa = Double.parseDouble(cgpaField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Package and CGPA must be numbers.");
            return;
        }

        String sql = "INSERT INTO job (company_id, role, package_lpa, min_cgpa, department, required_skills) VALUES (?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, companyId);
            ps.setString(2, roleField.getText());
            ps.setDouble(3, packageLpa);
            ps.setDouble(4, minCgpa);
            ps.setString(5, deptField.getText());
            ps.setString(6, skillsField.getText());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Job added successfully!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}