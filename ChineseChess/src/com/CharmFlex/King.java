package com.CharmFlex;

import java.awt.*;
import java.util.ArrayList;

public class King extends Piece{
    byte opponentShift = 7;
    byte boundaryIndex = 2;

    public King(byte x, byte y, boolean isRed, String icon, byte playerBlack) {
        super(x, y, isRed, icon);
        if ((!isRed && playerBlack == 0) || (isRed && playerBlack == 1)) {
            opponentShift = 0;
        }
    }

    public King(Piece piece) {
        super(piece.x, piece.y, piece.isRed, piece.icon);
    }


    @Override
    Piece copy(Piece piece) {
        return new King(piece);
    }

    @Override
    ArrayList<Point> legalMove(Piece[][] board) {
        positions.clear();
        killPositions.clear();

        // right
        if (inBoundary((byte) (x + 1), y)) {
            if (board[y][x + 1] == null) {
                positions.add(new Point(x + 1, y));
            }
            else if (board[y][x + 1] != null && board[y][x+1].isRed != isRed) {
                killPositions.add(new Point(x+1, y));
            }
        }
        // left
        if (inBoundary((byte) (x - 1), y)) {
            if (board[y][x-1] == null) {
                positions.add(new Point(x - 1, y));
            }
            else if (board[y][x-1] != null && board[y][x-1].isRed != isRed) {
                killPositions.add(new Point(x - 1, y));
            }
        }
        // up
        if (inBoundary(x, (byte) (y - 1))) {
            if (board[y - 1][x] == null) {
                positions.add(new Point(x, y - 1));
            }
            else if (board[y - 1][x] != null && board[y - 1][x].isRed != isRed) {
                killPositions.add(new Point(x, y - 1));
            }
        }
        // down
        if (inBoundary(x, (byte) (y + 1))) {
            if (board[y + 1][x] == null) {
                positions.add(new Point(x, y + 1));
            }
            else if (board[y + 1][x] != null && board[y + 1][x].isRed != isRed) {
                killPositions.add(new Point(x, y + 1));
            }
        }

        // fly king
        for (int i = y + 1; i <= YMAXINDEX; i++) {
            if (board[i][x] != null) {
                if (board[i][x].getClass().getSimpleName().equals("King")) {
                    killPositions.add(new Point(x, i));
                }
                break;
            }
        }
        for (int i = y - 1; i >= 0; i--) {
            if (board[i][x] != null) {
                if (board[i][x].getClass().getSimpleName().equals("King")) {
                    killPositions.add(new Point(x, i));
                }
                break;
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
