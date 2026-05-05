package com.cks.tetris.model.block;

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
