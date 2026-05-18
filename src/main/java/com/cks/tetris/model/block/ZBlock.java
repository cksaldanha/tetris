package com.cks.tetris.model.block;

import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.Color;
import com.cks.tetris.model.Point;

import java.util.Set;

public class ZBlock extends Block {

    public static final Set<Point> UPRIGHT_OFFSETS = Set.of(
            Point.of(+0, +0),
            Point.of(+1, +0),
            Point.of(+1, +1),
            Point.of(+2, +1)
    );

    public ZBlock() {
        this(UPRIGHT_OFFSETS);
    }

    protected ZBlock(Set<Point> offsets) {
        super(offsets, Color.RED);
    }

    @Override
    public ZBlock rotate(RotationMatrix rotationMatrix) {
        return new ZBlock(rotateOffsets(rotationMatrix));
    }
}
