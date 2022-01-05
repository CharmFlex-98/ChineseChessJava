package com.CharmFlex;

import java.awt.*;
import java.util.ArrayList;

public class Horse extends Piece {
    public Horse(byte x, byte y, boolean isRed, String icon) {
        super(x, y, isRed, icon);
    }

    public Horse(Piece piece) {
        super(piece.x, piece.y, piece.isRed, piece.icon);
    }


    @Override
    Piece copy(Piece piece) {
        return new Horse(piece);
    }

    @Override
    ArrayList<Point> legalMove(Piece[][] board) {
        positions.clear();
        killPositions.clear();

        for (int i = -2; i <= 2; i++) {
            if (i != 0) {
                int j = 3 - Math.abs(i);
                byte nextX = (byte) (x + i);
                byte[] nextYs = {(byte) (y + j), (byte) (y-j)};
                for (byte nextY : nextYs) {
                    if (inBoundary(nextX, nextY)) {
                        byte checkX;
                        byte checkY;
                        if (Math.abs(i) > Math.abs(j)) {
                            checkX = (byte) (Math.signum(i)*1);
                            checkY = 0;
                        }
                        else {
                            checkY = (byte) (Math.signum(nextY-y)*1);
                            checkX = 0;
                        }
                        if ((board[nextY][nextX] == null) && (board[y + checkY ][x + checkX] == null)) {
                            positions.add(new Point(nextX, nextY));
                        }
                        else if ((board[nextY][nextX] != null) && (board[y + checkY ][x + checkX] == null
                                && board[nextY][nextX].isRed != isRed)) {
                            killPositions.add(new Point(nextX, nextY));
                        }
                    }
                }
            }
        }
        return positions;
    }
}

