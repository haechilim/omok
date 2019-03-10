package com.haechi.omok.display;

import java.awt.*;

public class OmokPiece {
    private static final String classpath = OmokPiece.class.getResource("/").getPath();

    public static Image getPiece(boolean white) {
        String path = String.format("%s%s.png", classpath, getKey(white));
        return Util.loadImage(path);
    }

    public static String getKey(boolean white) {
        return white ? "white" : "black";
    }
}
