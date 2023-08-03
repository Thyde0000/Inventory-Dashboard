package com.example.erastojr.dashboardgui.customcomponents;
import com.example.erastojr.dashboardgui.UserInterfaceDashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MessageDialogs extends JDialog {
    private int initialX;
    private int initialY;

    private JButton cancelButton = createCustomButton("Cancel", 0, 100, new Color(0xADDA3B3B, true), Color.WHITE);
    private JButton confirmButton = createCustomButton("Confirm", 250, 100, UserInterfaceDashboard.SLIME_GREEN, Color.BLACK);
    private static boolean isConfirmed = false;
    public static JLabel messageLabel = createMessageLabel("");

    private static JLabel createMessageLabel(String s) {
        JLabel label = new JLabel();
        label.setText(s);
        label.setFont(UserInterfaceDashboard.BOLD_FONT);
        label.setForeground(Color.WHITE);
        label.setBounds(0, 0, 500, 100);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    public static boolean isConfirmed() {
        return isConfirmed;
    }


    JPanel panel;

    public MessageDialogs() {
        panel = createTablePanel();
        panel.add(cancelButton);
        panel.add(confirmButton);
        panel.add(messageLabel);
        setModal(true);
        setUndecorated(true);
        setSize(500, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(panel);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialX = e.getX();
                initialY = e.getY();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int currentX = getLocation().x;
                int currentY = getLocation().y;

                int newX = currentX + (e.getX() - initialX);
                int newY = currentY + (e.getY() - initialY);

                setLocation(newX, newY);
            }
        });

        setVisible(true);
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(147, 126, 185));
        panel.setBounds(280, 100, 500, 150);
        return panel;
    }

    private JButton createCustomButton(String nameOfButton, int x, int y, Color background, Color foreground) {
        JButton button = new JButton();
        button.setFont(UserInterfaceDashboard.BOLD_FONT);
        button.setText(nameOfButton);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setBounds(x, y, 250, 50);
        button.setHorizontalAlignment(JButton.CENTER);
        button.setFocusable(false);
        button.addActionListener(e -> {
            if (e.getSource() == cancelButton) {
                dispose();
            } else if (e.getSource() == confirmButton) {
                isConfirmed = true;
                dispose();
            }
        });
        return button;
    }
}
