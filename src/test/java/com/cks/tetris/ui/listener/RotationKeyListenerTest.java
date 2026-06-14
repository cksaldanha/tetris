package com.cks.tetris.ui.listener;

import com.cks.tetris.controller.GameController;
import com.cks.tetris.event.BoardEvent;
import com.cks.tetris.event.GameEventPublisher;
import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.state.GameState;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.event.KeyEvent;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RotationKeyListenerTest {

    @Mock
    GameController controller;

    @Mock
    GameEventPublisher publisher;

    @InjectMocks
    RotationKeyListener listener;

    @Nested
    class KeyPressedTest {
        @Mock
        KeyEvent keyEvent;

        @Test
        void whenRotateClockWiseWithUp() {
            assertRotation(KeyEvent.VK_UP, RotationMatrix.CLOCK_WISE_90);
        }

        @Test
        void whenRotateClockWiseWithX() {
            assertRotation(KeyEvent.VK_X, RotationMatrix.CLOCK_WISE_90);
        }

        @Test
        void whenRotateCounterClockWise() {
            assertRotation(KeyEvent.VK_Z, RotationMatrix.COUNTER_CLOCK_WISE_90);
        }

        void assertRotation(int keyCode, RotationMatrix rotationMatrix) {
            when(keyEvent.getKeyCode()).thenReturn(keyCode);

            listener.keyPressed(keyEvent);

            verify(publisher).publishGameEvent(argThat(event -> {
                assertThat(event).isInstanceOf(BoardEvent.class);
                BoardEvent boardEvent = (BoardEvent) event;
                assertThat(boardEvent.getSource()).isEqualTo(listener);
                assertThat(boardEvent.getOperator()).isNotNull();
                UnaryOperator<GameState> operator = boardEvent.getOperator();
                operator.apply(mock(GameState.class));
                verify(controller, atLeastOnce()).rotateActiveBlock(any(GameState.class), eq(rotationMatrix));
                return true;
            }));
        }
    }
}
