package com.example.erastojr.dashboardgui.customcomponents;

import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

class RoundBorderButton implements Border {
    private int radius;

    public RoundBorderButton(int radius) {
        this.radius = radius;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius, this.radius, this.radius, this.radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(c.getForeground());

        RoundRectangle2D roundRect = new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius);
        g2.draw(roundRect);

        g2.dispose();
    }
}
