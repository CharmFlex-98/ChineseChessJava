package com.CharmFlex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class MenuPanel extends Panel{
    ArrayList<JTextField> textFields = new ArrayList<JTextField>();

    public MenuPanel(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    public void setTextField(int x, int y, int width, int height, String text, Color foregroundColor) {
        JTextField textField = new JTextField(text);
        textField.setFont(new Font("Consolas", Font.PLAIN, 13));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setForeground(foregroundColor);
        textField.setBackground(Color.BLACK);
        textField.setBounds(x, y, width, height);
        textFields.add(textField);
        add(textField);
    }

    public void setButton(int x, int y, int width, int height, String text) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.addActionListener(new ButtonListener());
        add(button);
    }

    public void setComboBox(int x, int y, int width, int height) {
        String[] sides = {"CHOOSE", "RED", "BLACK"};
        JComboBox comboBox = new JComboBox(sides);
        comboBox.setBounds(x, y, width, height);
        comboBox.addActionListener(new ComboBoxListener(comboBox));
        add(comboBox);
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (textFields.get(0).getText().equals("") || textFields.get(1).getText().equals("") || GameManager.playerBlack == 100) {
                System.out.println("Please input name(s)!");
                return;
            }
            GameManager.selfName = textFields.get(0).getText();
            GameManager.opponentName = textFields.get(1).getText();
            Client.setServerIP(textFields.get(2).getText());
            Client.setPort(textFields.get(3).getText());
            try {
                GameManager.setGameStart();
                GameManager.joinGame();
            } catch (IOException e) {
                GameManager.go2Menu();
                System.out.println("No connection");
            }
        }
    }

    private class ComboBoxListener implements ActionListener {
        private JComboBox comboBox;

        public ComboBoxListener(JComboBox comboBox) {
            this.comboBox = comboBox;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (comboBox.getSelectedItem() == "BLACK") {
                GameManager.playerBlack = 1;
            }
            else if (comboBox.getSelectedItem() == "RED") {
                GameManager.playerBlack = 0;
            }
        }
    }
}
