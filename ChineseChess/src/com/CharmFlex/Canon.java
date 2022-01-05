package com.CharmFlex;

import java.awt.*;
import java.util.ArrayList;

public class Canon extends Piece{

    public Canon(byte x, byte y, boolean isRed, String icon) {
        super(x, y, isRed, icon);
    }

    public Canon(Piece piece) {
        super(piece.x, piece.y, piece.isRed, piece.icon);
    }


    @Override
    Piece copy(Piece piece) {
        return new Canon(piece);
    }

    @Override
    ArrayList<Point> legalMove(Piece[][] board) {
        positions.clear();
        killPositions.clear();

        // move right
        for (int i = x + 1; i <= XMAXINDEX; i++) {
            if (board[y][i] != null) {
                for (int j = i + 1; j < XMAXINDEX; j++) {
                    if (board[y][j] != null && board[y][j].isRed != isRed) {
                        killPositions.add(new Point(j, y));
                        break;
                    }
                }
                break;
            }
            positions.add(new Point(i, y));
        }
        // move left
        for (int i = x - 1; i >= 0; i--) {
            if (board[y][i] != null) {
                for (int j = i - 1; j >= 0; j--) {
                    if (board[y][j] != null && board[y][j].isRed != isRed) {
                        killPositions.add(new Point(j, y));
                        break;
                    }
                }
                break;
            }
            positions.add(new Point(i, y));
        }
        // move up
        for (int i = y + 1; i <= YMAXINDEX; i++) {
            if (board[i][x] != null) {
                for (int j = i + 1; j <= YMAXINDEX; j++) {
                    if (board[j][x] != null && board[j][x].isRed != isRed) {
                        killPositions.add(new Point(x, j));
                        break;
                    }
                }
                break;
            }
            positions.add(new Point(x, i));
        }
        // move down
        for (int i = y - 1; i >= 0; i--) {
            if (board[i][x] != null) {
                for (int j = i - 1; j >= 0; j--) {
                    if (board[j][x] != null && board[j][x].isRed != isRed) {
                        killPositions.add(new Point(x, j));
                        break;
                    }
                }
                break;
            }
            positions.add(new Point(x, i));
        }

        return positions;
    }
}
