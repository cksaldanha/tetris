package com.cks.tetris.math;

import com.cks.tetris.model.Point;

public final class RotationMatrix extends IntMatrix {

    public static final RotationMatrix CLOCK_WISE_90 = new RotationMatrix(Math.PI / 2);
    public static final RotationMatrix COUNTER_CLOCK_WISE_90 = new RotationMatrix(-Math.PI / 2);

    private RotationMatrix(double rad) {
        super(new int[][]{
                {Math.round((float) Math.cos(rad)), -Math.round((float) Math.sin(rad))},
                {Math.round((float) Math.sin(rad)), Math.round((float) Math.cos(rad))}
        });
    }

    public Point rotate(Point p) {
        int x = get(0, 0) * p.x + get(0, 1) * p.y;
        int y = get(1, 0) * p.x + get(1, 1) * p.y;
        return Point.of(x, y);
    }

}
