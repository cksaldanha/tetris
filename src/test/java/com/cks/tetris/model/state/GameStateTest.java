package com.cks.tetris.model.state;

import com.cks.tetris.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameStateTest {

    @Mock
    Board board;

    @Mock
    Score score;

    @Nested
    class MutateTest {
        GameState original;

        @BeforeEach
        void setUp() {
            original = new GameState(board, score, false, false);
        }

        @Test
        void whenNoMutations() {
            GameState modified = original.mutate().build();

            assertThat(modified)
                    .isEqualTo(original)
                    .isNotSameAs(original);
        }

        @Test
        void whenMutations() {
            Score newScore = mock(Score.class);
            GameState modified = original.mutate().score(newScore).build();

            assertThat(modified).isNotEqualTo(original);
        }
    }
}
