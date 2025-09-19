package Assignment_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

// CREATE TABLE Customers(
//   id INT PRIMARY KEY AUTO_INCREMENT,
//   name VARCHAR(50),
//   homePhone VARCHAR(15),
//   businessPhone VARCHAR(15),
//   fax VARCHAR(15),
//   cell VARCHAR(15),
//   pager VARCHAR(15),
//   maritalStatus VARCHAR(10),
//   children INT,
//   income DOUBLE,
//   photo BLOB
// );


public class RetailApp extends JFrame {
    JTextField txtName = new JTextField(15);
    JTextField txtPhone = new JTextField(10);
    JTextField txtIncome = new JTextField(10);

    JTextArea output = new JTextArea(10, 30);
    Connection con;

    public RetailApp() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/retaildb", "root", "password");
        } catch (Exception ex) { ex.printStackTrace(); }

        setTitle("Retail Company");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3,2));
        panel.add(new JLabel("Name:")); panel.add(txtName);
        panel.add(new JLabel("Phone:")); panel.add(txtPhone);
        panel.add(new JLabel("Income:")); panel.add(txtIncome);

        JButton addBtn = new JButton("Add");
        JButton avgBtn = new JButton("Avg Married Income");
        panel.add(addBtn); panel.add(avgBtn);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);

        addBtn.addActionListener(e -> {
            try {
                PreparedStatement ps = con.prepareStatement("INSERT INTO Customers(name, homePhone, income) VALUES(?,?,?)");
                ps.setString(1, txtName.getText());
                ps.setString(2, txtPhone.getText());
                ps.setDouble(3, Double.parseDouble(txtIncome.getText()));
                ps.executeUpdate();
                output.append("Added customer: " + txtName.getText() + "\n");
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        avgBtn.addActionListener(e -> {
            try {
                CallableStatement cs = con.prepareCall("{call AvgMarriedIncome()}");
                ResultSet rs = cs.executeQuery();
                if (rs.next()) output.append("Avg Married Income: " + rs.getDouble(1) + "\n");
            } catch (Exception ex) { ex.printStackTrace(); }
        });
    }

    public static void main(String[] args) {
        new RetailApp().setVisible(true);
    }
}
