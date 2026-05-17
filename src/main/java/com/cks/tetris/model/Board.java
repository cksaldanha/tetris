package com.cks.tetris.model;

import com.cks.tetris.model.block.Block;
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

    private final int rowCount;
    private final int columnCount;
    private final Block activeBlock;
    private final Point activeBlockPosition;
    private final Tile[][] tiles;

    public Board(Tile[][] tiles, Block activeBlock, Point activeBlockPosition) {
        this.tiles = tiles.clone();
        this.rowCount = tiles.length;
        this.columnCount = tiles[0].length;
        this.activeBlock = activeBlock;
        this.activeBlockPosition = activeBlockPosition;
    }

    public Tile[][] getTiles() {
        return tiles.clone();
    }

    public Tile getTile(int r, int c) {
        return tiles[r][c];
    }

    public Tile getTileAtCoordinates(int x, int y) {
        return tiles[y][x];
    }

    public boolean containsTileAtCoordinates(int x, int y) {
        return getTileAtCoordinates(x, y) != null;
    }

    public Set<Integer> getFullRows() {
        return IntStream.range(0, rowCount)
                .filter(this::isFullRow)
                .boxed()
                .collect(Collectors.toSet());
    }

    private boolean isFullRow(int row) {
        for (int c = 0; c < columnCount; c++) {
            if (tiles[row][c] == null) {
                return false;
            }
        }
        return true;
    }
}
