package com.haechi.omok.core;

import com.haechi.omok.display.OmokFrame;

public class Board {
    private Cell[][] cells;
    private OmokFrame omokFrame;

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

    public void cellClicked(int posX, int posY) {
        omokFrame.redraw();
    }
}
