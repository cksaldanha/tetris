package com.cks.tetris.model.state;

import com.cks.tetris.model.Board;

public record GameState(Board board, long score, boolean paused) {

}
