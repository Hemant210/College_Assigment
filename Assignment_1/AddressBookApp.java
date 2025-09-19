package Assignment_1;
// AddressBookApp.java
import javax.swing.*;
import java.awt.*;
import java.util.*;

class Address {
    String name;
    String homeAddress;
    String homePhone;
    String businessAddress;
    String businessPhone;
    String fax;
    String cell;
    String pager;

    public Address(String name, String homeAddress, String homePhone,
                   String businessAddress, String businessPhone,
                   String fax, String cell, String pager) {
        this.name = name; this.homeAddress = homeAddress; this.homePhone = homePhone;
        this.businessAddress = businessAddress; this.businessPhone = businessPhone;
        this.fax = fax; this.cell = cell; this.pager = pager;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class AddressBookApp extends JFrame {
    DefaultListModel<Address> model = new DefaultListModel<>();
    JList<Address> list = new JList<>(model);

    // form fields
    JTextField tfName = new JTextField(20);
    JTextField tfHomeAddr = new JTextField(20);
    JTextField tfHomePhone = new JTextField(15);
    JTextField tfBizAddr = new JTextField(20);
    JTextField tfBizPhone = new JTextField(15);
    JTextField tfFax = new JTextField(15);
    JTextField tfCell = new JTextField(15);
    JTextField tfPager = new JTextField(15);

    public AddressBookApp() {
        super("Address Book (In-Memory)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 400);
        setLayout(new BorderLayout());

        // left: list
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(list), BorderLayout.WEST);
        list.setPreferredSize(new Dimension(200, 0));

        // right: form + buttons
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.anchor = GridBagConstraints.WEST;

        String[] labels = {"Name","Home Addr","Home Phone","Business Addr","Business Phone","Fax","Cell","Pager"};
        JTextField[] fields = {tfName, tfHomeAddr, tfHomePhone, tfBizAddr, tfBizPhone, tfFax, tfCell, tfPager};

        for (int i=0;i<labels.length;i++){
            c.gridx=0; c.gridy=i; form.add(new JLabel(labels[i]+":"), c);
            c.gridx=1; form.add(fields[i], c);
        }

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton findBtn = new JButton("Find (by name)");
        JButton clearBtn = new JButton("Clear");

        buttons.add(addBtn); buttons.add(editBtn); buttons.add(deleteBtn);
        buttons.add(findBtn); buttons.add(clearBtn);

        c.gridx=0; c.gridy=labels.length; c.gridwidth=2;
        form.add(buttons, c);

        add(form, BorderLayout.CENTER);

        // behaviors
        addBtn.addActionListener(e -> {
            Address a = readFromForm();
            if (a != null) {
                model.addElement(a);
                clearForm();
            }
        });

        editBtn.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx >= 0) {
                Address a = readFromForm();
                if (a != null) {
                    model.set(idx, a);
                }
            } else JOptionPane.showMessageDialog(this, "Select a record to edit.");
        });

        deleteBtn.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx >= 0) model.remove(idx);
            else JOptionPane.showMessageDialog(this, "Select a record to delete.");
        });

        findBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Enter name to find:");
            if (name != null) {
                for (int i=0;i<model.size();i++){
                    if (model.get(i).name.equalsIgnoreCase(name.trim())) {
                        list.setSelectedIndex(i);
                        list.ensureIndexIsVisible(i);
                        populateForm(model.get(i));
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Not found.");
            }
        });

        clearBtn.addActionListener(e -> clearForm());

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Address sel = list.getSelectedValue();
                if (sel != null) populateForm(sel);
            }
        });

        // sample data
        model.addElement(new Address("Asha","12 Green St","9876543210","Office Park","022-123456","022-5555","9000000000",""));
        setVisible(true);
    }

    private Address readFromForm() {
        String name = tfName.getText().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Name required"); return null; }
        return new Address(name, tfHomeAddr.getText(), tfHomePhone.getText(),
                tfBizAddr.getText(), tfBizPhone.getText(), tfFax.getText(), tfCell.getText(), tfPager.getText());
    }

    private void populateForm(Address a) {
        tfName.setText(a.name);
        tfHomeAddr.setText(a.homeAddress);
        tfHomePhone.setText(a.homePhone);
        tfBizAddr.setText(a.businessAddress);
        tfBizPhone.setText(a.businessPhone);
        tfFax.setText(a.fax);
        tfCell.setText(a.cell);
        tfPager.setText(a.pager);
    }

    private void clearForm() {
        tfName.setText(""); tfHomeAddr.setText(""); tfHomePhone.setText("");
        tfBizAddr.setText(""); tfBizPhone.setText(""); tfFax.setText("");
        tfCell.setText(""); tfPager.setText("");
        list.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddressBookApp());
    }
}
