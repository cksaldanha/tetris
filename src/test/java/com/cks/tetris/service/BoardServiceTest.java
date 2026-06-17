package com.cks.tetris.service;

import com.cks.tetris.math.Matrix;
import com.cks.tetris.model.Board;
import com.cks.tetris.model.Point;
import com.cks.tetris.model.Tile;
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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.cks.tetris.model.Color.GREEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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

        samplePosition = Point.of(1, 5);

        sampleBoard = new Board(new Matrix<>(20, 10), sampleBlock, samplePosition);
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
            List<List<Tile>> tiles = sampleBoard.getTiles().getAll();
            tiles.get(5).set(0, Tile.ofColor(GREEN));
            Board boardWithTile = new Board(new Matrix<>(tiles), sampleBlock, samplePosition);

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
        List<List<Tile>> tiles;
        Board board;

        @BeforeEach
        void setUp() {
            tiles = List.of(
                    Arrays.asList(t, null),
                    Arrays.asList(t, t),
                    Arrays.asList(null, t),
                    Arrays.asList(t, t)
            );

            board = new Board(new Matrix<>(tiles), null, null);
        }

        @Test
        void whenSingleRow() {
            List<List<Tile>> expected = List.of(
                    Arrays.asList(null, null),
                    Arrays.asList(t, null),
                    Arrays.asList(t, t),
                    Arrays.asList(null, t)
            );

            Board actual = service.removeRows(board, Set.of(3));

            assertThat(actual)
                    .extracting(Board::getTiles)
                    .isEqualTo(new Matrix<>(expected));
        }

        @Test
        void whenMultipleRows() {
            List<List<Tile>> expected = List.of(
                    Arrays.asList(null, null),
                    Arrays.asList(null, null),
                    Arrays.asList(t, null),
                    Arrays.asList(null, t)
            );

            Board actual = service.removeRows(board, Set.of(1, 3));

            assertThat(actual)
                    .extracting(Board::getTiles)
                    .isEqualTo(new Matrix<>(expected));
        }
    }

    @Nested
    class GetMaximumDistanceToBottomTest {
        Matrix<Tile> tiles;
        Block block;

        @BeforeEach
        void setUp() {
            Tile t = Tile.ofColor(GREEN);
            tiles = new Matrix<>(List.of(
                    Arrays.asList(null, null, null, null),
                    Arrays.asList(null, null, null, null),
                    Arrays.asList(null, null, null, null),
                    Arrays.asList(null, null, null, null),
                    Arrays.asList(null, null, null, null),
                    Arrays.asList(null, null, t, t),
                    Arrays.asList(null, t, t, t),
                    Arrays.asList(t, t, null, t)
            ));

            block = mock(Block.class);
            when(block.getOffsets()).thenReturn(Set.of(Point.ORIGIN));
        }

        @Test
        void whenBlockIsLeft() {
            Board board = new Board(tiles, block, Point.of(0, 0));

            int actual = service.getMaximumDistanceToBottom(board);

            assertThat(actual).isEqualTo(6);
        }

        @Test
        void whenBlockIsRight() {
            Board board = new Board(tiles, block, Point.of(tiles.getColumnCount() - 1, 0));

            int actual = service.getMaximumDistanceToBottom(board);

            assertThat(actual).isEqualTo(4);
        }

        @Test
        void whenBlockIsMiddle() {
            Board board = new Board(tiles, block, Point.of(1, 0));

            int actual = service.getMaximumDistanceToBottom(board);

            assertThat(actual).isEqualTo(5);
        }
    }
}
