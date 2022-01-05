package com.CharmFlex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class BoardPanel extends Panel {

    private final int yLengthDiv = 10;
    private final int xLengthDiv = 9;
    private final int highlightMoveWidth;
    private final int highlightMoveHeight;
    private final int highlightPieceWidth;
    private final int highlightPieceHeight;

    private byte playerBlack = 1;
    private Piece[][] board;
    private ArrayList<JLabel> diePiece;
    private int highlightPieceX;
    private int highlightPieceY;
    private ArrayList<Point> highlightMovePos;
    private ArrayList<Point> highlightKillPos;
    private boolean pieceIsSelected;
    private Piece selectedPiece;
    private Piece opponentKing;
    private ArrayList<Piece> selfPieces;
    private ArrayList<Piece> opponentPieces;

    public boolean playerTurn;

    public BoardPanel(int x, int y, int width, int height, Color color, int playerBlack) {
        super(x, y, width, height, color);

        this.playerBlack = (byte) playerBlack;
        playerTurn = this.playerBlack == 0;
        highlightPieceWidth = 60;
        highlightPieceHeight = 60;
        highlightMoveWidth = 60;
        highlightMoveHeight = 60;
        pieceIsSelected = false;
        highlightMovePos = new ArrayList<Point>();
        highlightKillPos = new ArrayList<Point>();
        diePiece = new ArrayList<JLabel>();
        selfPieces = new ArrayList<Piece>();
        opponentPieces = new ArrayList<Piece>();
        initPiece();
        initPieceLocation();
        addMouseListener(new boardListener());
    }

    public void initPiece() {
        board = new Piece[10][9];

        placePiece(new Car((byte) 0, (byte) Math.abs(9*playerBlack-9), true, "Resources/r_rook.png"));
        placePiece(new Car((byte) 8, (byte) Math.abs(9*playerBlack-9), true, "Resources/r_rook.png"));
        placePiece(new Horse((byte) 1, (byte) Math.abs(9*playerBlack-9), true, "Resources/r_knight.png"));
        placePiece(new Horse((byte) 7, (byte) Math.abs(9*playerBlack-9), true, "Resources/r_knight.png"));
        placePiece(new Elephant((byte) 2, (byte) Math.abs(9*playerBlack-9), true, "Resources/r_elephant.png", playerBlack));
        placePiece(new Elephant((byte) 6, (byte) Math.abs(9*playerBlack-9), true, "Resources/r_elephant.png", playerBlack));
        placePiece(new Guard((byte) 3, (byte) Math.abs(9*playerBlack-9), true, "Resources/r_guide.png", playerBlack));
        placePiece(new Guard((byte) 5, (byte) Math.abs(9*playerBlack-9), true, "Resources/r_guide.png", playerBlack));
        placePiece(new King((byte) 4, (byte) Math.abs(9*playerBlack-9), true, "Resources/r_king.png", playerBlack));
        placePiece(new Canon((byte) 1, (byte) Math.abs(9*playerBlack-7), true, "Resources/r_canon.png"));
        placePiece(new Canon((byte) 7, (byte) Math.abs(9*playerBlack-7), true, "Resources/r_canon.png"));
        placePiece(new Soldier((byte) 0, (byte) Math.abs(9*playerBlack-6), true, "Resources/r_soldier.png", playerBlack));
        placePiece(new Soldier((byte) 2, (byte) Math.abs(9*playerBlack-6), true, "Resources/r_soldier.png", playerBlack));
        placePiece(new Soldier((byte) 4, (byte) Math.abs(9*playerBlack-6), true, "Resources/r_soldier.png", playerBlack));
        placePiece(new Soldier((byte) 6, (byte) Math.abs(9*playerBlack-6), true, "Resources/r_soldier.png", playerBlack));
        placePiece(new Soldier((byte) 8, (byte) Math.abs(9*playerBlack-6), true, "Resources/r_soldier.png", playerBlack));

        //black pieces
        placePiece(new Car((byte) 0, (byte) Math.abs(9*playerBlack), false, "Resources/b_rook.png"));
        placePiece(new Car((byte) 8, (byte) Math.abs(9*playerBlack), false, "Resources/b_rook.png"));
        placePiece(new Horse((byte) 1, (byte) Math.abs(9*playerBlack), false, "Resources/b_knight.png"));
        placePiece(new Horse((byte) 7, (byte) Math.abs(9*playerBlack), false, "Resources/b_knight.png"));
        placePiece(new Elephant((byte) 2, (byte) Math.abs(9*playerBlack), false, "Resources/b_elephant.png", playerBlack));
        placePiece(new Elephant((byte) 6, (byte) Math.abs(9*playerBlack), false, "Resources/b_elephant.png", playerBlack));
        placePiece(new Guard((byte) 3, (byte) Math.abs(9*playerBlack), false, "Resources/b_guide.png", playerBlack));
        placePiece(new Guard((byte) 5, (byte) Math.abs(9*playerBlack), false, "Resources/b_guide.png", playerBlack));
        placePiece(new King((byte) 4, (byte) Math.abs(9*playerBlack), false, "Resources/b_king.png", playerBlack));
        placePiece(new Canon((byte) 1, (byte) Math.abs(9*playerBlack-2), false, "Resources/b_canon.png"));
        placePiece(new Canon((byte) 7, (byte) Math.abs(9*playerBlack-2), false, "Resources/b_canon.png"));
        placePiece(new Soldier((byte) 0, (byte) Math.abs(9*playerBlack-3), false, "Resources/b_soldier.png", playerBlack));
        placePiece(new Soldier((byte) 2, (byte) Math.abs(9*playerBlack-3), false, "Resources/b_soldier.png", playerBlack));
        placePiece(new Soldier((byte) 4, (byte) Math.abs(9*playerBlack-3), false, "Resources/b_soldier.png", playerBlack));
        placePiece(new Soldier((byte) 6, (byte) Math.abs(9*playerBlack-3), false, "Resources/b_soldier.png", playerBlack));
        placePiece(new Soldier((byte) 8, (byte) Math.abs(9*playerBlack-3), false, "Resources/b_soldier.png", playerBlack));

    }

    public void initPieceLocation() {
        for (Piece[] pieces : board) {
            for (Piece piece : pieces) {
                if (piece == null) {
                    continue;
                }
                if ((playerBlack == 1 && piece.isRed) || (playerBlack == 0 && !piece.isRed)) {
                    opponentPieces.add(piece);
                    if (piece.getClass().getSimpleName().equals("King")) {
                        opponentKing = piece;
                    }
                }
                else {
                    selfPieces.add(piece);
                }

                piece.addMouseListener(new PieceListener(piece));
                draw(piece, piece.getIconPath(),
                        index2Coordinate(piece.getPosX(), true),
                        index2Coordinate(piece.getPosY(), false),
                        70, 70);
            }
        }
    }

    private void placePiece(Piece piece) {
        board[piece.getPosY()][piece.getPosX()] = piece;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void setSelectedPiece(byte x, byte y) {
        selectedPiece = board[y][x];
    }

    private void pieceChangePos(Piece[][] board, byte prevX, byte prevY, byte moveX, byte moveY) {
        if (board[moveY][moveX] != null) {
            board[moveY][moveX].isTerminated = true;
        }
        board[moveY][moveX] = board[prevY][prevX];
        board[prevY][prevX] = null;
        board[moveY][moveX].setX(moveX);
        board[moveY][moveX].setY(moveY);
    }

    public void movePiece(byte moveX, byte moveY) {
        byte prevX = selectedPiece.getPosX();
        byte prevY = selectedPiece.getPosY();

        selectedPiece.setLocation(index2Coordinate(moveX, true), index2Coordinate(moveY, false));
        pieceChangePos(board, prevX, prevY, moveX, moveY);
    }

    public  void  killPiece(byte killX, byte killY, Piece thePiece) {
        byte prevX = selectedPiece.getPosX();
        byte prevY = selectedPiece.getPosY();

        remove(thePiece);
        diePiece.add(thePiece);
        thePiece.isTerminated = true;
        selectedPiece.setLocation(index2Coordinate(killX, true), index2Coordinate(killY, false));
        pieceChangePos(board, prevX, prevY, killX, killY);
    }

    private void resetTestingBoard(Piece[][] testingBoard) {
        selfPieces.clear();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    testingBoard[i][j] = board[i][j].copy(board[i][j]);
                    // every player move, we must reset our pieces
                    if ((playerBlack == 1 && !testingBoard[i][j].isRed) || (playerBlack == 0 && testingBoard[i][j].isRed)) {
                        selfPieces.add(testingBoard[i][j]);
                    }
                    else {
                        if (testingBoard[i][j].getClass().getSimpleName().equals("King")) {
                            opponentKing = testingBoard[i][j];
                        }
                    }
                }
            }
        }
    }

    private boolean checkWinning() {
        Piece[][] testingBoard;
        int opponentWay = 0;
        int selfWay = 0;
        for (Piece opponentPiece : opponentPieces) {
            if (opponentPiece.isTerminated) {
                continue;
            }
            highlightMovePos = opponentPiece.legalMove(board);
            highlightKillPos = opponentPiece.legalKillMove();
            highlightMovePos.addAll(highlightKillPos);
            for (Point pos : highlightMovePos) {
                opponentWay += 1;
                // every player move, we must reset the board
                testingBoard = new Piece[10][9];
                resetTestingBoard(testingBoard);

                pieceChangePos(testingBoard, opponentPiece.getPosX(), opponentPiece.getPosY(), (byte) pos.getX(), (byte) pos.getY());

                for (Piece selfPiece : selfPieces) {
                    if (selfPiece.isTerminated) {
                        continue;
                    }
                    highlightMovePos = selfPiece.legalMove(testingBoard);
                    highlightKillPos = selfPiece.legalKillMove();
                    for (Point pos2 : highlightKillPos) {
                        if (pos2.getX() == opponentKing.getPosX() && pos2.getY() == opponentKing.getPosY()) {
                            selfWay += 1;
                            break;
                        }
                    }
                    if (selfWay == opponentWay) {
                        break;
                    }
                }
                if (selfWay != opponentWay) {
                    return false;
                }
            }
        }
        return true;
    }

    public void deSelectPiece() {
        highlightMovePos = new ArrayList<Point>();
        highlightKillPos = new ArrayList<Point>();
        selectedPiece = null;
        pieceIsSelected = false;
    }

    public int index2Coordinate(byte index, boolean isX) {
        if (isX) {
            return (int) ((float) (index)/xLengthDiv*getWidth());
        }
        return (int) ((float) (index)/yLengthDiv*getHeight());
    }

    public byte coordinate2Index(int coordinate, boolean isX) {
        if (isX) {
            return (byte) ((float)coordinate/getWidth()*xLengthDiv);
        }
        return (byte) ((float) coordinate/getHeight()*yLengthDiv);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (pieceIsSelected) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setPaint(Color.GREEN);
            g2D.setStroke(new BasicStroke(7));
            g2D.drawOval(highlightPieceX, highlightPieceY, highlightPieceWidth, highlightPieceHeight);
            g2D.setStroke(new BasicStroke(3));
//             this might not needed, just to show
//            for (Point pos : highlightMovePos) {
//                g2D.drawRect(index2Coordinate((byte) pos.getX(), true),
//                        index2Coordinate((byte) pos.getY(), false),
//                        highlightMoveWidth, highlightMoveHeight);
//            }
//            g2D.setPaint(Color.RED);
//            for (Point pos : highlightKillPos) {
//                g2D.drawRect(index2Coordinate((byte) pos.getX(), true),
//                        index2Coordinate((byte) pos.getY(), false),
//                        highlightMoveWidth, highlightMoveHeight);
//            }
        }
    }

    private class PieceListener implements MouseListener {
        private final Piece thePiece;

        public PieceListener(Piece piece) {
            thePiece = piece;
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            if (!playerTurn || (thePiece.isRed != (playerBlack == 0) && !pieceIsSelected) || GameManager.getGameEnd()) {
                return;
            }
            if (selectedPiece == null) {
                highlightPiece();
                highlightMove(); // might not need, just to show
                highlightKillMove();
                selectedPiece = thePiece;
                pieceIsSelected = true;
                repaint();
            }
            else if (selectedPiece == thePiece) {
                deSelectPiece();
                repaint();
            }
            else if (selectedPiece.isRed == thePiece.isRed) {
                highlightPiece();
                highlightMove(); // might not need, just to show
                highlightKillMove();
                selectedPiece = thePiece;
                repaint();
            }
            else {
                byte prevX = selectedPiece.getPosX();
                byte prevY = selectedPiece.getPosY();
                for (Point pos : highlightKillPos) {
                    if (thePiece.getPosX() == pos.getX() && thePiece.getPosY() == pos.getY()) {
                        killPiece(thePiece.getPosX(), thePiece.getPosY(), thePiece);
                        deSelectPiece();
                        deSelectPiece();
                        GameManager.setPlayerTurn(false);
                        GameManager.moveInfoTransferal(prevX, prevY, thePiece.getPosX(), thePiece.getPosY());
                        repaint();
                        if (checkWinning() || thePiece.getClass().getSimpleName().equals("King")) {
                           GameManager.announceWinner();
                        }
                        break;
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }

        public void highlightPiece() {
            int [] posInBoard = {index2Coordinate(thePiece.getPosX(), true), index2Coordinate(thePiece.getPosY(), false)};

            highlightPieceX = posInBoard[0] + 4;
            highlightPieceY = posInBoard[1] + 6;
        }

        public void highlightMove() {
            highlightMovePos = thePiece.legalMove(board);
        }

        public void highlightKillMove() {
            highlightKillPos = thePiece.legalKillMove();
        }

    }

    public class boardListener implements MouseListener {
        private byte clickX;
        private byte clickY;

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            if (selectedPiece == null) {
                return;
            }
            byte prevX = selectedPiece.getPosX();
            byte prevY = selectedPiece.getPosY();
            clickX = coordinate2Index(mouseEvent.getX(), true);
            clickY = coordinate2Index(mouseEvent.getY(), false);
            for (Point pos : highlightMovePos) {
                if (clickX == pos.getX() && clickY == pos.getY()) {
                    movePiece(clickX, clickY);
                    deSelectPiece();
                    deSelectPiece();
                    GameManager.setPlayerTurn(false);
                    GameManager.moveInfoTransferal(prevX, prevY, clickX, clickY);
                    repaint();
                    if (checkWinning()) {
                        GameManager.announceWinner();
                    }
                    break;
                }
            }

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

}
