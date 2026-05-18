package com.cks.tetris.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Named.named;

class IntMatrixTest {

    IntMatrix matrix;

    @BeforeEach
    void setUp() {
        int[][] backing = new int[][] {
                {1, 2, 3},
                {4, 5, 6}
        };
        matrix = new IntMatrix(backing);
    }

    @Nested
    class GetRowCountTest {
        @Test
        void whenCalled() {
            assertThat(matrix.getRowCount()).isEqualTo(2);
        }
    }

    @Nested
    class GetColumnCountTest {
        @Test
        void whenCalled() {
            assertThat(matrix.getColumnCount()).isEqualTo(3);
        }
    }

    @Nested
    class GetRowTest {
        @Test
        void whenCalled() {
            assertThat(matrix.getRow(1)).isEqualTo(new int[]{4, 5, 6});
        }
    }

    @Nested
    class GetColumnTest {
        @Test
        void whenCalled() {
            assertThat(matrix.getColumn(2)).isEqualTo(new int[]{3, 6});
        }
    }

    @Nested
    class MultiplyByTest {
        @Test
        void whenCalled() {
            IntMatrix other = new IntMatrix(new int[][]{
                    {1, 2},
                    {3, 4},
                    {5, 6}
            });
            IntMatrix expected = new IntMatrix(new int[][]{
                    {22, 28},
                    {49, 64}
            });
            assertThat(matrix.times(other)).isEqualTo(expected);
        }

        @Test
        void whenIncompatible() {
            IntMatrix other = new IntMatrix(new int[][]{
                    {1, 2},
                    {3, 4}
            });

            assertThatThrownBy(() -> matrix.times(other))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Column count of first matrix must be equal to row count of second matrix");
        }
    }

    @Nested
    class TransposeTest {
        @Test
        void whenCalled() {
            IntMatrix expected = new IntMatrix(new int[][]{
                    {1, 4},
                    {2, 5},
                    {3, 6}
            });
            assertThat(matrix.transpose()).isEqualTo(expected);
        }
    }

    @Nested
    class OfTest {
        @Test
        void whenSingleRow() {
            int[] row = {1, 2, 3};

            IntMatrix expected = new IntMatrix(new int[][]{row});

            assertThat(IntMatrix.of(new int[][]{row})).isEqualTo(expected);
        }

        @Test
        void whenMultipleRows() {
            int[] row1 = {1, 2, 3};
            int[] row2 = {4, 5, 6};

            IntMatrix expected = new IntMatrix(new int[][]{row1, row2});
            assertThat(IntMatrix.of(new int[][]{row1, row2})).isEqualTo(expected);
        }
    }

    @Nested
    class EqualsAndHashCodeTest {
        @Test
        void whenEqual() {
            IntMatrix other = new IntMatrix(new int[][]{{1, 2, 3}, {4, 5, 6}});

            assertThat(matrix)
                    .isEqualTo(other)
                    .hasSameHashCodeAs(other)
                    .isNotSameAs(other);
        }

        @ParameterizedTest
        @MethodSource("unequalMatrices")
        void whenNotEqual(IntMatrix other) {
            assertThat(matrix)
                    .isNotEqualTo(other)
                    .doesNotHaveSameHashCodeAs(other);
        }

        static Stream<Named<IntMatrix>> unequalMatrices() {
            return Stream.of(
                    named("different size", new IntMatrix(new int[][]{{1, 2}})),
                    named("different value", new IntMatrix(new int[][]{{1, 2, 4}, {4, 5, 6}})),
                    named("empty", new IntMatrix(new int[][]{{}}))
            );
        }
    }

    @Nested
    class ToStringTest {
        @Test
        void whenCalled() {
            assertThat(matrix.toString()).contains("1", "2", "3", "4", "5", "6");
        }
    }
}
