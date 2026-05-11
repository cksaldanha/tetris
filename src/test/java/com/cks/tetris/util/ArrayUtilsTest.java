package com.cks.tetris.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayUtilsTest {

    @Nested
    class EqualsTest {
        @ParameterizedTest
        @MethodSource("equalArrays")
        void whenEqual(int[][] left, int[][] right) {
            assertThat(ArrayUtils.equals(left, right)).isTrue();
        }

        @ParameterizedTest
        @MethodSource("unequalArrays")
        void whenUnequal(int[][] left, int[][] right) {
            assertThat(ArrayUtils.equals(left, right)).isFalse();
        }

        static Stream<Arguments> equalArrays() {
            return Stream.of(
                    Arguments.of(new int[][]{{1, 2}, {3, 4}}, new int[][]{{1, 2}, {3, 4}}),
                    Arguments.of(new int[][]{{1}, {2}}, new int[][]{{1}, {2}}),
                    Arguments.of(new int[][]{{}, {}}, new int[][]{{}, {}}),
                    Arguments.of(null, null)
            );
        }

        static Stream<Arguments> unequalArrays() {
            return Stream.of(
                    Arguments.of(new int[][]{{1, 2}, {3, 4}}, new int[][]{{3, 4}, {1, 2}}),
                    Arguments.of(new int[][]{{1}, {3, 4}}, new int[][]{{3, 4}, {1, 2}}),
                    Arguments.of(new int[][]{{1, 2}, {3}}, new int[][]{{3, 4}, {3}}),
                    Arguments.of(null, new int[][]{}),
                    Arguments.of(new int[][]{}, null)
            );
        }
    }
}
