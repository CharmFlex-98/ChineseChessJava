package com.CharmFlex;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameManager {
    public static String selfName;
    public static String opponentName;
    private static BoardPanel player;
    public static byte playerBlack;
    public static Window gameWindow;
    private static Window menuWindow;
    public static StatusPanel statusPanel;
    private static boolean gameEnd = false;

    public static void init() {
        playerBlack = 100;
        go2Menu();
    }

    public static void go2Menu() {
        MenuPanel menuPanel = new MenuPanel(0, 0, 400, 400, Color.darkGray);
        menuPanel.setTextField(100, 100, 200, 25, "Enter Your Name", Color.GREEN);
        menuPanel.setTextField(100, 150, 200, 25, "Enter Your Opponent Name", Color.GREEN);
        menuPanel.setTextField(0, 0, 50, 20, "6.tcp.ngrok.io", Color.gray);
        menuPanel.setTextField(0, 20, 50, 20, "14953", Color.gray);
        menuPanel.setComboBox(150, 200, 100, 25);
        menuPanel.setButton(150, 250, 100, 30, "Play!");
        menuWindow = new Window(400, 400, "Login");
        menuWindow.addPanel(menuPanel);
    }

    public static void setGameStart() {
        gameEnd = false;
    }
    public static void setGameEnd() {
        gameEnd = true;
    }

    public static boolean getGameEnd() {
        return gameEnd;
    }


    public static void popEndGameMsg (boolean win) {
        if (win) {
            JOptionPane.showMessageDialog(null, "You win!!", "Message", JOptionPane.PLAIN_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Awh.. You lose   :(", "Message", JOptionPane.PLAIN_MESSAGE);
        }

    }

    public static  void joinGame() throws IOException {
        menuWindow.disposeWindow();
        Client.init();

        // instantiate board panel
        BoardPanel boardPanel = new BoardPanel(0, 0, 600, 600, Color.black, playerBlack);
        boardPanel.draw("Resources/board.png", 0, 0, 600, 600);
        insertBoard(boardPanel);

        // instantiate status panel
        statusPanel = new StatusPanel(600, 0, 300, 600, Color.DARK_GRAY);
        if (playerBlack == 1) {
            statusPanel.putPlayerInfo(0, 0, 300, 200, GameManager.opponentName);
            statusPanel.putPlayerInfo(0, 300, 300, 200, GameManager.selfName);
            statusPanel.putTurnInfo();
            statusPanel.turnInfoOn(false);
        }
        else {
            statusPanel.putPlayerInfo(0, 300, 300, 200, GameManager.selfName);
            statusPanel.putPlayerInfo(0, 0, 300, 200, GameManager.opponentName);
            statusPanel.putTurnInfo();
            statusPanel.turnInfoOn(true);
        }
        statusPanel.putOnlineStatus(0, 100, 300, 100);
        opponentIsOnline(false);

        // instantiate game window
        gameWindow = new Window(900, 645, "Chinese Chess");
        gameWindow.setCloseOperation();
        gameWindow.addPanel(boardPanel);
        gameWindow.addPanel(statusPanel);
    }

    public static void announceWinner() {
        Client.infoTransfer("You Lose");
        setGameEnd();
        popEndGameMsg(true);
    }

    public static void setPlayerTurn(boolean b) {
        if (b) {
            player.playerTurn = true;
            statusPanel.turnInfoOn(true);
        }
        else {
            player.playerTurn = false;
            statusPanel.turnInfoOn(false);
        }
    }


    public static void opponentIsOnline(boolean bool) {
        if (bool) {
            statusPanel.getOpponentStatus().setForeground(Color.GREEN);
            statusPanel.getOpponentStatus().setText("(ONLINE)");
        }
        else {
            statusPanel.getOpponentStatus().setForeground(Color.RED);
            statusPanel.getOpponentStatus().setText("(OFFLINE)");
        }
    }

    public static void insertBoard(BoardPanel player) {
        GameManager.player = player;
    }

    public static void moveInfoTransferal(byte startX, byte startY, byte endX, byte endY) {
        String info;
        info = "Pos: " + (8 - startX) + " " + (9 - startY) + " " + (8 - endX) + " " + (9 - endY);

        Client.infoTransfer(info);
    }

    public static void moveInfoReceiver(String info) {
        String[] moveInfo = info.split("\\s+");
        byte startX = Byte.parseByte(moveInfo[1]);
        byte startY = Byte.parseByte(moveInfo[2]);
        byte endX = Byte.parseByte(moveInfo[3]);
        byte endY = Byte.parseByte(moveInfo[4]);

        player.setSelectedPiece(startX, startY);
        if (player.getBoard()[endY][endX] == null) {
            player.movePiece(endX, endY);
            player.repaint();
        }
        else {
            player.killPiece(endX, endY, player.getBoard()[endY][endX]);
            player.repaint();
        }
        player.deSelectPiece();
        setPlayerTurn(true);
    }


}
