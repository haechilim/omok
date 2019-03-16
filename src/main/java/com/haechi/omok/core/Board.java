package com.haechi.omok.core;

import com.haechi.omok.display.OmokFrame;

public class Board {
    private Cell[][] cells;
    private OmokFrame omokFrame;
    private boolean whiteTurn = false;
    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;
    private static final int DIAGONAL_RIGHT = 2;
    private static final int DIAGONAL_LEFT = 3;

    public void init() {
        makeCells();

        omokFrame = new OmokFrame("해치 오목", this);
        omokFrame.init();
        omokFrame.redraw();
    }

    private void makeCells() {
        cells = new Cell[19][];

        for(int x = 0; x < 19; x++) {
            cells[x] = new Cell[19];

            for(int y = 0; y < 19; y++) {
                cells[x][y] = new Cell();
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    private boolean cheakComplet(int posX, int posY, int direction) {
        int count = 0;
        int turn = whiteTurn ? Cell.WHITE : Cell.BLACK;

        switch (direction) {
            case HORIZONTAL:
                for (int i = 0;; i++) {
                    if(!validIndex(posX + i, posY)) break;
                    if (cells[posX + i][posY].getType() == turn) count++;
                    else break;
                }
                for (int i = 0;; i++) {
                    if(!validIndex(posX - i, posY)) break;
                    if (cells[posX - i][posY].getType() == turn) count++;
                    else break;
                }
                break;

            case VERTICAL:
                for (int i = 0;; i++) {
                    if(!validIndex(posX, posY + i)) break;
                    if (cells[posX][posY + i].getType() == turn) count++;
                    else break;
                }
                for (int i = 0;; i++) {
                    if(!validIndex(posX, posY - i)) break;
                    if (cells[posX][posY - i].getType() == turn) count++;
                    else break;
                }
                break;

            case DIAGONAL_RIGHT:
                for (int i = 0;; i++) {
                    if(!validIndex(posX, posY - i)) break;
                    if (cells[posX + i][posY - i].getType() == turn) count++;
                    else break;
                }
                for (int i = 0;; i++) {
                    if(!validIndex(posX - i, posY)) break;
                    if (cells[posX - i][posY + i].getType() == turn) count++;
                    else break;
                }
                break;

            case DIAGONAL_LEFT:
                for (int i = 0;; i++) {
                    if(!validIndex(posX, posY)) break;
                    if (cells[posX + i][posY + i].getType() == turn) count++;
                    else break;
                }
                for (int i = 0;; i++) {
                    if(!validIndex(posX - i, posY - i)) break;
                    if (cells[posX - i][posY - i].getType() == turn) count++;
                    else break;
                }
                break;
        }

        return count >= 6;
    }

    private boolean cheakGameOver(int posX, int posY) {
        if(cheakComplet(posX, posY, HORIZONTAL)) return true;
        if(cheakComplet(posX, posY, VERTICAL)) return true;
        if(cheakComplet(posX, posY, DIAGONAL_RIGHT)) return true;
        if(cheakComplet(posX, posY, DIAGONAL_LEFT)) return true;

        return false;
    }

    private boolean validIndex(int posX, int posY) {
        return (posX >= 0 && posX < 19) && (posY >= 0 && posY < 19);
    }

    public void cellClicked(int posX, int posY) {
        Cell cell = cells[posX][posY];

        if(cell.getType() != Cell.NONE) return;

        cell.setType(whiteTurn ? Cell.WHITE : Cell.BLACK);

        omokFrame.redraw();

        if(cheakGameOver(posX, posY)) {
            omokFrame.showResult(whiteTurn ? "WHITE Win!" : "BLACK Win!");
            return;
        }

        whiteTurn = !whiteTurn;
    }
}