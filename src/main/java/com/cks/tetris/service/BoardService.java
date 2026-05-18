package com.cks.tetris.service;

import com.cks.tetris.model.block.Block;
import com.cks.tetris.model.Board;
import com.cks.tetris.model.Point;
import com.cks.tetris.model.Tile;
import com.cks.tetris.math.Matrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class BoardService {

    public Board setActiveBlock(Board board, Block block, Point position) {
        if (block.equals(board.getActiveBlock()) && position.equals(board.getActiveBlockPosition())) {
            return board;
        }
        return new Board(board.getTiles(), block, position);
    }

    public Board lockActiveBlock(Board board) {
        Block activeBlock = board.getActiveBlock();
        Point activeBlockPosition = board.getActiveBlockPosition();

        List<List<Tile>> tiles = board.getTiles().getAll();

        for (Point offset : activeBlock.getOffsets()) {
            int x = activeBlockPosition.x + offset.x;
            int y = activeBlockPosition.y + offset.y;

            tiles.get(y).set(x, Tile.ofColor(activeBlock.getColor()));
        }

        return new Board(new Matrix<>(tiles), activeBlock, activeBlockPosition);
    }

    public boolean canPlaceBlock(Board board, Block block, Point pos) {
        for (Point offset : block.getOffsets()) {
            int x = pos.x + offset.x;
            int y = pos.y + offset.y;

            if (x < 0 || x >= board.getColumnCount() || y >= board.getRowCount()) {
                return false;
            }

            if (board.containsTileAtCoordinates(x, y)) {
                return false;
            }
        }
        return true;
    }

    public Set<Integer> getFullRows(Board board) {
        return board.getFullRows();
    }

    public Board removeRows(Board board, Set<Integer> rowIndices) {
        List<List<Tile>> tiles = board.getTiles().getAll();

        for (int r : rowIndices) {
            for (int c = 0; c < board.getColumnCount(); c++) {
                tiles.get(r).set(c, null);
            }
        }

        int shift = 0;
        for (int r = board.getRowCount() - 1; r >= 0; r--) {
            for (int c = 0; c < board.getColumnCount(); c++) {
                tiles.get(r + shift).set(c, tiles.get(r).get(c));
                if (shift > 0) {
                    tiles.get(r).set(c, null);
                }
            }
            if (rowIndices.contains(r)) {
                shift++;
            }
        }

        return new Board(new Matrix<>(tiles), board.getActiveBlock(), board.getActiveBlockPosition());
    }
}
