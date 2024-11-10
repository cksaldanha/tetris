package com.cks.tetris.block;

/**
 *
 * @author colin.saldanha
 */
interface Rotateable {

    default void rotateLeft() {
        throw new UnsupportedOperationException();
    }

    void rotateRight();
}
