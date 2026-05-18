package com.cks.tetris.event;

import com.cks.tetris.controller.GameController;
import com.cks.tetris.model.state.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class GameEventListener implements ApplicationListener<GameEvent> {

    private final GameController gameController;
    private final AtomicReference<GameState> gameState;

    @Autowired
    public GameEventListener(GameController gameController, AtomicReference<GameState> gameState) {
        this.gameController = gameController;
        this.gameState = gameState;
    }

    @Override
    public void onApplicationEvent(GameEvent event) {
        gameState.set(event.getUpdateFunction().apply(gameState.get(), gameController));
    }
}
