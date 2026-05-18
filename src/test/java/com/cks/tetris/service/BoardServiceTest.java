package com.cks.tetris.service;

import com.cks.tetris.model.*;
import com.cks.tetris.model.block.Block;
import com.cks.tetris.model.block.IBlock;
import com.cks.tetris.model.block.TBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.cks.tetris.model.Color.GREEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @InjectMocks
    BoardService service;

    Board sampleBoard;
    Block sampleBlock;
    Point samplePosition;

    @BeforeEach
    void setUp() {
        sampleBlock = new IBlock();

        samplePosition = Point.of(0, 5);

        sampleBoard = new Board(new Tile[20][10], sampleBlock, samplePosition);
    }

    @Nested
    class SetActiveBlockTest {
        @Test
        @DisplayName("should return a new board with the same block set to the given position")
        void whenDifferentPosition() {
            Board newboard = service.setActiveBlock(sampleBoard, sampleBlock, samplePosition.moveDown(1));

            assertThat(newboard)
                    .isNotEqualTo(sampleBoard)
                    .extracting(Board::getActiveBlockPosition)
                    .isEqualTo(samplePosition.moveDown(1));
        }

        @Test
        @DisplayName("should return a new board with the given block set to the same position")
        void whenDifferentBlock() {
            Block newBlock = new TBlock();

            Board newboard = service.setActiveBlock(sampleBoard, newBlock, samplePosition);

            assertThat(newboard)
                    .isNotEqualTo(sampleBoard)
                    .extracting("tiles", "activeBlock", "activeBlockPosition")
                    .contains(sampleBoard.getTiles(), newBlock, samplePosition);
        }

        @Test
        @DisplayName("should return the same board instance when the same block is set to the same position")
        void whenSamePosition() {
            Board newboard = service.setActiveBlock(sampleBoard, sampleBlock, samplePosition);

            assertThat(newboard)
                    .isEqualTo(sampleBoard)
                    .isSameAs(sampleBoard);
        }
    }

    @Nested
    class CanPlaceBlockTest {
        @Test
        @DisplayName("should return false if any part of the block is out of bounds (left, right or bottom)")
        void whenOutOfBounds() {
            assertThat(service.canPlaceBlock(sampleBoard, sampleBlock, Point.of(-1, 0))).isFalse();
            assertThat(service.canPlaceBlock(sampleBoard, sampleBlock, Point.of(10, 0))).isFalse();
            assertThat(service.canPlaceBlock(sampleBoard, sampleBlock, Point.of(3, 20))).isFalse();
        }

        @Test
        @DisplayName("should return false if any part of the block overlaps with an existing tile")
        void whenOverlapping() {
            Tile[][] tiles = sampleBoard.getTiles();
            tiles[5][0] = Tile.ofColor(GREEN);
            Board boardWithTile = new Board(tiles, sampleBlock, samplePosition);

            assertThat(service.canPlaceBlock(boardWithTile, sampleBlock, samplePosition)).isFalse();
        }

        @Test
        @DisplayName("should return true if the block can be placed at the given position")
        void whenValidPlacement() {
            assertThat(service.canPlaceBlock(sampleBoard, sampleBlock, samplePosition)).isTrue();
        }
    }

    @Nested
    class GetFullRowsTest {
        @Test
        void whenCalled() {
            Board board = mock(Board.class);

            service.getFullRows(board);

            verify(board).getFullRows();
        }
    }

    @Nested
    class RemoveRowsTest {
        Tile t = Tile.ofColor(GREEN);
        Tile[][] tiles;
        Board board;

        @BeforeEach
        void setUp() {
            tiles = new Tile[][]{
                    {t, null},
                    {t, t},
                    {null, t},
                    {t, t},
            };

            board = new Board(tiles, null, null);
        }

        @Test
        void whenSingleRow() {
            Tile[][] expected = new Tile[][]{
                    {null, null},
                    {t, null},
                    {t, t},
                    {null, t},
            };

            Board actual = service.removeRows(board, Set.of(3));

            assertThat(actual)
                    .extracting(Board::getTiles)
                    .isEqualTo(expected);
        }

        @Test
        void whenMultipleRows() {
            Tile[][] expected = new Tile[][]{
                    {null, null},
                    {null, null},
                    {t, null},
                    {null, t},
            };

            Board actual = service.removeRows(board, Set.of(1, 3));

            assertThat(actual)
                    .extracting(Board::getTiles)
                    .isEqualTo(expected);
        }
    }

}
