package com.cks.tetris.model;

import com.cks.tetris.math.IntMatrix;
import com.cks.tetris.math.RotationMatrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class PointTest {

    @Nested
    class OfTest {
        @Test
        @DisplayName("should return the same instance if in cache")
        void whenInCache() {
            Point p1 = Point.of(2, 2);
            Point p2 = Point.of(2, 2);

            assertThat(p1).isSameAs(p2);
        }

        @Test
        @DisplayName("should return new instances if outside of cache")
        void whenNotInCache() {
            Point p1 = Point.of(10, 10);
            Point p2 = Point.of(10, 10);

            assertThat(p1)
                    .isEqualTo(p2)
                    .isNotSameAs(p2);
        }
    }

    @Nested
    class RotateTest {
        RotationMatrix clockWise90Spy = spy(RotationMatrix.CLOCK_WISE_90);
        RotationMatrix counterClockWise90Spy = spy(RotationMatrix.COUNTER_CLOCK_WISE_90);
        Point point;

        @BeforeEach
        void setUp() {
            point = Point.of(1, 3);
        }

        @Test
        void whenClockWise90() {
            point.rotate(clockWise90Spy);

            verify(clockWise90Spy).times(new IntMatrix(new int[][]{{1}, {3}}));
        }

        @Test
        void whenCounterClockWise90() {
            point.rotate(counterClockWise90Spy);

            verify(counterClockWise90Spy).times(new IntMatrix(new int[][]{{1}, {3}}));
        }
    }
}
