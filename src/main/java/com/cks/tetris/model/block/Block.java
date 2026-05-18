package com.cks.tetris.model.block;

import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.Color;
import com.cks.tetris.model.Point;
import com.cks.tetris.model.Rotatable;

import java.util.Set;

public abstract class Block implements Rotatable {

    private final Set<Point> offsets;
    private final Color color;

    protected Block(Set<Point> offsets, Color color) {
        this.offsets = Set.copyOf(offsets);
        this.color = color;
        validateOffsets(offsets);
    }

    public Set<Point> getOffsets() {
        return offsets;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public abstract Block rotate(RotationMatrix rotationMatrix);

    @Override
    public boolean equals(Object o) {
        return o instanceof Block other && offsets.equals(other.offsets);
    }

    @Override
    public int hashCode() {
        return offsets.hashCode();
    }

    @Override
    public String toString() {
        return offsets.toString();
    }

    private void validateOffsets(Set<Point> offsets) {
        validateNonNull(offsets);
        validateSize(offsets);
        validateOrigin(offsets);
    }

    private void validateNonNull(Set<Point> offsets) {
        if (offsets == null) {
            throw new IllegalArgumentException("Offsets cannot be null");
        }
    }

    private void validateOrigin(Set<Point> offsets) {
        if (!offsets.contains(Point.ORIGIN)) {
            throw new IllegalArgumentException("The origin offset (0, 0) is required");
        }
    }

    private void validateSize(Set<Point> offsets) {
        if (offsets == null || offsets.size() != 4) {
            throw new IllegalArgumentException("A tetromino must have 4 offsets");
        }
    }
}
