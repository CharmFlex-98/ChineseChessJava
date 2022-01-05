package com.CharmFlex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Objects;


public abstract class Panel extends JPanel {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Panel(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        setLayout(null);
        setBounds(x, y, width, height);
        setBackground(color);
        setOpaque(true);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void draw(String imagePath, int x, int y, int resizeWidth, int resizeHeight) {
        JLabel label = new JLabel();
        ImageIcon pic = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath))).
                getImage().getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_DEFAULT));
        label.setIcon(pic);
        label.setBounds(x, y, resizeWidth, resizeHeight);
        add(label);
    }

    public void draw(Piece piece, String imagePath, int x, int y, int resizeWidth, int resizeHeight) {
        ImageIcon pic = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath))).
                getImage().getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_DEFAULT));
        piece.setIcon(pic);
        piece.setBounds(x, y, resizeWidth, resizeHeight);
        add(piece);
    }
}