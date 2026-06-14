package com.cks.tetris.ui.listener;

import com.cks.tetris.controller.GameController;
import com.cks.tetris.event.BoardEvent;
import com.cks.tetris.event.GameEventPublisher;
import com.cks.tetris.model.Direction;
import com.cks.tetris.model.state.GameState;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.event.KeyEvent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DirectionKeyListenerTest {

    @Mock
    GameController controller;

    @Mock
    GameEventPublisher publisher;

    @InjectMocks
    DirectionKeyListener listener;

    @Nested
    class KeyPressedTest {

        @Mock
        KeyEvent keyEvent;

        @Test
        void whenLeft() {
            when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);

            listener.keyPressed(keyEvent);

            verify(publisher).publishGameEvent(argThat(event -> {
                assertThat(event).isInstanceOf(BoardEvent.class);
                event.getOperator().apply(mock(GameState.class));
                verify(controller, atLeast(1)).moveActiveBlock(any(GameState.class), eq(Direction.LEFT));
                return true;
            }));
        }

        @Test
        void whenRight() {
            when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);

            listener.keyPressed(keyEvent);

            verify(publisher).publishGameEvent(argThat(event -> {
                assertThat(event).isInstanceOf(BoardEvent.class);
                event.getOperator().apply(mock(GameState.class));
                verify(controller, atLeast(1)).moveActiveBlock(any(GameState.class), eq(Direction.RIGHT));
                return true;
            }));
        }

        @Test
        void whenDown() {
            when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_DOWN);

            listener.keyPressed(keyEvent);

            verify(publisher).publishGameEvent(argThat(event -> {
                assertThat(event).isInstanceOf(BoardEvent.class);
                event.getOperator().apply(mock(GameState.class));
                verify(controller, atLeast(1)).softDropActiveBlock(any(GameState.class));
                return true;
            }));
        }
    }
}
