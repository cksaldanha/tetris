package com.cks.tetris.model;

public record Tile(int x, int y, Color color) {

    public Tile(Point p, Color color) {
        this(p.x, p.y, color);
    }
}
