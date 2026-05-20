package com.cks.tetris.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameEventPublisherTest {

    @Mock
    ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    GameEventPublisher publisher;

    @Nested
    class PublishGameEventTest {
        @Test
        @DisplayName("should delegate the application event publisher")
        void whenCalled() {
            GameEvent event = mock(GameEvent.class);

            publisher.publishGameEvent(event);

            verify(applicationEventPublisher).publishEvent(event);
        }
    }
}
