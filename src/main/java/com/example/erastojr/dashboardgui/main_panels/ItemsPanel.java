package com.example.erastojr.dashboardgui.main_panels;

import com.example.erastojr.dashboardgui.customcomponents.MessageDialogInput;
import com.example.erastojr.dashboardgui.customcomponents.MessageDialogs;
import com.example.erastojr.dashboardgui.customcomponents.RoundPanels;
import com.example.erastojr.database_connection.InventoryItems;
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

public class ItemsPanel implements ActionListener {
    private JPanel itemsPanel;
    private JPanel itemsDataTable;
    private JLabel currentItem;
    private JTable itemsDataTable2;
    int id;
    String itemName;
    String itemQuantity;
    String itemCategory;

    private final JButton addItemButton;
    private final JButton deleteItemButton;
    private final JButton updateItemIDButton;
    private final JButton updateItemNameButton;
    private final JButton updateItemQuantityButton;
    private final JButton updateItemCategoryButton;

    public ItemsPanel() throws SQLException {
        itemsPanel = new RoundPanels(25);
        itemsPanel.setLayout(null);
        itemsPanel.setBounds(270, 10, 1320, 880);
        itemsPanel.setBackground(PURPLE_COLOR);
        itemsDataTable = createTablePanel();
        itemsDataTable.setVisible(true);
        currentItem = currentItemSelectedLabel();
        itemsPanel.add(currentItem);
        itemsPanel.add(itemsDataTable);
        addItemButton = createButton("Add Item", 10, 620);
        deleteItemButton = createButton("Delete Item", 240, 620);
        updateItemIDButton = createButton("Update Item ID", 10, 680);
        updateItemNameButton = createButton("Update Item Name", 240, 680);
        updateItemQuantityButton = createButton("Update Item Quantity", 470, 680);
        updateItemCategoryButton = createButton("Update Item Category", 470, 620);
        itemsPanel.add(addItemButton);
        itemsPanel.add(deleteItemButton);
        itemsPanel.add(updateItemIDButton);
        itemsPanel.add(updateItemQuantityButton);
        itemsPanel.add(updateItemCategoryButton);
        itemsPanel.add(updateItemNameButton);
    }

    public JPanel getPanel() {
        return itemsPanel;
    }

    public JPanel getItemsDataTable() {
        return itemsDataTable;
    }

    private int selectedTableItemID() {
        return id;
    }

    private JLabel currentItemSelectedLabel() {
        JLabel currentItemSelected = new JLabel("Item: ");
        currentItemSelected.setBounds(50, 10, 900, 60);
        currentItemSelected.setFont(new Font("Arial", Font.BOLD, 25));
        currentItemSelected.setForeground(LIGHT_WHITE_BORDER);
        currentItem = currentItemSelected; // Assign the created JLabel to the currentItem variable
        updateCurrentItemLabel(); // Initialize the label text
        return currentItemSelected;
    }

    private void updateCurrentItemLabel() {
        int selectedRow = itemsDataTable2.getSelectedRow();
        int column = 1;
        if (selectedRow >= 0) {
            Object value = itemsDataTable2.getValueAt(selectedRow, column);
            if (value != null) {
                itemName = value.toString();
                itemQuantity = itemsDataTable2.getValueAt(selectedRow, 2).toString();
                id = itemsDataTable2.getValueAt(selectedRow, 0).hashCode();
                itemCategory = itemsDataTable2.getValueAt(selectedRow, 3).toString();

                currentItem.setText("Item: " + itemName + ", ID: " + id + ", Quantity:" + itemQuantity + ", Category: " + itemCategory);
            }
        } else {
            currentItem.setText("Item: "); // No row selected
        }
    }

    private JPanel createTablePanel() throws SQLException {
        //1000, 520
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
        String[] columnNames = {"ID", "Item", "Quantity", "Category"};
        List<InventoryItems> inventoryOfItems = UserConsole.program.viewAllItems();
        Object[][] tableData = new Object[inventoryOfItems.size()][columnNames.length];
        for (int i = 0; i < inventoryOfItems.size(); i++) {
            InventoryItems currentItem = inventoryOfItems.get(i);
            tableData[i] = new Object[]{currentItem.getID(), currentItem.getItemName(),
                    currentItem.getQuantity(), currentItem.getCategory()};
        }
        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames);
        itemsDataTable2 = new JTable(tableModel);
        itemsDataTable2.setPreferredScrollableViewportSize(new Dimension(1290, 500)); //1275 WIDTH
        itemsDataTable2.setFont(new Font("Arial", Font.BOLD, 16));
        itemsDataTable2.setShowHorizontalLines(false);
        itemsDataTable2.setShowVerticalLines(false);
        itemsDataTable2.setRowHeight(35);
        itemsDataTable2.setBackground(Color.WHITE);
        itemsDataTable2.setDefaultEditor(Object.class, null);

        // Add a ListSelectionListener to the itemsDataTable2 table
        itemsDataTable2.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateCurrentItemLabel();
            }
        });

        return itemsDataTable2;
    }

    private void updateTableData() throws SQLException {
        String[] columnNames = {"ID", "Item", "Quantity", "Category"};
        List<InventoryItems> inventoryOfItems = UserConsole.program.viewAllItems();
        Object[][] tableData = new Object[inventoryOfItems.size()][columnNames.length];
        for (int i = 0; i < inventoryOfItems.size(); i++) {
            InventoryItems currentItem = inventoryOfItems.get(i);
            tableData[i] = new Object[]{currentItem.getID(), currentItem.getItemName(),
                    currentItem.getQuantity(), currentItem.getCategory()};
        }
        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames);
        itemsDataTable2.setModel(tableModel);
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
        if(e.getSource() == addItemButton){
            String itemName;
            String itemQuantity;
            String itemCategory;
            MessageDialogInput.messageLabel.setText("Enter the name of the item");
            new MessageDialogInput();
            if(MessageDialogInput.isConfirmed()){
                itemName = MessageDialogInput.textField.getText();
                MessageDialogInput.textField.setText("");
                MessageDialogInput.messageLabel.setText("Enter the quantity of the item");
                new MessageDialogInput();
                if(MessageDialogInput.isConfirmed()){
                    itemQuantity = MessageDialogInput.textField.getText();
                    MessageDialogInput.textField.setText("");
                    MessageDialogInput.messageLabel.setText("Enter the category of the item");
                    new MessageDialogInput();
                    if(MessageDialogInput.isConfirmed()){
                        itemCategory = MessageDialogInput.textField.getText();
                        MessageDialogInput.textField.setText("");
                        try {
                            MessageDialogInput.messageLabel.setText("Enter the ID of the item OR leave blank to auto-generate");
                            new MessageDialogInput();
                            if(MessageDialogInput.isConfirmed()){
                                if(MessageDialogInput.textField.getText().equals("")){
                                    UserConsole.program.addItem((UserConsole.program.getCurrentItemID() + 1), itemName, Integer.parseInt(itemQuantity), itemCategory);
                                    updateTableData();
                                }
                                else{
                                    if(UserConsole.program.itemExists(Integer.parseInt(MessageDialogInput.textField.getText()))){
                                        MessageDialogs.messageLabel.setText("An item with this ID already exists");
                                        new MessageDialogs();
                                        return;
                                    }
                                    UserConsole.program.addItem(Integer.parseInt(MessageDialogInput.textField.getText()),itemName, Integer.parseInt(itemQuantity), itemCategory);
                                    updateTableData();
                                }
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
            }
        }
        else if(e.getSource() == deleteItemButton){
            MessageDialogs.messageLabel.setText("Are you sure you want to delete this item?");
            new MessageDialogs();
            if(MessageDialogs.isConfirmed()){
                try {
                    UserConsole.program.deleteItem(selectedTableItemID());
                    updateTableData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        else if(e.getSource() == updateItemIDButton){
            int newID;
            MessageDialogInput.messageLabel.setText("Enter the new ID of the item");
            new MessageDialogInput();
            if(MessageDialogInput.isConfirmed()){
                try {
                    newID = Integer.parseInt(MessageDialogInput.textField.getText());
                    MessageDialogInput.textField.setText("");
                    if(UserConsole.program.itemExists(newID)){
                        MessageDialogs.messageLabel.setText("An item with this ID already exists");
                        new MessageDialogs();
                        return;
                    }
                    UserConsole.program.editItemID(selectedTableItemID(), newID);
                    MessageDialogInput.textField.setText("");
                    updateTableData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }
        else if(e.getSource() == updateItemNameButton){
            MessageDialogInput.messageLabel.setText("Enter the new name of the item");
            new MessageDialogInput();
            if(MessageDialogInput.isConfirmed()){
                try {
                    UserConsole.program.editItemName(selectedTableItemID(), MessageDialogInput.textField.getText());
                    MessageDialogInput.textField.setText("");
                    updateTableData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        else if(e.getSource() == updateItemQuantityButton){
            MessageDialogInput.messageLabel.setText("Enter the new quantity of the item");
            new MessageDialogInput();
            if(MessageDialogInput.isConfirmed()){
                try {
                    UserConsole.program.editItemQuantity(selectedTableItemID(), MessageDialogInput.textField.getText());
                    MessageDialogInput.textField.setText("");
                    updateTableData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        else if(e.getSource() == updateItemCategoryButton){
            MessageDialogInput.messageLabel.setText("Enter the new category of the item");
            new MessageDialogInput();
            if(MessageDialogInput.isConfirmed()){
                try {
                    UserConsole.program.changeCategory(selectedTableItemID(), MessageDialogInput.textField.getText());
                    MessageDialogInput.textField.setText("");
                    updateTableData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
