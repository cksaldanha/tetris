package com.cks.tetris.block;

/**
 *
 * @author colin.saldanha
 */
interface Moveable {

    void moveDown();

    default void moveLeft() {
        throw new UnsupportedOperationException();
    }

    default void moveRight() {
        throw new UnsupportedOperationException();
    }

    default void moveUp() {
        throw new UnsupportedOperationException();
    }
}
