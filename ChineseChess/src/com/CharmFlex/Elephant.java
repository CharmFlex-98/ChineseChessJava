package com.CharmFlex;

import java.awt.*;
import java.util.ArrayList;

public class Elephant extends Piece{
    byte opponentShift = 5;
    byte boundaryIndex = 4;
    public Elephant(byte x, byte y, boolean isRed, String icon, byte playerBlack) {
        super(x, y, isRed, icon);
        if ((!isRed && playerBlack == 0) || (isRed && playerBlack == 1)) {
            opponentShift = 0;
        }
    }

    public Elephant(Piece piece) {
        super(piece.x, piece.y, piece.isRed, piece.icon);
    }


    @Override
    Piece copy(Piece piece) {
        return new Elephant(piece);
    }

    @Override
    ArrayList<Point> legalMove(Piece[][] board) {
        positions.clear();
        killPositions.clear();

        byte[] nextXs = {(byte) (x + 2), (byte) (x - 2)};
        byte[] nextYs = {(byte) (y + 2), (byte) (y - 2)};
        for (byte nextX : nextXs) {
            for (byte nextY : nextYs) {
                if (inBoundary(nextX, nextY)) {
                    if (board[nextY][nextX] == null && board[(nextY + y)/2][(nextX + x)/2] == null) {
                        positions.add(new Point(nextX, nextY));
                    }
                    else if (board[nextY][nextX] != null && board[(nextY + y)/2][(nextX + x)/2] == null &&
                            board[nextY][nextX].isRed != isRed) {
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
                (nextX >= 0) && (nextX <= XMAXINDEX);
    }

}
