package com.cks.tetris.block;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 *
 * @author colin.saldanha
 */
public class BlockTile extends JComponent {

    public static final int CELL_WIDTH = 25, CELL_HEIGHT = 25;

    private int posX, posY;
    private Color color;

    public BlockTile(Color color) {
        this(0, 0, color);
    }

    public BlockTile(int x, int y, Color color) {
        this.posX = x;
        this.posY = y;
        this.color = color;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(CELL_WIDTH, CELL_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color);
        g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        g.setColor(color.darker());
        g.drawRect(0, 0, getWidth() + 1, getHeight() + 1);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int x) {
        posX = x;
    }    //should do checks

    public int getPosY() {
        return posY;
    }

    public void setPosY(int y) {
        posY = y;
    }    //should do checks

    @Override
    public String toString() {
        return "BlockTile[posX=" + posX + ", posY=" + posY + "]";
    }
}
