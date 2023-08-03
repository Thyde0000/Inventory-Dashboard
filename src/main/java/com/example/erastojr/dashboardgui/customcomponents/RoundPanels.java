package com.example.erastojr.dashboardgui.customcomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundPanels extends JPanel {
    private int cornerRadius;

    public RoundPanels(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setOpaque(false); // Make the panel transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        // Create a rounded rectangle shape
        Shape roundedRect = new RoundRectangle2D.Double(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);

        // Set the panel's background color
        g2d.setColor(getBackground());
        g2d.fill(roundedRect);

        g2d.dispose();
    }

    private JPanel createRoundPanel(int x, int y, int w, int h, Color c) {
        RoundPanels panel = new RoundPanels(25);
        panel.setBounds(x, y, w, h);
        panel.setBackground(c);
        return panel;
    }
}
