package com.CharmFlex;

import java.awt.*;
import java.util.ArrayList;

public class Soldier extends Piece{
    byte opponentShift = 0;
    byte boundaryIndex = 4;
    byte front = 1;

    public Soldier(byte x, byte y, boolean isRed, String icon, byte playerBlack) {
        super(x, y, isRed, icon);
        positions.clear();
        if ((!isRed && playerBlack == 0) || (isRed && playerBlack == 1)) {
            opponentShift = 5;
            front = -1;
        }
    }

    public Soldier(Piece piece) {
        super(piece.x, piece.y, piece.isRed, piece.icon);
    }


    @Override
    Piece copy(Piece piece) {
        return new Soldier(piece);
    }


    @Override
    public ArrayList<Point> legalMove(Piece[][] board) {
        positions.clear();
        killPositions.clear();

        // front
        if (inBoundary(x, (byte) (y - front))) {
            if (board[y - front][x] == null) {
                positions.add(new Point(x, y - front));
            }
            else if (board[y - front][x] != null && board[y - front][x].isRed != isRed) {
                killPositions.add(new Point(x, y - front));
            }
        }

        // right
        if (inBoundary((byte) (x + 1), y)) {
            if (board[y][x + 1] == null && crossBoundary(y)) {
                positions.add(new Point(x + 1, y));
            }
            else if (board[y][x + 1] != null && crossBoundary(y) && board[y][x + 1].isRed != isRed) {
                killPositions.add(new Point(x + 1, y));
            }
        }
        // left
        if (inBoundary((byte) (x - 1), y)) {
            if (board[y][x-1] == null  && crossBoundary(y)) {
                positions.add(new Point(x - 1, y));
            }
            else if (board[y][x - 1] != null && crossBoundary(y) && board[y][x - 1].isRed != isRed) {
                killPositions.add(new Point(x - 1, y));
            }
        }
        return positions;
    }

    boolean crossBoundary(byte nextY) {
        return (nextY >= opponentShift) && (nextY <= boundaryIndex + opponentShift);
    }
}
