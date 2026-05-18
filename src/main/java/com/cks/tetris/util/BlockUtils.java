package com.cks.tetris.util;

import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.Point;

import java.util.Set;
import java.util.stream.Collectors;

public class BlockUtils {

    private BlockUtils() {
        /* non-instantiated utility class */
    }

    public static Set<Point> rotateOffsets(Set<Point> offsets, RotationMatrix rotationMatrix) {
        return offsets.stream()
                .map(pt -> pt.rotate(rotationMatrix))
                .collect(Collectors.toSet());
    }
}
