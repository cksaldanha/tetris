package com.cks.tetris.scheduler;

import com.cks.tetris.controller.GameController;
import com.cks.tetris.event.GameEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GameTicker {

    private final GameController gameController;
    private final GameEventPublisher gameEventPublisher;

    @Autowired
    public GameTicker(GameController gameController, GameEventPublisher gameEventPublisher) {
        this.gameController = gameController;
        this.gameEventPublisher = gameEventPublisher;
    }

    @Scheduled(fixedDelay = 500L, initialDelay = 500L)
    public void tick() {
        gameEventPublisher.publishGameEvent(gameController::lowerActiveBlock);
        gameEventPublisher.publishGameEvent(gameController::clearFullRows);
    }

}
