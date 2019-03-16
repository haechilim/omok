package com.haechi.omok.display;

import com.haechi.omok.core.Board;
import com.haechi.omok.core.Cell;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class OmokBoard extends Panel implements MouseListener {
    private static final String classpath = OmokBoard.class.getResource("/").getPath();
    private static final Color colorLine = new Color(0, 0, 0);
    private Font font = new Font("Monospaced", Font.BOLD, 30);

    private Board board;
    private Label result;
    private Cell[][] cells;
    private double cellMargin;
    private double cellWidth;
    private double cellHeight;
    private double circleSize;

    private Image offscreen;
    private Image background;
    private Map<String, Image> imagePiecies;

    public OmokBoard(Board board) {
        this.board = board;
        this.cells = board.getCells();
    }

    public void init() {
        imagePiecies = new HashMap<>();

        setLayout(null);
        initDimensions();
        makeComponents();
        addMouseListener(this);
    }

    private void initDimensions() {
        cellWidth = getWidth() / 19.0;
        cellHeight = getHeight() / 19.0;
        cellMargin = cellWidth * 0.1;
        circleSize = cellHeight / 4.0;
    }

    private void makeComponents() {
        result = new Label();
        result.setVisible(false);
        result.setFont(font);
        result.setForeground(Color.WHITE);
        result.setBackground(new Color(90, 0, 30));
        result.setAlignment(Label.CENTER);
        result.setBounds(0, getHeight()/2 - 50, getWidth(), 100);
        add(result);
    }

    public void showResult(String text) {
        result.setText(text);
        result.setVisible(true);
    }

    public void redraw() {
        if(offscreen == null) offscreen = createImage(getWidth(), getHeight());

        Graphics graphics = offscreen.getGraphics();
        drawBackground();
        graphics.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        if(cells == null) return;

        for(int x=0; x<cells.length; x++) {
            for(int y=0; y<cells[x].length; y++) {
                drawPiece(graphics, x, y, cells[x][y]);
            }
        }

        revalidate();
        repaint();
    }

    private void drawBackground() {
        if(background != null) return;

        background = createImage(getWidth(), getHeight());
        Image back = Util.loadImage(String.format("%s%s", classpath, "woodpattern.jpg"));

        Graphics graphics = background.getGraphics();
        graphics.drawImage(back, 0, 0, getWidth(), getHeight(), null);
        drawLines(graphics);
        drawDots(graphics);
    }

    private void drawLines(Graphics graphics) {
        int marginX = (int)(cellWidth / 2);
        int marginY = (int)(cellHeight / 2);

        graphics.setColor(colorLine);

        for(double y = marginY; y < getHeight(); y += cellHeight)
            graphics.drawLine(marginX, (int)y, getWidth() - marginX - 1, (int)y);

        for(double x = marginX; x < getWidth(); x += cellWidth)
            graphics.drawLine((int)x, marginY, (int)x, getHeight() - marginY);

        graphics.drawRect(marginX - 1, marginY - 1, getWidth() - marginX*2 + 1, getHeight() - marginY*2 + 2);
    }

    private void drawDots(Graphics graphics) {
        drawDot(graphics, 3, 3);
        drawDot(graphics, 9, 3);
        drawDot(graphics, 15, 3);
        drawDot(graphics, 3, 9);
        drawDot(graphics, 9, 9);
        drawDot(graphics, 15, 9);
        drawDot(graphics, 3, 15);
        drawDot(graphics, 9, 15);
        drawDot(graphics, 15, 15);
    }

    private void drawDot(Graphics graphics, int posX, int posY) {
        int marginX = (int)(cellWidth / 2);
        int marginY = (int)(cellHeight / 2);
        int x = marginX + (int)(cellWidth * posX);
        int y = marginY + (int)(cellHeight * posY);

        graphics.fillOval(x - (int)(circleSize/2), y - (int)(circleSize/2), (int)circleSize, (int)circleSize);
    }

    private void drawPiece(Graphics graphics, int posX, int posY, Cell cell) {
        int status = cell.getStatus();
        if(status == Cell.NONE) return;

        int x = (int)(posX * cellWidth + cellMargin);
        int y = (int)(posY * cellHeight);
        graphics.drawImage(getPieceImage(status==Cell.WHITE), x, y, (int)cellHeight, (int)cellHeight, this);
    }

    private Image getPieceImage(boolean white) {
        String key = OmokPiece.getKey(white);
        Image image = imagePiecies.get(key);

        if(image == null) {
            image = OmokPiece.getPiece(white);
            imagePiecies.put(key, image);
        }

        return image;
    }

    @Override
    public void update(Graphics graphics) {
        paint(graphics);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.drawImage(offscreen, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void mousePressed(MouseEvent event) {}

    @Override
    public void mouseReleased(MouseEvent event) {
        board.cellClicked(event.getX() / (int)cellWidth, event.getY() / (int)cellHeight);
    }

    @Override
    public void mouseClicked(MouseEvent event) {}

    @Override
    public void mouseEntered(MouseEvent event) {}

    @Override
    public void mouseExited(MouseEvent event) {}
}
