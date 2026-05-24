package com.cks.tetris.ui;

import com.cks.tetris.model.Board;
import com.cks.tetris.model.Point;
import com.cks.tetris.model.Tile;
import com.cks.tetris.model.block.Block;
import com.cks.tetris.model.state.GameState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class BoardPanel extends JPanel {

    private final Map<com.cks.tetris.model.Color, Color> colorMap;
    private Board board;

    @Autowired
    public BoardPanel(AtomicReference<GameState> gameState, Map<com.cks.tetris.model.Color, Color> colorMap) {
        super(null);
        this.colorMap = colorMap;
        Dimension size = new Dimension(300, 600);
        setPreferredSize(size);
        setMinimumSize(size);
        setBorder(BorderFactory.createLineBorder(getBackground().darker(), 2));
        this.board = gameState.get().board();
    }

    public void setBoard(Board board) {
        log.debug("Updating board panel with new board state");
        this.board = board;
        repaint();
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 600);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(300, 600);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int cellWidth = panelWidth / board.getColumnCount();
        int cellHeight = panelHeight / board.getRowCount();

        paintBackground(g);
        paintTiles(g, cellWidth, cellHeight);
        paintBlock(g, cellWidth, cellHeight);
    }

    private void paintBackground(Graphics g) {
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void paintTiles(Graphics g, int tileWidth, int tileHeight) {
        for (int x = 0; x < board.getColumnCount(); x++) {
            for (int y = 0; y < board.getRowCount(); y++) {
                if (board.getTileAtCoordinates(x, y) != null) {
                    Tile tile = board.getTileAtCoordinates(x, y);
                    paintTile(g, x * tileWidth, y * tileHeight, tileWidth, tileHeight, colorMap.get(tile.color()));
                }
            }
        }
    }

    private void paintTile(Graphics g, int x, int y, int tileWidth, int tileHeight, Color color) {
        Color prevColor = g.getColor();
        Color highlight = color.brighter();
        Color shadow = color.darker();
        int bevelSize = 3;

        // inner base rectangle
        g.setColor(color);
        g.fillRect(x + bevelSize, y + bevelSize, tileWidth - (bevelSize * 2), tileHeight - (bevelSize * 2));

        // beveled edges
        for (int i = 0; i < bevelSize; i++) {
            g.setColor(highlight);
            g.drawLine(x + i, y + i, x + tileWidth - 1 - i, y + i); // Top edge
            g.drawLine(x + i, y + i, x + i, y + tileHeight - 1 - i); // Left edge

            g.setColor(shadow);
            g.drawLine(x + tileWidth - 1 - i, y + i, x + tileWidth - 1 - i, y + tileHeight - 1 - i); // Right edge
            g.drawLine(x + i, y + tileWidth - 1 - i, x + tileWidth - 1 - i, y + tileHeight - 1 - i); // Bottom edge
        }

        g.setColor(prevColor);
    }

    private void paintBlock(Graphics g, int tileWidth, int tileHeight) {
        Block block = board.getActiveBlock();
        Point position = board.getActiveBlockPosition();
        Color prevColor = g.getColor();
        for (Point offset : block.getOffsets()) {
            int x = (position.x + offset.x) * tileWidth;
            int y = (position.y + offset.y) * tileHeight;
            paintTile(g, x, y, tileWidth, tileHeight, colorMap.get(block.getColor()));
        }
        g.setColor(prevColor);
    }
}
