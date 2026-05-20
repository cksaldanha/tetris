package com.cks.tetris.event;

import com.cks.tetris.model.state.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class GameEventListener implements ApplicationListener<GameEvent> {

    private final AtomicReference<GameState> gameState;

    @Autowired
    public GameEventListener(AtomicReference<GameState> gameState) {
        this.gameState = gameState;
    }

    @Override
    public void onApplicationEvent(GameEvent event) {
        gameState.getAndUpdate(state -> event.getOperator().apply(state));
    }
}
