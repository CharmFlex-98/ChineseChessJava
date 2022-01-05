package com.CharmFlex;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Piece extends JLabel {

    final byte YMAXINDEX = 9;
    final byte XMAXINDEX = 8;
    byte x;
    byte y;
    boolean isRed;
    boolean isTerminated;
    String icon;
    ArrayList<Point> positions = new ArrayList<Point>();
    ArrayList<Point> killPositions = new ArrayList<Point>();

    Piece(byte x, byte y, boolean isRed, String icon) {
        this.x = x;
        this.y = y;
        this.isRed = isRed;
        this.icon = icon;
        isTerminated = false;
    }

    abstract Piece copy(Piece piece);

    void move(byte x, byte y) {

    }

    byte getPosX() {
        return x;
    }

    byte getPosY() {
        return y;
    }

    void setX(byte x) {
        this.x = x;
    }

    void setY(byte y) {
        this.y = y;
    }

    String getIconPath() {
        return icon;
    }
    boolean inBoundary(byte nextX, byte nextY) {
        return (nextY >= 0) && (nextY <= YMAXINDEX) && (nextX >= 0) && (nextX <= XMAXINDEX);
    }

    abstract ArrayList<Point> legalMove(Piece[][] board);
    ArrayList<Point> legalKillMove() {
        return killPositions;
    }



}
