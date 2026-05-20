package com.cks.tetris.event;

import com.cks.tetris.model.state.GameState;
import org.springframework.context.ApplicationEvent;

import java.util.function.UnaryOperator;

public abstract class GameEvent extends ApplicationEvent {

    private final UnaryOperator<GameState> operator;

    protected GameEvent(Object source, UnaryOperator<GameState> operator) {
        super(source);
        this.operator = operator;
    }

    public UnaryOperator<GameState> getOperator() {
        return operator;
    }
}
