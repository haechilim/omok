package com.haechi.omok.core;

public class Cell {
    public static int NONE = 0;
    public static int WHITE = 1;
    public static int BLACK = 2;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
