package com.cks.tetris.event;

import com.cks.tetris.controller.GameController;
import com.cks.tetris.model.state.GameState;
import org.springframework.context.ApplicationEvent;

import java.util.function.BiFunction;

public class GameEvent extends ApplicationEvent {

    private final BiFunction<GameState, GameController, GameState> updateFunction;

    public GameEvent(Object source, BiFunction<GameState, GameController, GameState> updateFunction) {
        super(source);
        this.updateFunction = updateFunction;
    }

    public BiFunction<GameState, GameController, GameState> getUpdateFunction() {
        return updateFunction;
    }
}
