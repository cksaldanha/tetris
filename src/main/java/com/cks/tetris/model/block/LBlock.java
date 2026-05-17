package com.cks.tetris.model.block;

import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.Color;
import com.cks.tetris.model.Point;

import java.util.Set;

public class LBlock extends Block {

    private static final Set<Point> UPRIGHT_OFFSETS = Set.of(
            Point.of(+0, +0),
            Point.of(+0, +1),
            Point.of(+0, +2),
            Point.of(+1, +2)
    );

    public LBlock() {
        this(UPRIGHT_OFFSETS);
    }

    protected LBlock(Set<Point> offsets) {
        super(offsets, Color.ORANGE);
    }

    @Override
    public LBlock rotate(RotationMatrix rotationMatrix) {
        return new LBlock(rotateOffsets(rotationMatrix));
    }
}
