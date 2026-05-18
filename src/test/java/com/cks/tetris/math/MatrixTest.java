package com.cks.tetris.math;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MatrixTest {

    @Nested
    class ConstructorTest {
        @Nested
        class RowAndColConstructorTest {
            @ParameterizedTest
            @CsvSource({"-1,2", "2,-1", "-1,-1", "0, 0", "1,0", "0,1"})
            void whenInvalid(int rows, int cols) {
                assertThatThrownBy(() -> new Matrix<>(rows, cols))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("Row and column counts must be positive");
            }

            @Test
            void whenValid() {
                assertThat(new Matrix<>(1, 1)).isNotNull();
            }
        }

        @Nested
        class CopyConstructorTest {
            @Test
            void whenValid() {
                Matrix<Integer> original = new Matrix<>(List.of(
                        Arrays.asList(1, 2, 3),
                        Arrays.asList(4, 5, 6)
                ));

                Matrix<Integer> copy = new Matrix<>(original);

                assertThat(copy)
                        .isEqualTo(original)
                        .isNotSameAs(original);
            }

            @Test
            void whenNull() {
                Matrix<Integer> nullMatrix = null;

                assertThatThrownBy(() -> new Matrix<>(nullMatrix))
                        .isInstanceOf(NullPointerException.class)
                        .hasMessageContaining("matrix cannot be null");
            }
        }

        @Nested
        class ListConstructorTest {
            @Test
            void whenValid() {
                List<List<Integer>> lists = List.of(
                        Arrays.asList(1, 2, 3),
                        Arrays.asList(4, 5, 6)
                );

                Matrix<Integer> matrix = new Matrix<>(lists);

                assertThat(matrix.getAll())
                        .isEqualTo(lists)
                        .isNotSameAs(lists);
            }

            @Test
            void whenNull() {
                List<List<Integer>> nullLists = null;

                assertThatThrownBy(() -> new Matrix<>(nullLists))
                        .isInstanceOf(NullPointerException.class)
                        .hasMessageContaining("cannot be null");
            }
        }
    }

    @Nested
    class GetAllTest {
        @Test
        void whenCalled() {
            List<List<Integer>> original = List.of(
                    Arrays.asList(1, 2, 3),
                    Arrays.asList(4, 5, 6)
            );
            Matrix<Integer> matrix = new Matrix<>(original);

            List<List<Integer>> all = matrix.getAll();

            assertThat(all)
                    .isEqualTo(original)
                    .isNotSameAs(original);

            //test mutability of returned list
            assertThatNoException().isThrownBy(() -> all.get(0).set(0, 99));

            //test that original list is not affected by mutation of returned list
            assertThat(matrix.get(0, 0)).isEqualTo(1);
        }
    }

    @Nested
    class EqualsAndHashCodeTest {
        @Test
        void whenEqual() {
            Matrix<Integer> m1 = new Matrix<>(List.of(
                    Arrays.asList(1, 2, 3),
                    Arrays.asList(4, 5, 6)
            ));
            Matrix<Integer> m2 = new Matrix<>(List.of(
                    Arrays.asList(1, 2, 3),
                    Arrays.asList(4, 5, 6)
            ));

            assertThat(m1)
                    .isEqualTo(m2)
                    .hasSameHashCodeAs(m2)
                    .isNotSameAs(m2);
        }

        @Test
        void whenUnequal() {
            Matrix<Integer> m1 = new Matrix<>(List.of(
                    Arrays.asList(1, 2, 3),
                    Arrays.asList(4, 5, 6)
            ));
            Matrix<Integer> m2 = new Matrix<>(List.of(
                    Arrays.asList(1, 2),
                    Arrays.asList(3, 4),
                    Arrays.asList(5, 6)
            ));

            assertThat(m1)
                    .isNotEqualTo(m2)
                    .doesNotHaveSameHashCodeAs(m2);
        }
    }
}
