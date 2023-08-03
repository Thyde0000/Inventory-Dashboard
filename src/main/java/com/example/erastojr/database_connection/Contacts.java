package com.example.erastojr.database_connection;

public class Contacts {
    private int contactID;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String contactEmail;

    public Contacts(int contactID, String firstName, String lastName, String contactNumber, String contactEmail) {
        this.contactID = contactID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.contactEmail = contactEmail;
    }

    public int getContactID() {
        return contactID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String toString() {
        return "Contact ID: " + contactID + "\nFirst Name: " + firstName + "\nLast Name: " + lastName + "\nContact Number: " + contactNumber + "\nContact Email: " + contactEmail;
    }
}
