package com.cks.tetris.board;

import com.cks.tetris.block.Block;
import com.cks.tetris.block.BlockTile;
import com.cks.tetris.offset.BlockOffsets;
import com.cks.tetris.offset.Offset;

import java.awt.Component;
import java.awt.Point;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

import java.util.stream.IntStream;

/**
 *
 * @author colin.saldanha
 */
public class Scorer implements Runnable {

    private Board board;

    public Scorer(Board b) {
        this.board = b;
    }

    @Override
    public void run() {
        Block last = board.getActiveBlock();

        //check all rows of the 'last' block position
        Point loc = last.getLocation();
        List<Offset> offs = BlockOffsets.getOffsetList(last.getShape(), last.getOrientation());
        int[] rows = offs.stream().mapToInt(o -> loc.y + o.getOffsetY()).distinct().filter(y -> checkLine(y)).sorted().toArray(); //all rows with full lines
        int multiplier = rows.length;

        //lines were filled
        if (multiplier > 0) {
            long addToScore = (100 * multiplier) * multiplier;

            //zero out the rows in Board tile array
            Arrays.stream(rows).forEach(y -> IntStream.range(0, Board.MAX_COLS).forEach(x -> board.setTileAt(x, y, 0)));

            //remove tiles from board panel
            Arrays.stream(rows).forEach(y -> IntStream.range(0, Board.MAX_COLS).forEach(x -> removeTile(x, y)));

            //Drop tiles until they hit (update array and tile components)
            assert max(rows) == rows[rows.length - 1];  //rows should be ordered, with max unit at the end

            int y = Board.MAX_ROWS;
            int drop = 0;

            while (y > 0) {
                y--;
                if (containsRow(y, rows)) {
                    drop++;
                    continue;
                }
                for (int x = 0; x < Board.MAX_COLS; x++) {
                    updateTile(x, y, drop);
                    updateTileset(x, y, drop);
                }
            }

            board.addScore(addToScore);
            board.revalidate();
        }
    }

    private boolean containsRow(int row, int[] rows) {
        for (int i = 0; i < rows.length; i++) {
            if (row == rows[i]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkLine(int y) {
        for (int x = 0; x < Board.MAX_COLS; x++) {
            if (board.getTileAt(x, y) == 0) {
                return false;
            }
        }

        return true;
    }

    //Removes BlockTile component from Board panel
    private void removeTile(int x, int y) {
        Component c = board.getComponentAt(x * BlockTile.CELL_WIDTH, y * BlockTile.CELL_HEIGHT);
        if (c != null && c instanceof BlockTile) {
            board.remove(c);
        }
    }

    //update the component
    private void updateTile(int x, int y, int drop) {
        Component c = board.getComponentAt(x * BlockTile.CELL_WIDTH, y * BlockTile.CELL_HEIGHT);
        if (c != null && c instanceof BlockTile) {
            BlockTile t = (BlockTile) c;
            t.setPosY(t.getPosY() + drop);
        }
    }

    //update the array
    private void updateTileset(int x, int y, int drop) {
        Board.reentLock.lock();
        try {
            int val = board.getTileAt(x, y);
            board.setTileAt(x, y, 0);
            board.setTileAt(x, y + drop, val);
        } finally {
            Board.reentLock.unlock();
        }
    }

    private static int max(int... vals) {
        OptionalInt opt = Arrays.stream(vals).max();
        return opt.orElseThrow(IllegalArgumentException::new);
    }
}
