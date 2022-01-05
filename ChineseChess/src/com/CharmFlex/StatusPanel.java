package com.CharmFlex;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends Panel{
    private JLabel opponentStatus;
    private JLabel turnInfo;


    public StatusPanel(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    public void putPlayerInfo(int x, int y, int width, int height, String playerName) {
        JLabel playerInfo = new JLabel(playerName, SwingConstants.CENTER);
        playerInfo.setFont(new Font("Consolas", Font.PLAIN, 18));
        playerInfo.setForeground(Color.WHITE);
        playerInfo.setBounds(x, y, width, height);
        add(playerInfo);
    }

    public void putTurnInfo() {
        turnInfo = new JLabel("YOUR TURN", SwingConstants.CENTER);
        turnInfo.setFont(new Font("Consolas", Font.PLAIN, 18));
        turnInfo.setForeground(Color.ORANGE);
        add(turnInfo);
    }

    public void turnInfoOn(boolean on) {
        if (on) {
            turnInfo.setBounds(0, 400, 300,200);
            turnInfo.setText("YOUR TURN");
        }
        else {
            turnInfo.setBounds(0, 100, 300,200);
            turnInfo.setText("OPPONENT'S TURN");
        }
    }

    public void putOnlineStatus(int x, int y, int width, int height) {
        opponentStatus = new JLabel();
        opponentStatus.setFont(new Font("Consolas", Font.PLAIN, 18));
        opponentStatus.setBounds(x, y, width, height);
        opponentStatus.setHorizontalAlignment(SwingConstants.CENTER);
        opponentStatus.setVerticalAlignment(SwingConstants.CENTER);
        add(opponentStatus);
    }

    public JLabel getOpponentStatus() {
        return opponentStatus;
    }
}
