package com.haechi.omok.core;

public class Cell {
    public static int NONE = 0;
    public static int WHITE = 1;
    public static int BLACK = 2;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setWhite(int white) {
        type = white;
    }

    public void setBlack(int black) {
        type = black;
    }
}
