package com.cks.tetris.util;

import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BlockUtilsTest {

    @Nested
    class RotateOffsetsTest {

        Set<Point> offsets;

        @BeforeEach
        void setUp() {
            offsets = Set.of(
                    Point.of(+0, +0),
                    Point.of(+0, +1),
                    Point.of(+0, +2)
            );
        }

        @Test
        void whenRotateClockWise() {
            Set<Point> rotated = BlockUtils.rotateOffsets(offsets, RotationMatrix.CLOCK_WISE_90);

            assertThat(rotated)
                    .containsExactlyInAnyOrder(
                            Point.of(-0, +0),
                            Point.of(-1, +0),
                            Point.of(-2, +0)
                    );
        }

        @Test
        void whenRotateCounterClockWise() {
            Set<Point> rotated = BlockUtils.rotateOffsets(offsets, RotationMatrix.COUNTER_CLOCK_WISE_90);

            assertThat(rotated)
                    .containsExactlyInAnyOrder(
                            Point.of(+0, +0),
                            Point.of(+1, +0),
                            Point.of(+2, +0)
                    );
        }
    }
}
