package com.cks.tetris.math;

import com.cks.tetris.model.Point;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RotationMatrixTest {

    @Nested
    class ConstructorTest {
        @Test
        void whenClockWise90() {
            int[][] expected = {
                    {0, -1},
                    {1, 0}
            };
            assertThat(RotationMatrix.CLOCK_WISE_90.getAll()).isEqualTo(expected);
        }

        @Test
        void whenCounterClockWise90() {
            int[][] expected = {
                    {0, 1},
                    {-1, 0}
            };
            assertThat(RotationMatrix.COUNTER_CLOCK_WISE_90.getAll()).isEqualTo(expected);
        }

        @Test
        void whenCopying() {
            IntMatrix orig = RotationMatrix.CLOCK_WISE_90;
            IntMatrix copy = new IntMatrix(RotationMatrix.CLOCK_WISE_90);

            assertThat(orig)
                    .isEqualTo(copy)
                    .hasSameHashCodeAs(copy)
                    .isNotSameAs(copy);
        }
    }


    @Nested
    class RotateTest {
        @Test
        void whenOrigin() {
            Point result = Point.ORIGIN.rotate(RotationMatrix.CLOCK_WISE_90);

            assertThat(result)
                    .isEqualTo(Point.ORIGIN)
                    .isSameAs(Point.ORIGIN);
        }

        @Test
        void whenClockWise() {
            Point original = Point.of(1, 3);

            Point rotated1 = original.rotate(RotationMatrix.CLOCK_WISE_90);
            assertThat(rotated1)
                    .isNotEqualTo(original)
                    .isEqualTo(Point.of(-3, 1));

            Point rotated2 = rotated1.rotate(RotationMatrix.CLOCK_WISE_90);

            assertThat(rotated2).isEqualTo(Point.of(-1, -3));

            Point rotated3 = rotated2.rotate(RotationMatrix.CLOCK_WISE_90);
            assertThat(rotated3).isEqualTo(Point.of(3, -1));

            Point rotated4 = rotated3.rotate(RotationMatrix.CLOCK_WISE_90);
            assertThat(rotated4).isEqualTo(original);
        }

        @Test
        void whenCounterClockWise() {
            Point original = Point.of(1, 3);

            Point rotated1 = original.rotate(RotationMatrix.COUNTER_CLOCK_WISE_90);
            assertThat(rotated1).isEqualTo(Point.of(3, -1));

            Point rotated2 = rotated1.rotate(RotationMatrix.COUNTER_CLOCK_WISE_90);
            assertThat(rotated2).isEqualTo(Point.of(-1, -3));

            Point rotated3 = rotated2.rotate(RotationMatrix.COUNTER_CLOCK_WISE_90);
            assertThat(rotated3).isEqualTo(Point.of(-3, 1));

            Point rotated4 = rotated3.rotate(RotationMatrix.COUNTER_CLOCK_WISE_90);
            assertThat(rotated4).isEqualTo(original);
        }
    }
}
