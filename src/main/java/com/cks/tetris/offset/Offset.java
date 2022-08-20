/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cks.tetris.offset;

import java.util.Objects;

/**
 *
 * @author colin.saldanha
 */
public class Offset {

    private final int xOff;
    private final int yOff;

    public Offset(int xOff, int yOff) {
        this.xOff = xOff;
        this.yOff = yOff;
    }

    public int getOffsetX() {
        return xOff;
    }

    public int getOffsetY() {
        return yOff;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xOff, yOff);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!getClass().equals(o.getClass())) {
            return false;
        }
        Offset other = (Offset) o;
        return xOff == other.xOff && yOff == other.yOff;
    }
}
