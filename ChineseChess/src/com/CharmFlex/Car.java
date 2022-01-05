package com.CharmFlex;

import java.awt.*;
import java.util.ArrayList;

public class Car extends Piece{

    public Car(byte x, byte y, boolean isRed, String icon) {
        super(x, y, isRed, icon);
    }

    public Car(Piece piece) {
        super(piece.x, piece.y, piece.isRed, piece.icon);
    }


    @Override
    Piece copy(Piece piece) {
        return new Car(piece);
    }

    @Override
    ArrayList<Point> legalMove(Piece[][] board) {
        positions.clear();
        killPositions.clear();

        // move right
        for (int i = x + 1; i <= XMAXINDEX; i++) {
            if (board[y][i] != null) {
                if (board[y][i].isRed != isRed) {
                    killPositions.add(new Point(i, y));
                }
                break;
            }
            positions.add(new Point(i, y));
        }
        // move left
        for (int i = x - 1; i >= 0; i--) {
            if (board[y][i] != null) {
                if (board[y][i].isRed != isRed) {
                    killPositions.add(new Point(i, y));
                }
                break;
            }
            positions.add(new Point(i, y));
        }
        // move up
        for (int i = y + 1; i <= YMAXINDEX; i++) {
            if (board[i][x] != null) {
                if (board[i][x].isRed != isRed) {
                    killPositions.add(new Point(x, i));
                }
                break;
            }
            positions.add(new Point(x, i));
        }
        // move down
        for (int i = y - 1; i >= 0; i--) {
            if (board[i][x] != null) {
                if (board[i][x].isRed != isRed) {
                    killPositions.add(new Point(x, i));
                }
                break;
            }
            positions.add(new Point(x, i));
        }

        return positions;
    }


}
