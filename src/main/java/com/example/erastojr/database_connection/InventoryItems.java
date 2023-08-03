package com.example.erastojr.database_connection;

public class InventoryItems {
    //Item ID
    private final int idItem;
    //Item Name When Creating New Item
    private final String itemName;
    //Amount of New Item When Creating New Item
    private final int quantity;
    //Category Of Item
    private final String category;
    //Constructor of New Item
    public InventoryItems(int idItem, String itemName, int quantity, String category){
        this.idItem = idItem;
        this.itemName = itemName;
        this.quantity = quantity;
        this.category = category;
    }

    public int getID() {
        return idItem;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String toString(){
        return "Category: " + category + "\nID: " + idItem + "\nItem: " + itemName + " \nQuantity: " + quantity;
    }
}
