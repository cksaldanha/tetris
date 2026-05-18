package com.cks.tetris.model;

import com.cks.tetris.model.block.Block;
import com.cks.tetris.math.Matrix;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@ToString
@EqualsAndHashCode
public class Board {

    private final Block activeBlock;
    private final Point activeBlockPosition;
    private final Matrix<Tile> tiles;

    public Board(Matrix<Tile> tiles, Block activeBlock, Point activeBlockPosition) {
        this.tiles = tiles;
        this.activeBlock = activeBlock;
        this.activeBlockPosition = activeBlockPosition;
    }

    public Matrix<Tile> getTiles() {
        return tiles;
    }

    public Tile getTileAtCoordinates(int x, int y) {
        return tiles.get(y, x);
    }

    public boolean containsTileAtCoordinates(int x, int y) {
        return tiles.get(y, x) != null;
    }

    public int getRowCount() {
        return tiles.getRowCount();
    }

    public int getColumnCount() {
        return tiles.getColumnCount();
    }

    public Set<Integer> getFullRows() {
        return IntStream.range(0, tiles.getRowCount())
                .filter(this::isFullRow)
                .boxed()
                .collect(Collectors.toSet());
    }

    private boolean isFullRow(int row) {
        for (int c = 0; c < tiles.getColumnCount(); c++) {
            if (tiles.get(row, c) == null) {
                return false;
            }
        }
        return true;
    }
}
