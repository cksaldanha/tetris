package com.cks.tetris.model.block;

import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.Color;
import com.cks.tetris.model.Point;
import com.cks.tetris.util.BlockUtils;

import java.util.Set;

public class SBlock extends Block {

    private static final Set<Point> UPRIGHT_OFFSETS = Set.of(
            Point.of(+0, +0),
            Point.of(-1, +0),
            Point.of(-1, +1),
            Point.of(-2, +1)
    );

    public SBlock() {
        this(UPRIGHT_OFFSETS);
    }

    protected SBlock(Set<Point> offsets) {
        super(offsets, Color.GREEN);
    }

    @Override
    public SBlock rotate(RotationMatrix rotationMatrix) {
        return new SBlock(BlockUtils.rotateOffsets(getOffsets(), rotationMatrix));
    }
}
