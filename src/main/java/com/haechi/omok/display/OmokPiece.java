package com.haechi.omok.display;

public class OmokPiece {
    public static String getPiece(boolean white) {
        return String.format("%s.png", getKey(white));
    }

    public static String getKey(boolean white) {
        return white ? "white" : "black";
    }
}