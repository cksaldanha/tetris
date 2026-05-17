package com.cks.tetris.model.block;

import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.Color;
import com.cks.tetris.model.Point;

import java.util.Set;

public class TBlock extends Block {

    private static final Set<Point> UPRIGHT_OFFSETS = Set.of(
            Point.of(-1, +0),
            Point.of(+0, +0),
            Point.of(+1, +0),
            Point.of(+0, -1)
    );

    public TBlock() {
        this(UPRIGHT_OFFSETS);
    }

    protected TBlock(Set<Point> offsets) {
        super(offsets, Color.PURPLE);
    }

    @Override
    public TBlock rotate(RotationMatrix rotationMatrix) {
        return new TBlock(rotateOffsets(rotationMatrix));
    }
}
