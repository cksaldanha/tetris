/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
