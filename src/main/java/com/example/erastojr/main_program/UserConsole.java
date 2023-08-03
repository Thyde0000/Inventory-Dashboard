package com.example.erastojr.main_program;

import com.example.erastojr.database_connection.SQLConnectionDB;
import com.example.erastojr.splashwindow.SplashScreenWindow;

import javax.swing.*;

public class UserConsole {
    public static SQLConnectionDB program = new SQLConnectionDB();

    public static void main(String[] args) {
        //Begins Connection
        program.startConnection();

        //Begins Splash Screen Then Calls JFrame
        SwingUtilities.invokeLater(() -> new SplashScreenWindow(5000));
    }
}
