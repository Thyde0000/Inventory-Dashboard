package com.example.erastojr.dashboardgui;

import com.example.erastojr.dashboardgui.customcomponents.RoundPanels;
import com.example.erastojr.dashboardgui.main_panels.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UserInterfaceDashboard extends JFrame {
    public static final Color PURPLE_COLOR = new Color(207, 186, 225);
    public static final Color LIGHT_WHITE_BORDER = new Color(243, 243, 254);
    public static final Color SLIME_GREEN = new Color(160, 255, 217);
    public static final Font BOLD_FONT = new Font("Arial", Font.BOLD, 15);

    private final JPanel outerWhiteFrame = createOuterWhiteFrame();
    public static JPanel sideButtonPanel = new LeftButtonsPanel().getPanel();
    public static JPanel dashboardPanel;

    static {
        try {
            dashboardPanel = new DashboardPanel().getPanel();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static JPanel contactsPanel;
    public static JPanel contactsDataTable;
    public static JPanel itemsPanel;
    public static JPanel itemsDataTable;

    public UserInterfaceDashboard() {
        try {
            contactsPanel = new ContactsPanel().getPanel();
            contactsDataTable = new ContactsPanel().getContactsDataTable();
            contactsDataTable.setVisible(false);
            itemsPanel = new ItemsPanel().getPanel();
            itemsDataTable = new ItemsPanel().getItemsDataTable();
            itemsDataTable.setVisible(false);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setRegularUI();
        setLayoutAndAppearance();
        addComponentsToFrame();
    }


    private void setLayoutAndAppearance() {
        //1300 , 900
        setSize(1600, 900);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setUndecorated(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inventory Dashboard");
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addComponentsToFrame() {
        outerWhiteFrame.setLayout(null);
        contactsPanel.setVisible(false);
        itemsPanel.setVisible(false);
        outerWhiteFrame.add(sideButtonPanel);
        outerWhiteFrame.add(dashboardPanel);
        outerWhiteFrame.add(contactsPanel);
        outerWhiteFrame.add(itemsPanel);
        add(outerWhiteFrame);
    }

    private JPanel createOuterWhiteFrame() {
        RoundPanels panel = new RoundPanels(25);
        panel.setBounds(5, 5, 1590, 890);
        panel.setBackground(LIGHT_WHITE_BORDER);
        return panel;
    }

    void setRegularUI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            throw new RuntimeException(ex);
        }
    }
}
