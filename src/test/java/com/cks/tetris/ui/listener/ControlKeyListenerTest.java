package com.cks.tetris.ui.listener;

import com.cks.tetris.controller.GameController;
import com.cks.tetris.event.BoardEvent;
import com.cks.tetris.event.GameEventPublisher;
import com.cks.tetris.event.PauseEvent;
import com.cks.tetris.model.state.GameState;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.event.KeyEvent;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControlKeyListenerTest {

    @Mock
    GameController controller;

    @Mock
    GameEventPublisher publisher;

    @InjectMocks
    ControlKeyListener listener;

    @Nested
    class KeyPressedTest {
        @Mock
        KeyEvent keyEvent;

        @Test
        void whenEscape() {
            when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);

            listener.keyPressed(keyEvent);

            verify(publisher).publishGameEvent(any(PauseEvent.class));
        }

        @Test
        void whenSpace() {
            when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_SPACE);

            listener.keyPressed(keyEvent);

            verify(publisher).publishGameEvent(argThat(event -> {
                assertThat(event).isInstanceOf(BoardEvent.class);
                BoardEvent boardEvent = (BoardEvent) event;
                assertThat(boardEvent.getSource()).isEqualTo(listener);
                assertThat(boardEvent.getOperator()).isNotNull();
                UnaryOperator<GameState> operator = boardEvent.getOperator();
                operator.apply(mock(GameState.class));
                verify(controller, atLeastOnce()).hardDropActiveBlock(any(GameState.class));
                return true;
            }));
        }
    }
}
