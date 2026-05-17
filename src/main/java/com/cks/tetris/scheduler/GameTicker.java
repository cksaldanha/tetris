package com.cks.tetris.scheduler;

import com.cks.tetris.controller.GameController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GameTicker {

    private final GameController gameController;

    @Autowired
    public GameTicker(GameController gameController) {
        this.gameController = gameController;
    }

    @Scheduled(fixedDelay = 500L, initialDelay = 500L)
    public void tick() {
        gameController.lowerActiveBlock();
        gameController.clearFullRows();
    }

}
