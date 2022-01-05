package com.CharmFlex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Window {
    JFrame window;

    public Window(int width, int height, String windowName) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLayout(null);
        window.setSize(width, height);
        window.setResizable(false);
        window.setTitle(windowName);
        window.getContentPane().setBackground(Color.black);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void addPanel(Panel panel) {
        window.add(panel);

    }

    public void setCloseOperation() {
        window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                window.dispose();
                Client.closeSocket();
                GameManager.go2Menu();
            }
        });
    }

    public void disposeWindow() {
        window.dispose();
    }
}

