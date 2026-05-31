package com.cks.tetris.model.state;

import com.cks.tetris.model.Board;

public record GameState(Board board, Score score, boolean paused, boolean gameOver) {

    public GameState(Builder builder) {
        this(builder.board, builder.score, builder.paused, builder.gameOver);
    }

    public Builder mutate() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Board board;
        private Score score;
        private boolean paused;
        private boolean gameOver;

        protected Builder() {
        }

        protected Builder(GameState original) {
            this.board = original.board;
            this.score = original.score;
            this.paused = original.paused;
        }

        public Builder board(Board board) {
            this.board = board;
            return self();
        }

        public Builder score(Score score) {
            this.score = score;
            return self();
        }

        public Builder paused(boolean paused) {
            this.paused = paused;
            return self();
        }

        public Builder gameOver(boolean gameOver) {
            this.gameOver = gameOver;
            return self();
        }

        protected Builder self() {
            return this;
        }

        public GameState build() {
            return new GameState(this);
        }
    }
}
