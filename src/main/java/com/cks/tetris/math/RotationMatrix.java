package com.cks.tetris.math;

public final class RotationMatrix extends IntMatrix {

    public static final RotationMatrix CLOCK_WISE_90 = new RotationMatrix(Math.PI / 2);
    public static final RotationMatrix COUNTER_CLOCK_WISE_90 = new RotationMatrix(-Math.PI / 2);

    private RotationMatrix(double rad) {
        super(new int[][]{
                {Math.round((float) Math.cos(rad)), -Math.round((float) Math.sin(rad))},
                {Math.round((float) Math.sin(rad)), Math.round((float) Math.cos(rad))}
        });
    }

}
