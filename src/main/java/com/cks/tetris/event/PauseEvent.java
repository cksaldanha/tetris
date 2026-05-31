package com.cks.tetris.event;

public class PauseEvent extends GameEvent {

    public PauseEvent(Object source) {
        super(source, state -> state.gameOver() ? state : state.mutate().paused(!state.paused()).build());
    }
}
