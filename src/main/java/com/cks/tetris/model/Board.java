package com.cks.tetris.model;

import lombok.Getter;

@Getter
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

}
