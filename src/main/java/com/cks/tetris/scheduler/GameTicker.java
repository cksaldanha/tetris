package com.cks.tetris.scheduler;

import com.cks.tetris.event.GameEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GameTicker {

    private final GameEventPublisher gameEventPublisher;

    @Autowired
    public GameTicker(GameEventPublisher gameEventPublisher) {
        this.gameEventPublisher = gameEventPublisher;
    }

    @Scheduled(fixedDelay = 500L, initialDelay = 500L)
    public void tick() {
        gameEventPublisher.publishGameEvent((state, controller) -> controller.lowerActiveBlock(state));
        gameEventPublisher.publishGameEvent((state, controller) -> controller.clearFullRows(state));
    }

}
