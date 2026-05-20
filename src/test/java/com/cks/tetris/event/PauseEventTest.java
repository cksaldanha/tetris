package com.cks.tetris.event;

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
            GameState originalState = new GameState(null, 0, false);
            PauseEvent event = new PauseEvent(this);

            GameState modifiedState = event.getOperator().apply(originalState);

            assertThat(modifiedState).extracting("paused").isEqualTo(true);
        }
    }
}
