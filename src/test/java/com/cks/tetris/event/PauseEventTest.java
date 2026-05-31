package com.cks.tetris.event;

import com.cks.tetris.model.state.Score;
import com.cks.tetris.model.state.GameState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PauseEventTest {

    @Nested
    class ConstructorTest {
        @Test
        @DisplayName("should create an UnaryOperator that flips the 'paused' field of game state")
        void whenCalled() {
            GameState originalState = new GameState(null, new Score(0, 0), false, false);
            PauseEvent event = new PauseEvent(this);

            GameState modifiedState = event.getOperator().apply(originalState);

            assertThat(modifiedState).extracting("paused").isEqualTo(true);
        }

        @Test
        @DisplayName("should return the same state if 'gameOver=true'")
        void whenGameOver() {
            GameState originalState = GameState.builder().gameOver(true).build();
            PauseEvent event = new PauseEvent(this);

            GameState modifiedState = event.getOperator().apply(originalState);

            assertThat(modifiedState)
                    .isEqualTo(originalState)
                    .isSameAs(originalState);
        }
    }
}
