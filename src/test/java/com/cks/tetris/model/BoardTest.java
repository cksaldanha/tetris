package com.cks.tetris.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.cks.tetris.model.Color.*;
import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Nested
    class GetFullRowsTest {
        Board board;
        Tile[][] tiles;

        @Test
        void whenHasFullRows() {
            Tile t = new Tile(GREEN);
            tiles = new Tile[][]{
                    {null,  null,   null},
                    {t,     t,      t},
                    {t,     t,      t},
            };
            board = new Board(tiles, null, null);

            Set<Integer> actual = board.getFullRows();

            assertThat(actual)
                    .contains(1, 2)
                    .doesNotContain(0);
        }

        @Test
        void whenOnlyPartialRows() {
            Tile t = new Tile(GREEN);
            tiles = new Tile[][]{
                    {t,     null,   null},
                    {null,  t,      null},
                    {null,  null,   t},
            };

            board = new Board(tiles, null, null);

            Set<Integer> actual = board.getFullRows();

            assertThat(actual).isEmpty();
        }
    }
}
