package com.cks.tetris.model.block;

import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.Color;
import com.cks.tetris.model.Point;
import com.cks.tetris.util.BlockUtils;

import java.util.Set;

public class OBlock extends Block {

    private static final Set<Point> UPRIGHT_OFFSETS = Set.of(
            Point.of(+0, +0),
            Point.of(+1, +0),
            Point.of(+1, +1),
            Point.of(+0, +1)
    );

    public OBlock() {
        this(UPRIGHT_OFFSETS);
    }

    protected OBlock(Set<Point> offsets) {
        super(offsets, Color.YELLOW);
    }

    @Override
    public OBlock rotate(RotationMatrix rotationMatrix) {
        return new OBlock(BlockUtils.rotateOffsets(getOffsets(), rotationMatrix));
    }
}
