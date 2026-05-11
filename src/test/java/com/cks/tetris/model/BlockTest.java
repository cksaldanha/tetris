package com.cks.tetris.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Named.named;

class BlockTest {

    @Nested
    class ConstructorTest {
        @ParameterizedTest
        @MethodSource("invalidOffsets")
        void whenInvalidOffsets(Set<Point> offsets) {
            assertThatThrownBy(() -> new Block(offsets, Color.RED))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void whenValidOffsets() {
            assertThat(new Block(validOffsets(), Color.RED)).isNotNull();
        }

        static Stream<Named<Set<Point>>> invalidOffsets() {
            return Stream.of(
                    named("empty", Collections.emptySet()),
                    named("no origin", Set.of(
                            Point.of(1, 0),
                            Point.of(0, 1),
                            Point.of(1, 1),
                            Point.of(-1, -1))),
                    named("not 4 points", Set.of(
                            Point.ORIGIN,
                            Point.of(0, 1),
                            Point.of(1, 0)))
            );
        }
    }

    @Nested
    class GetOffsetsTest {
        Block block;

        @BeforeEach
        void setUp() {
            block = new Block(validOffsets(), Color.BLUE);
        }

        @Test
        @DisplayName("should return an immutable set")
        void whenCalled() {
               Set<Point> offsets = block.getOffsets();

               assertThat(offsets)
                       .isNotNull()
                       .hasSize(4);

               assertThatThrownBy(() -> offsets.add(Point.ORIGIN))
                       .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    class EqualsAndHashCodeTest {
        @Test
        void whenEqualOffsets() {
            Block b1 = new Block(Set.of(Point.of(0, 0), Point.of(0, 1), Point.of(0, 2), Point.of(0, 3)), Color.GREEN);
            Block b2 = new Block(Set.of(Point.of(0, 0), Point.of(0, 1), Point.of(0, 2), Point.of(0, 3)), Color.GREEN);

            assertThat(b1)
                    .isEqualTo(b2)
                    .hasSameHashCodeAs(b2)
                    .isNotSameAs(b2);
        }

        @Test
        void whenEqualOffsetsDifferentOrder() {
            Block b1 = new Block(Set.of(Point.of(0, 0), Point.of(0, 1), Point.of(0, 2), Point.of(0, 3)), Color.BLUE);
            Block b2 = new Block(Set.of(Point.of(0, 3), Point.of(0, 2), Point.of(0, 1), Point.of(0, 0)), Color.BLUE);

            assertThat(b1)
                    .isEqualTo(b2)
                    .hasSameHashCodeAs(b2)
                    .isNotSameAs(b2);
        }

        @Test
        void whenUnequalOffsets() {
            Block b1 = new Block(Set.of(Point.of(0, 0), Point.of(0, 1), Point.of(0, 2), Point.of(0, 3)), Color.GREEN);
            Block b2 = new Block(Set.of(Point.of(0, 0), Point.of(1, 0), Point.of(2, 0), Point.of(3, 0)), Color.GREEN);

            assertThat(b1)
                    .isNotEqualTo(b2)
                    .doesNotHaveSameHashCodeAs(b2)
                    .isNotSameAs(b2);
        }
    }

    static Set<Point> validOffsets() {
        return Set.of(
                Point.ORIGIN,
                Point.of(0, 1),
                Point.of(0, -1),
                Point.of(1, 0)
        );
    }
}
