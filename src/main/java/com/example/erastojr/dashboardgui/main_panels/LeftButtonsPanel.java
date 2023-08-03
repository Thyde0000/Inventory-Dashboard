package com.example.erastojr.dashboardgui.main_panels;

import com.example.erastojr.dashboardgui.customcomponents.MessageDialogs;
import com.example.erastojr.dashboardgui.customcomponents.RoundPanels;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import static com.example.erastojr.dashboardgui.UserInterfaceDashboard.*;
import static com.example.erastojr.dashboardgui.main_panels.DashboardPanel.refreshLabels;

public class LeftButtonsPanel implements ActionListener {
        private final JPanel leftButtonsPanelBackground;
        private final JButton dashboardButton;
        private final JButton itemsButton;
        private final JButton contactsButton;
        private final JButton exitButton;
        private final JButton documentationButton;

        public LeftButtonsPanel() {
            leftButtonsPanelBackground = new RoundPanels(25);
            leftButtonsPanelBackground.setBounds(10, 10, 250, 880);
            leftButtonsPanelBackground.setBackground(PURPLE_COLOR);
            dashboardButton = createButton("Dashboard",30,90, Color.WHITE);
            dashboardButton.setContentAreaFilled(true);
            dashboardButton.setOpaque(true);
            dashboardButton.addActionListener(this);
            contactsButton = createButton("Contacts",30,165, Color.WHITE);
            contactsButton.setContentAreaFilled(false);
            contactsButton.addActionListener(this);
            itemsButton = createButton("Items",30,240, Color.WHITE);
            itemsButton.setContentAreaFilled(false);
            itemsButton.addActionListener(this);
            exitButton = createButton("Exit",30,315, Color.WHITE);
            exitButton.setContentAreaFilled(false);
            exitButton.addActionListener(this);
            documentationButton = createButton("Documentation",30,800, Color.WHITE);
            documentationButton.setContentAreaFilled(false);
            documentationButton.addActionListener(this);
            leftButtonsPanelBackground.add(dashboardButton);
            leftButtonsPanelBackground.add(contactsButton);
            leftButtonsPanelBackground.add(itemsButton);
            leftButtonsPanelBackground.add(exitButton);
            leftButtonsPanelBackground.add(documentationButton);
            leftButtonsPanelBackground.setLayout(null);
        }

        public JPanel getPanel(){
            return leftButtonsPanelBackground;
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dashboardButton) {
            //Sets Panel Visible & Highlights Button
            dashboardPanel.setVisible(true);
            dashboardButton.setContentAreaFilled(true);
            dashboardButton.setOpaque(true);
            //Unhighlights other buttons & Hides other panels
            contactsButton.setContentAreaFilled(false);
            contactsButton.setOpaque(false);
            itemsButton.setContentAreaFilled(false);
            itemsButton.setOpaque(false);


            contactsPanel.setVisible(false);
            itemsPanel.setVisible(false);
            try {
                refreshLabels();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == contactsButton) {
            //Sets Panel Visible & Highlights Button
            contactsPanel.setVisible(true);
            contactsDataTable.setVisible(true);
            contactsButton.setContentAreaFilled(true);
            contactsButton.setOpaque(true);
            //Unhighlights other buttons & Hides other panels
            dashboardButton.setContentAreaFilled(false);
            dashboardButton.setOpaque(false);
            itemsButton.setContentAreaFilled(false);
            itemsButton.setOpaque(false);

            itemsPanel.setVisible(false);
            dashboardPanel.setVisible(false);
            try {
                refreshLabels();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == itemsButton) {
            //Sets Panel Visible & Highlights Button
            itemsPanel.setVisible(true);
            itemsDataTable.setVisible(true);
            itemsButton.setContentAreaFilled(true);
            itemsButton.setOpaque(true);
            //Unhighlights other buttons & Hides other panels
            dashboardButton.setContentAreaFilled(false);
            dashboardButton.setOpaque(false);
            contactsButton.setContentAreaFilled(false);
            contactsButton.setOpaque(false);


            contactsPanel.setVisible(false);
            dashboardPanel.setVisible(false);
            try {
                refreshLabels();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == exitButton) {
            MessageDialogs.messageLabel.setText("Are you sure you want to exit?");
            new MessageDialogs();
            if (MessageDialogs.isConfirmed()) {
                System.exit(0);
            }
        } else if (e.getSource() == documentationButton) {
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/Thyde0000/InventoryDashboard").toURI());
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    public JButton createButton(String nameOfButton, int x, int y, Color buttonColor) {
        JButton button = new JButton();
        button.setFont(BOLD_FONT);
        button.setText(nameOfButton);
        button.setBackground(buttonColor);
        button.setForeground(Color.BLACK);
        button.setBounds(x, y, 200, 50);
        button.setHorizontalAlignment(JButton.CENTER);
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        return button;
    }

}
