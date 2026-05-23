package com.cks.tetris.service;

import com.cks.tetris.config.ScoreProperties;
import com.cks.tetris.config.ScoreProperties.Multiplier;
import com.cks.tetris.model.state.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScoreServiceTest {

    @Spy
    Score original = new Score(0, 0);

    @Spy
    Multiplier scoreMultiplier = new Multiplier(1, 2, 3, 4);

    @Spy
    ScoreProperties scoreProperties = new ScoreProperties(scoreMultiplier);

    @InjectMocks
    ScoreService service;

    @Nested
    class IncreaseTest {
        @Test
        @DisplayName("should increase the score total but keep the lines the same")
        void whenCalled() {
            Score actual = service.increase(original, 5);

            assertThat(actual)
                    .isNotEqualTo(original)
                    .extracting("total", "lines")
                    .containsExactly(5L, 0);

            verifyNoInteractions(scoreProperties);
        }
    }

    @Nested
    class IncreaseWithLevelTest {
        @Test
        @DisplayName("should increase total by amount * level")
        void whenCalled() {
            doReturn(3).when(original).level();
            Score actual = service.increaseWithLevel(original, 5);

            assertThat(actual)
                    .isNotEqualTo(original)
                    .extracting("total", "lines")
                    .containsExactly(15L, 0);

            verifyNoInteractions(scoreProperties);
        }
    }

    @Nested
    class IncreaseByLinesTest {
        @Test
        void whenInvalid() {
            assertThatThrownBy(() -> service.increaseByLines(original, 5))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContainingAll("increase", "line");
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3, 4})
        void whenValid(int lineCount) {
            Score actual = service.increaseByLines(original, lineCount);

            assertThat(actual)
                    .isNotEqualTo(original)
                    .extracting("total", "lines")
                    .containsExactly((long)lineCount, lineCount);

            verify(scoreProperties, times(1)).getMultiplier();
        }
    }
}
