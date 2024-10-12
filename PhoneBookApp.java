import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PhoneBookApp extends JFrame {
    private DefaultListModel<Contact> contactListModel;
    private JList<Contact> contactList;
    private JTextField searchField;
    private JComboBox<String> categoryComboBox;
    private PhoneBook phoneBook;

    public PhoneBookApp() {
        setTitle("Phone Book");
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // Prevent resizing to maintain layout

        phoneBook = new PhoneBook();

        // Search and Category Filter Panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Contacts"));

        // Create a fading text field for search
        searchField = new JTextField("Search here...");
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search here...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Search here...");
                }
            }
        });
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchContacts();
            }
        });

        // Add search field to the panel
        searchPanel.add(searchField, BorderLayout.CENTER);

        // Category Filter ComboBox
        String[] categories = {"All", "Family", "Friends", "Work"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchContacts();
            }
        });

        // Add category combo box to the panel
        searchPanel.add(categoryComboBox, BorderLayout.SOUTH);

        // Add the search panel to the frame
        add(searchPanel, BorderLayout.NORTH);

        // Contact List
        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);
        contactList.setCellRenderer(new ContactListCellRenderer());
        loadContacts(); // Initially load all contacts

        JScrollPane scrollPane = new JScrollPane(contactList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addButton = new JButton("Add Contact");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewContact();
            }
        });

        JButton updateButton = new JButton("Update Contact");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContact();
            }
        });

        JButton deleteButton = new JButton("Delete Contact");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void searchContacts() {
        String query = searchField.getText().toLowerCase();
        String selectedCategory = (String) categoryComboBox.getSelectedItem();

        List<Contact> results;
        if ("All".equals(selectedCategory)) {
            results = phoneBook.advancedSearch(query);
        } else {
            results = phoneBook.getContactsByCategory(selectedCategory);
        }

        contactListModel.clear();
        for (Contact contact : results) {
            contactListModel.addElement(contact);
        }
    }

    private void loadContacts() {
        contactListModel.clear();
        for (Contact contact : phoneBook.getAllContacts()) {
            contactListModel.addElement(contact);
        }
    }

    private void addNewContact() {
        String name = JOptionPane.showInputDialog(this, "Enter contact name:");
        String phoneNumber = JOptionPane.showInputDialog(this, "Enter phone number:");
        String[] categories = {"Family", "Friends", "Work"};
        String category = (String) JOptionPane.showInputDialog(this, "Select category:",
                "Category", JOptionPane.QUESTION_MESSAGE, null, categories, categories[0]);

        if (name != null && phoneNumber != null && category != null) {
            Contact newContact = new Contact(name, phoneNumber, category);
            phoneBook.addContact(newContact);
            loadContacts();
            JOptionPane.showMessageDialog(this, "Contact added successfully.");
        }
    }

    private void updateContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a contact to update.");
            return;
        }
        Contact selectedContact = contactListModel.getElementAt(selectedIndex);
        String newPhoneNumber = JOptionPane.showInputDialog(this, "Enter new phone number:", selectedContact.getPhoneNumber());
        String newCategory = (String) JOptionPane.showInputDialog(this, "Select new category:",
                "Category", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Family", "Friends", "Work"}, selectedContact.getCategory());

        if (newPhoneNumber != null && newCategory != null) {
            phoneBook.updateContact(selectedContact.getName(), newPhoneNumber, newCategory);
            loadContacts();
            JOptionPane.showMessageDialog(this, "Contact updated successfully.");
        }
    }

    private void deleteContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a contact to delete.");
            return;
        }
        Contact selectedContact = contactListModel.getElementAt(selectedIndex);
        if (phoneBook.deleteContact(selectedContact.getName())) {
            loadContacts();
            JOptionPane.showMessageDialog(this, "Contact deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PhoneBookApp().setVisible(true);
            }
        });
    }
}