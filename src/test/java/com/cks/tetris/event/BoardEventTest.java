package com.cks.tetris.event;

import com.cks.tetris.model.state.Score;
import com.cks.tetris.model.state.GameState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class BoardEventTest {

    @Nested
    class ConstructorTest {
        @Test
        @DisplayName("should wrap the provided operator in a new one")
        void whenCalled() {
            UnaryOperator<GameState> originalOperator = state -> state;

            BoardEvent event = new BoardEvent(this, originalOperator);

            assertThat(event.getOperator()).isNotSameAs(originalOperator);
        }
    }

    @Nested
    class GetOperatorTest {
        @Test
        @DisplayName("should return the same state if paused")
        void whenPaused() {
            GameState originalState = new GameState(null, new Score(0, 0), true, false);
            UnaryOperator<GameState> modifier = state -> new GameState(state.board(), state.score().addTotal(1), state.paused(), false);

            BoardEvent event = new BoardEvent(this, modifier);

            UnaryOperator<GameState> actualOperator = event.getOperator();
            GameState actualState = actualOperator.apply(originalState);

            assertThat(actualState).isSameAs(originalState);
            assertThat(actualOperator).isNotSameAs(modifier);
        }

        @Test
        @DisplayName("should modify the state if unpaused")
        void whenUnpaused() {
            GameState originalState = new GameState(null, new Score(0, 0), false, false);
            UnaryOperator<GameState> originalOperator = state -> new GameState(state.board(), state.score().addTotal(1), state.paused(), false);

            BoardEvent event = new BoardEvent(this, originalOperator);

            UnaryOperator<GameState> actualOperator = event.getOperator();
            GameState actualState = actualOperator.apply(originalState);

            assertThat(actualState)
                    .isNotEqualTo(originalState)
                    .extracting("score").isEqualTo(new Score(1L, 0));
        }
    }
}