package com.cks.tetris.model.state;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Named.named;

class ScoreTest {

    Score original;

    @BeforeEach
    void setUp() {
        original = new Score(0, 0);
    }

    @Nested
    class ConstructorTest {

        @ParameterizedTest
        @MethodSource("invalidArguments")
        void whenInvalidArguments(TestModel model) {
            assertThatThrownBy(() -> new Score(model.total(), model.lines()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContainingAll("cannot", "0");
        }

        record TestModel(int total, int lines) {
        }

        static Stream<Named<TestModel>> invalidArguments() {
            return Stream.of(
                    named("negative total", new TestModel(-1, 0)),
                    named("negative lines", new TestModel(0, -1))
            );
        }

        @Nested
        class BuilderTest {
            @Test
            void whenUsingSetters() {
                Score actual = Score.builder()
                        .total(0)
                        .lines(1)
                        .build();

                assertThat(actual)
                        .extracting("total", "lines")
                        .containsExactly(0L, 1);
            }

            @Test
            void whenDefault() {
                Score actual = Score.builder().build();

                assertThat(actual)
                        .extracting("total", "lines")
                        .containsExactly(0L, 0);
            }
        }
    }

    @Nested
    class AddTotalTest {
        @Test
        @DisplayName("should increase the total value by supplied amount")
        void whenChange() {
            Score modified = original.addTotal(5);

            assertThat(modified)
                    .isNotEqualTo(original)
                    .extracting("total", "lines")
                    .containsExactly(5L, 0);
        }

        @Test
        void whenNoChange() {
            Score modified = original.addTotal(0);

            assertThat(modified).isSameAs(original);
        }
    }

    @Nested
    class AddLinesTest {
        @Test
        @DisplayName("should increase the amount of lines by given amount")
        void whenChanged() {
            Score modified = original.addLines(5);

            assertThat(modified)
                    .isNotEqualTo(original)
                    .extracting("total", "lines")
                    .containsExactly(0L, 5);
        }

        @Test
        void whenNoChange() {
            Score modified = original.addLines(0);

            assertThat(modified).isSameAs(original);
        }
    }
}
