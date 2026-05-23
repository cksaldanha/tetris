package com.cks.tetris.controller;

import com.cks.tetris.factory.BlockFactory;
import com.cks.tetris.math.Matrix;
import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.Board;
import com.cks.tetris.model.Direction;
import com.cks.tetris.model.Point;
import com.cks.tetris.model.state.Score;
import com.cks.tetris.model.block.Block;
import com.cks.tetris.model.block.TBlock;
import com.cks.tetris.model.state.GameState;
import com.cks.tetris.service.BlockService;
import com.cks.tetris.service.BoardService;
import com.cks.tetris.service.ScoreService;
import com.cks.tetris.ui.BoardPanel;
import com.cks.tetris.ui.ScorePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    BoardPanel boardPanel;

    @Mock
    ScorePanel scorePanel;

    @Mock
    BoardService boardService;

    @Mock
    ScoreService scoreService;

    @Mock
    BlockService blockService;

    @Mock
    BlockFactory blockFactory;

    @InjectMocks
    GameController controller;

    @Nested
    class RotateActiveBlockTest {

        Block block;
        Point position;
        Board board;

        @BeforeEach
        void setUp() {
            block = new TBlock();
            position = Point.ORIGIN;
            board = spy(new Board(new Matrix<>(20, 10), block, position));
        }

        @Test
        @DisplayName("should result in a new state and update the board panel")
        void whenCanRotateBlock() {
            GameState original = new GameState(board, new Score(0, 0), false);

            when(blockService.rotateBlock(block, RotationMatrix.CLOCK_WISE_90)).thenReturn(block);
            when(boardService.canPlaceBlock(board, block, position)).thenReturn(true);
            when(boardService.setActiveBlock(board, block, position)).thenReturn(board);

            GameState actual = controller.rotateActiveBlock(original, RotationMatrix.CLOCK_WISE_90);

            assertThat(actual).isNotSameAs(original);
            verify(boardService).setActiveBlock(board, block, position);
        }

        @Test
        @DisplayName("should not update the board panel and should keep the state the same")
        void whenCannotRotateBlock() {
            GameState original = new GameState(board, new Score(0, 0), false);

            when(boardService.canPlaceBlock(board, block, position)).thenReturn(false);
            when(blockService.rotateBlock(block, RotationMatrix.CLOCK_WISE_90)).thenReturn(block);

            GameState actual = controller.rotateActiveBlock(original, RotationMatrix.CLOCK_WISE_90);

            assertThat(actual).isSameAs(original);
            verify(boardService, never()).setActiveBlock(any(Board.class), any(Block.class), any(Point.class));
        }
    }

    @Nested
    @DisplayName("should result in a new state and update the board panel")
    class MoveActiveBlockTest {

        Block block;
        Point originalPosition;
        Board board;

        @BeforeEach
        void setUp() {
            block = new TBlock();
            originalPosition = Point.ORIGIN;
            board = spy(new Board(new Matrix<>(20, 10), block, originalPosition));
        }

        @Test
        void whenCanMoveBlock() {
            Direction direction = Direction.LEFT;
            Point movedPosition = originalPosition.move(direction, 1);

            when(boardService.canPlaceBlock(board, block, movedPosition)).thenReturn(true);
            when(boardService.setActiveBlock(board, block, movedPosition)).thenReturn(board);

            GameState original = new GameState(board, new Score(0, 0), false);

            GameState actual = controller.moveActiveBlock(original, direction);

            assertThat(actual).isNotSameAs(original);
            verify(boardService).setActiveBlock(board, block, movedPosition);
        }

        @Test
        @DisplayName("should not update board panel or create new game state if cannot move active block")
        void whenCannotMoveBlock() {
            when(boardService.canPlaceBlock(any(Board.class), any(Block.class), any(Point.class))).thenReturn(false);

            GameState original = new GameState(board, new Score(0, 0), false);

            GameState actual = controller.moveActiveBlock(original, Direction.LEFT);

            assertThat(actual).isSameAs(original);
            verify(boardService, never()).setActiveBlock(any(Board.class), any(Block.class), any(Point.class));
        }
    }

    @Nested
    class LowerActiveBlockTest {
        Block block;
        Point originalPosition;
        Board board;

        @BeforeEach
        void setUp() {
            block = new TBlock();
            originalPosition = Point.of(0, 0);
            board = spy(new Board(new Matrix<>(20, 10), block, originalPosition));
        }

        @Test
        @DisplayName("should lower active block if possible and update board panel")
        void whenCanLowerActiveBlock() {
            Point loweredPosition = originalPosition.moveDown(1);
            when(boardService.canPlaceBlock(board, block, loweredPosition)).thenReturn(true);
            when(boardService.setActiveBlock(board, block, loweredPosition)).thenReturn(board);

            GameState original = new GameState(board, new Score(0, 0), false);

            GameState actual = controller.lowerActiveBlock(original);

            assertThat(actual).isNotSameAs(original);
            verify(boardService).setActiveBlock(board, block, loweredPosition);
        }

        @Test
        @DisplayName("should lock active block and spawn new block if cannot lower active block")
        void whenCannotLowerActiveBlock() {
            Point loweredPosition = originalPosition.moveDown(1);
            Point centeredPosition = Point.of(board.getColumnCount() / 2, 0);

            when(boardService.canPlaceBlock(board, block, loweredPosition)).thenReturn(false);
            when(boardService.lockActiveBlock(board)).thenReturn(board);
            when(blockFactory.getBlock()).thenReturn(block);
            when(boardService.setActiveBlock(board, block, centeredPosition)).thenReturn(board);

            GameState original = new GameState(board, new Score(0, 0), false);

            GameState actual = controller.lowerActiveBlock(original);

            assertThat(actual).isNotSameAs(original);
            verify(boardService).lockActiveBlock(board);
            verify(boardService).setActiveBlock(board, block, centeredPosition);
        }
    }

    @Nested
    class ClearFullRowsTest {

        @Test
        @DisplayName("should remove rows if exist and update board panel")
        void whenFullRows() {
            Board board = mock(Board.class);
            Set<Integer> fullRows = Set.of(1, 2);
            when(boardService.getFullRows(board)).thenReturn(fullRows);
            when(boardService.removeRows(board, fullRows)).thenReturn(board);
            Score modifiedScore = new Score(0, 2);
            when(scoreService.increaseByLines(any(Score.class), anyInt())).thenReturn(modifiedScore);

            GameState original = new GameState(board, new Score(0, 0), false);

            GameState actual = controller.clearFullRows(original);

            assertThat(actual).isNotSameAs(original);
            verify(boardService).removeRows(board, fullRows);
        }

        @Test
        @DisplayName("should not update board or score panels if no full rows")
        void whenNotFullRows() {
            Board board = mock(Board.class);
            when(boardService.getFullRows(board)).thenReturn(Collections.emptySet());

            GameState original = new GameState(board, new Score(0, 0), false);

            GameState actual = controller.clearFullRows(original);

            assertThat(actual).isSameAs(original);
        }
    }
}
