package com.example.erastojr.dashboardgui.main_panels;

import com.example.erastojr.dashboardgui.UserInterfaceDashboard;
import com.example.erastojr.dashboardgui.customcomponents.MessageDialogInput;
import com.example.erastojr.dashboardgui.customcomponents.MessageDialogs;
import com.example.erastojr.dashboardgui.customcomponents.RoundPanels;
import com.example.erastojr.database_connection.Contacts;
import com.example.erastojr.main_program.UserConsole;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import static com.example.erastojr.dashboardgui.UserInterfaceDashboard.*;

public class ContactsPanel implements ActionListener {
    private JPanel contactsPanel;
    private JPanel contactsDataTable;
    private JLabel currentContact;
    private JTable contactDataTable2;
    String firstName;
    String lastName;
    String contactNumber;
    String email;
    int id;

    private JButton addContactButton;
    private JButton deleteContactButton;

    private JButton sendMessageButton;

    public ContactsPanel() throws SQLException {
        contactsPanel = new RoundPanels(25);
        contactsPanel.setLayout(null);
        contactsPanel.setBounds(270, 10, 1320, 880);
        contactsPanel.setBackground(UserInterfaceDashboard.PURPLE_COLOR);
        contactsDataTable = createTablePanel();
        contactsDataTable.setVisible(true);
        currentContact = currentContactSelected();
        contactsPanel.add(currentContact);
        contactsPanel.add(contactsDataTable);
        addContactButton = createButton("Add Contact", 10, 620);
        deleteContactButton = createButton("Delete Contact", 240, 620);
        sendMessageButton = createButton("Send Message", 470, 620);
        contactsPanel.add(deleteContactButton);
        contactsPanel.add(addContactButton);
        contactsPanel.add(sendMessageButton);
    }

    public JPanel getPanel() {
        return contactsPanel;
    }

    public JPanel getContactsDataTable() {
        return contactsDataTable;
    }

    public int getCurrentIDContact() {
        return id;
    }

    private JLabel currentContactSelected() {
        JLabel currentContactLabel = new JLabel("Contact: ");
        currentContactLabel.setBounds(50, 10, 900, 60);
        currentContactLabel.setFont(new Font("Arial", Font.BOLD, 25));
        currentContactLabel.setForeground(LIGHT_WHITE_BORDER);
        currentContact = currentContactLabel; // Assign the created JLabel to the currentContact variable
        updateCurrentContactLabel(); // Initialize the label text
        return currentContactLabel;
    }


    private void updateCurrentContactLabel() {
        int selectedRow = contactDataTable2.getSelectedRow();
        int column = 1;
        if (selectedRow >= 0) {
            firstName = (String) contactDataTable2.getValueAt(selectedRow, column);
            lastName = (String) contactDataTable2.getValueAt(selectedRow, column + 1);
            contactNumber = (String) contactDataTable2.getValueAt(selectedRow, column + 2);
            email = (String) contactDataTable2.getValueAt(selectedRow, column + 3);
            id = contactDataTable2.getValueAt(selectedRow, 0).hashCode();
            if (firstName != null) {
                currentContact.setText("Contact: " + firstName + " " + lastName + ", ID: " + id + ", Contact Number: " + contactNumber);
            }
        } else {
            currentContact.setText("Contact: "); // No row selected
        }
    }

    public static JsonNode sendSimpleMessage(String email, String message) throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + "sandboxcf4261d4d807452990e2942b0735a253.mailgun.org" + "/messages")
                .basicAuth("api", "e9651fbab80200521523b0e1a9b0eb90-262b213e-85d585bd")
                .queryString("from", "Thyde's Store <orders@thyde.com>")
                .queryString("to", email)
                .queryString("subject", "Your order is ready!")
                .queryString("text", message)
                .asJson();
        return request.getBody();
    }

    private JPanel createTablePanel() throws SQLException {
        JPanel panel = new JPanel();
        panel.setBounds(10, 85, 1300, 520);
        panel.setBackground(LIGHT_WHITE_BORDER);
        panel.add(scrollPane());
        return panel;
    }

    private JScrollPane scrollPane() throws SQLException {
        JScrollPane scrollPane = new JScrollPane(itemDataTable());
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = PURPLE_COLOR;
            }
        });
        return scrollPane;
    }

    private JTable itemDataTable() throws SQLException {
        String[] columnNames = {"ID", "First Name", "Last Name", "Phone Number", "Email Address"};
        List<Contacts> contacts = UserConsole.program.viewAllContacts();
        Object[][] tableData = new Object[contacts.size()][columnNames.length];
        for (int i = 0; i < contacts.size(); i++) {
            Contacts currentContact = contacts.get(i);
            tableData[i] = new Object[]{currentContact.getContactID(), currentContact.getFirstName(),
                    currentContact.getLastName(), currentContact.getContactNumber(), currentContact.getContactEmail()};
        }
        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames);
        contactDataTable2 = new JTable(tableModel);
        contactDataTable2.setPreferredScrollableViewportSize(new Dimension(1290, 500));
        contactDataTable2.setFont(new Font("Arial", Font.BOLD, 16));
        contactDataTable2.setShowHorizontalLines(false);
        contactDataTable2.setShowVerticalLines(false);
        contactDataTable2.setRowHeight(35);
        contactDataTable2.setBackground(Color.WHITE);
        contactDataTable2.setDefaultEditor(Object.class, null);


        // Add a ListSelectionListener to the contactDataTable2 table
        contactDataTable2.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateCurrentContactLabel();
            }
        });

        return contactDataTable2;
    }

    private void updateTableData() throws SQLException {
        String[] columnNames = {"ID", "First Name", "Last Name", "Phone Number", "Email Address"};
        List<Contacts> contacts = UserConsole.program.viewAllContacts();
        Object[][] tableData = new Object[contacts.size()][columnNames.length];
        for (int i = 0; i < contacts.size(); i++) {
            Contacts currentContact = contacts.get(i);
            tableData[i] = new Object[]{currentContact.getContactID(), currentContact.getFirstName(),
                    currentContact.getLastName(), currentContact.getContactNumber(), currentContact.getContactEmail()};
        }
        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames);
        contactDataTable2.setModel(tableModel);

    }

    public JButton createButton(String nameOfButton, int x, int y) {
        JButton button = new JButton();
        button.setFont(BOLD_FONT);
        button.setText(nameOfButton);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setBounds(x, y, 200, 50);
        button.setHorizontalAlignment(JButton.CENTER);
        button.setFocusable(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addContactButton) {
            String firstName = "";
            String lastName = "";
            String contactNumber = "";
            String contactEmail = "";
            int contactID = 0;
            MessageDialogInput.messageLabel.setText("Enter the contact's first name:");
            new MessageDialogInput();
            if (MessageDialogInput.isConfirmed()) {
                firstName = MessageDialogInput.textField.getText();
                MessageDialogInput.textField.setText("");
                MessageDialogInput.messageLabel.setText("Enter the contact's last name:");
                new MessageDialogInput();
                if (MessageDialogInput.isConfirmed()) {
                    lastName = MessageDialogInput.textField.getText();
                    MessageDialogInput.textField.setText("");
                    MessageDialogInput.messageLabel.setText("Enter the contact's phone number:");
                    new MessageDialogInput();
                    if (MessageDialogInput.isConfirmed()) {
                        contactNumber = MessageDialogInput.textField.getText();
                        MessageDialogInput.textField.setText("");
                        MessageDialogInput.messageLabel.setText("Enter the contact's email address:");
                        new MessageDialogInput();
                        if (MessageDialogInput.isConfirmed()) {
                            contactEmail = MessageDialogInput.textField.getText();
                            MessageDialogInput.textField.setText("");
                            MessageDialogInput.messageLabel.setText("Enter the contact's ID OR leave blank to auto-generate:");
                            new MessageDialogInput();
                            if (MessageDialogInput.isConfirmed()) {
                                if (MessageDialogInput.textField.getText().equals("")) {
                                    try {
                                        UserConsole.program.addContact(UserConsole.program.getCurrentContactID() + 1 ,firstName, lastName, contactNumber, contactEmail);
                                        updateTableData();
                                    } catch (SQLException ex) {
                                        System.out.println("Error adding contact");
                                    }
                                } else {
                                    try {
                                        contactID = Integer.parseInt(MessageDialogInput.textField.getText());
                                        MessageDialogInput.textField.setText("");
                                        UserConsole.program.addContact(contactID, firstName, lastName, contactNumber, contactEmail);
                                        updateTableData();
                                    } catch (SQLException ex) {
                                        System.out.println("Error adding contact");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else if (e.getSource() == deleteContactButton) {
            MessageDialogs.messageLabel.setText("Are you sure you want to delete this contact?");
            new MessageDialogs();
            if (MessageDialogs.isConfirmed()) {
                try {
                    UserConsole.program.deleteContact(getCurrentIDContact());
                    updateTableData();
                } catch (SQLException ex) {
                    System.out.println("Error deleting contact");
                }
            }
        }
        else if(e.getSource() == sendMessageButton){
            MessageDialogInput.messageLabel.setText("Enter the message you would like to send:");
            new MessageDialogInput();
            if (MessageDialogInput.isConfirmed()) {
                String message = MessageDialogInput.textField.getText();
                MessageDialogInput.textField.setText("");
                try {
                    sendSimpleMessage(email, message);
                } catch (UnirestException ex) {
                    throw new RuntimeException(ex);
                }
                MessageDialogs.messageLabel.setText("Message sent!");
                new MessageDialogs();
            }
        }
    }
}
