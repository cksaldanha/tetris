package com.cks.tetris.model.block;

import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.Color;
import com.cks.tetris.model.Point;
import com.cks.tetris.util.BlockUtils;

import java.util.Set;

public class JBlock extends Block {

    private static final Set<Point> UPRIGHT_OFFSETS = Set.of(
            Point.of(+0, +0),
            Point.of(+0, +1),
            Point.of(+0, +2),
            Point.of(-1, +2)
    );

    public JBlock() {
        this(UPRIGHT_OFFSETS);
    }

    protected JBlock(Set<Point> offsets) {
        super(offsets, Color.NAVY);
    }

    @Override
    public JBlock rotate(RotationMatrix rotationMatrix) {
        return new JBlock(BlockUtils.rotateOffsets(getOffsets(), rotationMatrix));
    }
}
