package com.cks.tetris.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static com.cks.tetris.model.Color.GREEN;
import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Nested
    class GetFullRowsTest {
        Board board;
        Tile[][] tiles;

        @Test
        void whenHasFullRows() {
            Tile t = Tile.ofColor(GREEN);
            tiles = new Tile[][]{
                    {null, null, null},
                    {t, t, t},
                    {t, t, t},
            };
            board = new Board(tiles, null, null);

            Set<Integer> actual = board.getFullRows();

            assertThat(actual)
                    .contains(1, 2)
                    .doesNotContain(0);
        }

        @Test
        void whenOnlyPartialRows() {
            Tile t = Tile.ofColor(GREEN);
            tiles = new Tile[][]{
                    {t, null, null},
                    {null, t, null},
                    {null, null, t},
            };

            board = new Board(tiles, null, null);

            Set<Integer> actual = board.getFullRows();

            assertThat(actual).isEmpty();
        }
    }

    @Nested
    class GetTileAtCoordinatesTest {
        Board board;

        @BeforeEach
        void setUp() {
            Tile t = Tile.ofColor(GREEN);
            Tile[][] tiles = new Tile[][]{
                    {t, null, null},
                    {t, t, null},
                    {t, null, t},
                    {t, t, t},
            };

            board = new Board(tiles, null, null);
        }

        @ParameterizedTest
        @CsvSource({"0,0", "0,1", "1,1", "0,2", "0,3", "1,3", "2,3"})
        void whenTileExists(int x, int y) {
            assertThat(board.containsTileAtCoordinates(x, y)).isTrue();
        }

        @ParameterizedTest
        @CsvSource({"1,0", "2,0", "2,1", "1,2"})
        void whenTileDoesNotExist(int x, int y) {
            assertThat(board.containsTileAtCoordinates(x, y)).isFalse();
        }

        @ParameterizedTest
        @CsvSource({"-1,0", "0,10", "0,-1", "10, 0"})
        void whenOutOfBounds(int x, int y) {
            assertThat(board.containsTileAtCoordinates(x, y)).isFalse();
        }
    }
}
