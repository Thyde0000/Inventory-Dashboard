package com.example.erastojr.splashwindow;
import com.example.erastojr.dashboardgui.UserInterfaceDashboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SplashScreenWindow extends JWindow {

    public SplashScreenWindow(int duration) {
        JLabel background = new JLabel(new ImageIcon("ProgressBar.gif"));
        background.setBounds(0, 0, 1300, 900);
        getContentPane().add(background);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);

        Timer timer = new Timer(duration, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                try {
                    fadeOutAndDispose();
                } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException |
                         ClassNotFoundException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        timer.start();
    }

    private void fadeOutAndDispose() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        setVisible(false);
        dispose();
        new UserInterfaceDashboard();
    }
}
