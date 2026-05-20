package com.cks.tetris.event;

import com.cks.tetris.model.state.GameState;

import java.util.function.UnaryOperator;

public class BoardEvent extends GameEvent {

    public BoardEvent(Object source, UnaryOperator<GameState> operator) {
        super(source, state -> state.paused() ? state : operator.apply(state));
    }
}
