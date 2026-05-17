package com.cks.tetris.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DirectionTest {

    @Nested
    class CoordinateTest {
        @Test
        void whenLeft() {
            Direction dir = Direction.LEFT;

            assertThat(dir.dx).isEqualTo(-1);
            assertThat(dir.dy).isZero();
        }

        @Test
        void whenRight() {
            Direction dir = Direction.RIGHT;

            assertThat(dir.dx).isEqualTo(1);
            assertThat(dir.dy).isZero();
        }

        @Test
        void whenDown() {
            Direction dir = Direction.DOWN;

            assertThat(dir.dx).isZero();
            assertThat(dir.dy).isEqualTo(1);
        }
    }

    @Nested
    class ReverseTest {
        @Test
        void whenLeft() {
            assertThat(Direction.LEFT.reverse()).isEqualTo(Direction.RIGHT);
        }

        @Test
        void whenRight() {
            assertThat(Direction.RIGHT.reverse()).isEqualTo(Direction.LEFT);
        }

        @Test
        void whenDown() {
            assertThat(Direction.DOWN.reverse()).isEqualTo(Direction.UP);
        }

        @Test
        void whenUp() {
            assertThat(Direction.UP.reverse()).isEqualTo(Direction.DOWN);
        }
    }
}
