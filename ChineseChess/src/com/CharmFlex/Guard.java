package com.CharmFlex;

import java.awt.*;
import java.util.ArrayList;

public class Guard extends Piece{
    byte opponentShift = 7;
    byte boundaryIndex = 2;
    public Guard(byte x, byte y, boolean isRed, String icon, byte playerBlack) {
        super(x, y, isRed, icon);
        if ((!isRed && playerBlack == 0) || (isRed && playerBlack == 1)) {
            opponentShift = 0;
        }
    }

    public Guard(Piece piece) {
        super(piece.x, piece.y, piece.isRed, piece.icon);
    }


    @Override
    Piece copy(Piece piece) {
        return new Guard(piece);
    }

    @Override
    ArrayList<Point> legalMove(Piece[][] board) {
        positions.clear();

        byte[] nextXs = {(byte) (x + 1), (byte) (x - 1)};
        byte[] nextYs = {(byte) (y + 1), (byte) (y - 1)};
        for (byte nextX : nextXs) {
            for (byte nextY : nextYs) {
                if (inBoundary(nextX, nextY)) {
                    if (board[nextY][nextX] == null) {
                        positions.add(new Point(nextX, nextY));
                    }
                    else if (board[nextY][nextX] != null && board[nextY][nextX].isRed != isRed) {
                        killPositions.add(new Point(nextX, nextY));
                    }
                }
            }
        }
        return positions;
    }

    @Override
    boolean inBoundary(byte nextX, byte nextY) {
        return (nextY >= opponentShift) && (nextY <= boundaryIndex + opponentShift) &&
                (nextX >= 3) && (nextX <= 5);
    }


}
