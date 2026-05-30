package com.cks.tetris.event;

import com.cks.tetris.model.state.GameState;

public class PauseEvent extends GameEvent {

    public PauseEvent(Object source) {
        super(source, state -> new GameState(state.board(), state.score(), !state.paused(), false));
    }
}
