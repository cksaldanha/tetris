package com.cks.tetris.model;

public interface Moveable<T extends Moveable<T>> {

    T move(Direction direction, int steps);

    default T moveLeft(int steps) {
        return move(Direction.LEFT, steps);
    }

    default T moveRight(int steps) {
        return move(Direction.RIGHT, steps);
    }

    default T moveDown(int steps) {
        return move(Direction.DOWN, steps);
    }

}
