package com.example.erastojr.database_connection;

import com.example.erastojr.dashboardgui.customcomponents.MessageDialogs;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLConnectionDB {
    private Connection connection;
    private Statement statement;
    private int result;
    private ResultSet resultSet;
    private boolean orderQuantitySwitch = false;
    private boolean orderNameSwitch = false;


    public void startConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/InventoryProject?allowLoadLocalInfile=true", "root", "Jacob361");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void addContact(int id, String firstName, String lastName, String phoneNumber, String email) throws SQLException {
        statement = connection.createStatement();
        result = statement.executeUpdate("INSERT INTO Contacts (idContacts, firstName, lastName, phoneNumber, emailAddress) VALUES ('" + id + "', '" + firstName + "', '" + lastName + "', '" + phoneNumber + "', '" + email + "')");
        System.out.println(result > 0 ? "Added Contact" : "Failed to Add Contact!");
    }

    public void addItem(int id, String item, int itemQuantity, String category) throws SQLException {
        statement = connection.createStatement();
        if(item.contains("'"))
            item = item.replace("'", "''");
        result = statement.executeUpdate("INSERT INTO Items (idItem, item, itemQuantity, Category) VALUES ('" + id + "', '" + item + "', '" + itemQuantity + "', '" + category + "')");
        System.out.println(result > 0 ? "Added Item" : "Failed to Add Item!");
    }

    public List<InventoryItems> viewAllItems() throws SQLException {
        List<InventoryItems> itemList = new ArrayList<>();
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM Items");
        retrieveItems(itemList);
        return itemList;
    }

    public List<Contacts> viewAllContacts() throws SQLException {
        List<Contacts> itemList = new ArrayList<>();
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM Contacts");
        retrieveContacts(itemList);
        return itemList;
    }

    public int itemCount() throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT COUNT(*) FROM Items");
        int count = 0;
        while (resultSet.next()) {
            count += resultSet.getInt(1);
        }
        return count;
    }

    public int contactCount() throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT COUNT(*) FROM Contacts");
        int count = 0;
        while (resultSet.next()) {
            count += resultSet.getInt(1);
        }
        return count;
    }

    public void searchItem(String itemToSearchFor) throws SQLException {
        List<InventoryItems> itemList = new ArrayList<>();
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM Items WHERE item = '" + itemToSearchFor + "'");
        retrieveItems(itemList);
    }

    public List<InventoryItems> retrieveItems(List<InventoryItems> itemList) throws SQLException {
        while (resultSet.next()) {
            int idItem = resultSet.getInt(1);
            String item = resultSet.getString("item");
            int itemQuantity = resultSet.getInt("itemQuantity");
            String category = resultSet.getString("Category");
            InventoryItems itemToStore = new InventoryItems(idItem, item, itemQuantity, category);
            itemList.add(itemToStore);
        }
        return itemList;
    }

    public List<Contacts> retrieveContacts(List<Contacts> itemList) throws SQLException {
        while (resultSet.next()) {
            int idContact = resultSet.getInt(1);
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String phoneNumber = resultSet.getString("phoneNumber");
            String emailAddress = resultSet.getString("emailAddress");
            Contacts contact = new Contacts(idContact, firstName, lastName, phoneNumber, emailAddress);
            itemList.add(contact);
        }
        return itemList;
    }


    public void deleteItem(int idToDelete) throws SQLException {
        statement = connection.createStatement();
        result = statement.executeUpdate("DELETE FROM Items WHERE idItem = '" + idToDelete + "'");
        String message = result > 0 ? "Successfully Deleted!" : "Failed! Ensure to select an item to delete!";
        MessageDialogs.messageLabel.setText(message);
        new MessageDialogs();
    }

    public void deleteContact(int idToDelete) throws SQLException {
        statement = connection.createStatement();
        result = statement.executeUpdate("DELETE FROM Contacts WHERE idContacts = '" + idToDelete + "'");
        String message = result > 0 ? "Successfully Deleted ID" : "Failed! Ensure to select a contact to delete!";
        MessageDialogs.messageLabel.setText(message);
        new MessageDialogs();
    }

    public int getSpecificItemID(String itemToGet) throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT idItem FROM Items WHERE item = '" + itemToGet + "'");
        int specificItemID = 0;
        while (resultSet.next()) {
            specificItemID += resultSet.getInt(1);
        }
        return specificItemID;
    }

    public void editItemName(int id, String newName) throws SQLException {
        statement = connection.createStatement();
        result = statement.executeUpdate("UPDATE Items SET item = '" + newName + "' WHERE idItem = '" + id + "'");
        if (result > 0) {
            MessageDialogs.messageLabel.setText("Successfully Edited Item Name!");
            new MessageDialogs();
        } else {
            MessageDialogs.messageLabel.setText("Failed to Edit Item Name!");
            new MessageDialogs();
        }
    }

    public void editItemQuantity(int id, String newValue) throws SQLException {
        statement = connection.createStatement();
        result = statement.executeUpdate("UPDATE Items SET itemQuantity = '" + newValue + "' WHERE idItem = '" + id + "'");
        if (result > 0) {
            MessageDialogs.messageLabel.setText("Successfully Edited Item Quantity!");
            new MessageDialogs();
        } else {
            MessageDialogs.messageLabel.setText("Failed to Edit Item Quantity!");
            new MessageDialogs();
        }
    }

    public int getCurrentItemID() throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT MAX(idItem) FROM Items");
        int currentItemID = 0;
        while (resultSet.next()) {
            currentItemID += resultSet.getInt(1);
        }
        return currentItemID;
    }

    public void viewCategories() throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT DISTINCT Category FROM Categories");
        while (resultSet.next()) {
            String availableCategories = resultSet.getString("Category");
            System.out.print(availableCategories + " | ");
        }
        System.out.println();
    }

    public void viewItemsInCategory(String categoryItemsToView) throws SQLException {
        List<InventoryItems> itemList = new ArrayList<>();
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM Items WHERE Category = '" + categoryItemsToView + "'");
        retrieveItems(itemList);
    }

    public int getSpecificCategoryID(String categoryToGetIDFrom) throws SQLException {
        statement = connection.createStatement();
        int currentCategoryID = 0;
        resultSet = statement.executeQuery("SELECT idCategories FROM Categories WHERE Category = '" + categoryToGetIDFrom + "'");
        while (resultSet.next()) {
            currentCategoryID = resultSet.getInt(1);
        }
        return currentCategoryID;
    }

    public void sortByQuantity() throws SQLException {
        List<InventoryItems> itemList = new ArrayList<>();
        statement = connection.createStatement();
        if (!orderQuantitySwitch) {
            resultSet = statement.executeQuery("SELECT * FROM Items ORDER BY itemQuantity DESC");
            retrieveItems(itemList);
            orderQuantitySwitch = true;
        } else {
            resultSet = statement.executeQuery("SELECT * FROM Items ORDER BY itemQuantity");
            retrieveItems(itemList);
            orderQuantitySwitch = false;
        }
    }

    public void sortByName() throws SQLException {
        List<InventoryItems> itemList = new ArrayList<>();
        statement = connection.createStatement();
        if (!orderNameSwitch) {
            resultSet = statement.executeQuery("SELECT * FROM Items ORDER BY item");
            retrieveItems(itemList);
            orderNameSwitch = true;
        } else {
            resultSet = statement.executeQuery("SELECT * FROM Items ORDER BY item DESC");
            retrieveItems(itemList);
            orderNameSwitch = false;
        }
    }

    public void importFile(String fileToImport) throws SQLException {
        statement = connection.createStatement();
        try {
            result = statement.executeUpdate("LOAD DATA LOCAL INFILE '" + fileToImport + "' INTO TABLE Items FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 ROWS;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result > 0 ? "Imported File!" : "Failed To Import File");
    }

    public void exportFile(String pathToExportTo) throws SQLException {
        statement = connection.createStatement();
        try {
            String exportQuery = "SELECT * FROM Items;";
            ResultSet resultSet = statement.executeQuery(exportQuery);

            try (FileWriter writer = new FileWriter(pathToExportTo)) {
                // Write column headers
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    writer.append(columnName);
                    if (i < columnCount) {
                        writer.append(",");
                    }
                }
                writer.append("\n");

                // Write data rows
                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        String value = resultSet.getString(i);
                        writer.append(value);
                        if (i < columnCount) {
                            writer.append(",");
                        }
                    }
                    writer.append("\n");
                }

                System.out.println("Export successful!");
            } catch (IOException e) {
                System.out.println("Export failed!");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean itemExists(int i) throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM Items WHERE idItem = '" + i + "'");
        return resultSet.next();
    }

    public void changeCategory(int id, String newCategory) throws SQLException {
        statement = connection.createStatement();
        result = statement.executeUpdate("UPDATE Items SET Category = '" + newCategory + "' WHERE idItem = '" + id + "'");
        if (result > 0) {
            MessageDialogs.messageLabel.setText("Successfully Changed Category!");
            new MessageDialogs();
        } else {
            MessageDialogs.messageLabel.setText("Failed to Change Category!");
            new MessageDialogs();
        }
    }

    public boolean categoryExists(int categoryID) throws SQLException {
        statement = connection.createStatement();
        result = statement.executeUpdate("SELECT * FROM Categories WHERE idCategories = '" + categoryID + "'");
        return result > 0;
    }

    public void editItemID(int id, int newID) throws SQLException {
        statement = connection.createStatement();
        result = statement.executeUpdate("UPDATE Items SET idItem = '" + newID + "' WHERE idItem = '" + id + "'");
        if (result > 0) {
            MessageDialogs.messageLabel.setText("Successfully Changed ID!");
            new MessageDialogs();
        } else {
            MessageDialogs.messageLabel.setText("Failed to Change ID!");
            new MessageDialogs();
        }
    }

    public int getCurrentContactID() throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT MAX(idContacts) FROM Contacts");
        int currentContactID = 0;
        while (resultSet.next()) {
            currentContactID = resultSet.getInt(1);
        }
        return currentContactID;
    }
}