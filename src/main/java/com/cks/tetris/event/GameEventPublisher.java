package com.cks.tetris.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GameEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public GameEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishGameEvent(GameEvent gameEvent) {
        applicationEventPublisher.publishEvent(gameEvent);
    }
}
