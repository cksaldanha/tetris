package com.cks.tetris.model.block;

import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.Color;
import com.cks.tetris.model.Point;
import com.cks.tetris.util.BlockUtils;

import java.util.Set;

public class IBlock extends Block {

    private static final Set<Point> UPRIGHT_OFFSETS = Set.of(
            Point.of(-1, +0),
            Point.of(+0, +0),
            Point.of(+1, +0),
            Point.of(+2, +0)
    );

    public IBlock() {
        this(UPRIGHT_OFFSETS);
    }

    protected IBlock(Set<Point> offsets) {
        super(offsets, Color.CYAN);
    }

    @Override
    public IBlock rotate(RotationMatrix rotationMatrix) {
        return new IBlock(BlockUtils.rotateOffsets(getOffsets(), rotationMatrix));
    }
}
