package com.cks.tetris.event;

import com.cks.tetris.controller.GameController;
import com.cks.tetris.model.state.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class GameEventListener implements ApplicationListener<GameEvent> {

    private final AtomicReference<GameState> gameState;
    private final GameController gameController;

    @Autowired
    public GameEventListener(AtomicReference<GameState> gameState, GameController gameController) {
        this.gameState = gameState;
        this.gameController = gameController;
    }

    @Override
    public void onApplicationEvent(GameEvent event) {
        gameState.getAndUpdate(state -> {
            GameState updated = event.getOperator().apply(state);
            gameController.updateText(updated);
            return updated;
        });
    }
}
