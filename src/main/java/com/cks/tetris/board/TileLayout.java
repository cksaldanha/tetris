package com.cks.tetris.board;

import com.cks.tetris.block.BlockTile;

import java.awt.*;

/**
 *
 * @author colin.saldanha
 */
public class TileLayout implements LayoutManager {

    @Override
    public void addLayoutComponent(String name, Component c) {
    }

    @Override
    public void removeLayoutComponent(Component c) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        final int rows = Board.MAX_ROWS;
        final int cols = Board.MAX_COLS;

        Insets insets = parent.getInsets();

        int width = insets.left + insets.right + (cols * BlockTile.CELL_WIDTH);
        int height = insets.top + insets.bottom + (rows * BlockTile.CELL_HEIGHT);

        return new Dimension(width, height);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {

        Insets insets = parent.getInsets();

        int n = parent.getComponentCount();
        for (int i = 0; i < n; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
                if (c instanceof BlockTile) {//should always be true
                    BlockTile tile = (BlockTile) c;
                    int x = insets.left + tile.getPosX() * BlockTile.CELL_WIDTH;
                    int y = insets.top + tile.getPosY() * BlockTile.CELL_HEIGHT;
                    c.setBounds(x, y, BlockTile.CELL_WIDTH, BlockTile.CELL_HEIGHT);
                }
            }
        }
    }

}
