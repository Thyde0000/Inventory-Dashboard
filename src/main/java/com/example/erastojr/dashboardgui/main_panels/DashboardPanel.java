package com.example.erastojr.dashboardgui.main_panels;
import com.example.erastojr.dashboardgui.customcomponents.RoundPanels;
import com.example.erastojr.main_program.UserConsole;

import javax.swing.*;

import java.sql.SQLException;

import static com.example.erastojr.dashboardgui.UserInterfaceDashboard.LIGHT_WHITE_BORDER;
import static com.example.erastojr.dashboardgui.UserInterfaceDashboard.PURPLE_COLOR;

public class DashboardPanel {
    private JPanel dashboardPanel;
    private JPanel totalContactsPanel;
    public static JLabel totalContactsLabel;
    private JPanel totalItemsPanel;
    public static JLabel totalItemsLabel;

    public DashboardPanel() throws SQLException {
        dashboardPanel = new RoundPanels(25);
        dashboardPanel.setBounds(270, 10, 1320, 880);
        dashboardPanel.setBackground(PURPLE_COLOR);
        dashboardPanel.setLayout(null);
        totalContactsPanel = createPanelBorder(25, 260, 20, 300, 180);
        totalContactsLabel = createLabel("Total Contacts: " + UserConsole.program.contactCount(), 100, 20, 300, 180);
        totalContactsPanel.add(totalContactsLabel);
        totalItemsPanel = createPanelBorder(25, 600, 20, 300, 180);
        totalItemsLabel = createLabel("Total Items: " + UserConsole.program.itemCount(), 100, 20, 300, 180);
        totalItemsPanel.add(totalItemsLabel);
        dashboardPanel.add(totalContactsPanel);
        dashboardPanel.add(totalItemsPanel);
    }

    public JPanel createPanelBorder(int radius, int x, int y, int width, int height) {
        JPanel panel = new RoundPanels(radius);
        panel.setBounds(x, y, width, height);
        panel.setBackground(LIGHT_WHITE_BORDER);
        return panel;
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        return label;
    }

    public static void refreshLabels() throws SQLException {
        totalContactsLabel.setText("Total Contacts: " + UserConsole.program.contactCount());
        totalItemsLabel.setText("Total Items: " + UserConsole.program.itemCount());
    }

    public JPanel getPanel() {
        return dashboardPanel;
    }

}
