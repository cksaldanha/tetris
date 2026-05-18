package com.cks.tetris.controller;

import com.cks.tetris.factory.BlockFactory;
import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.Board;
import com.cks.tetris.model.Direction;
import com.cks.tetris.model.Point;
import com.cks.tetris.model.block.Block;
import com.cks.tetris.model.state.GameState;
import com.cks.tetris.service.BlockService;
import com.cks.tetris.service.BoardService;
import com.cks.tetris.ui.BoardPanel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.util.Set;

@Slf4j
@Controller
public class GameController {

    private final BoardPanel boardPanel;
    private final BoardService boardService;
    private final BlockService blockService;
    private final BlockFactory blockFactory;

    @Autowired
    public GameController(BoardPanel boardPanel, BoardService boardService, BlockService blockService, BlockFactory blockFactory) {
        this.boardPanel = boardPanel;
        this.boardService = boardService;
        this.blockService = blockService;
        this.blockFactory = blockFactory;
    }

    public GameState rotateActiveBlock(GameState state, RotationMatrix rotationMatrix) {
        log.debug("Rotating active block with rotation matrix: {}", rotationMatrix);

        Board board = state.getBoard();
        Block block = board.getActiveBlock();
        Point position = board.getActiveBlockPosition();

        Block rotatedBlock = blockService.rotateBlock(block, rotationMatrix);

        if (boardService.canPlaceBlock(board, rotatedBlock, position)) {
            board = boardService.setActiveBlock(board, rotatedBlock, position);
            state = new GameState(board);
            updateBoard(board);
        }

        return state;
    }

    public GameState moveActiveBlock(GameState state, Direction direction) {
        log.debug("Moving active block in direction: {}", direction);

        Board board = state.getBoard();
        Block block = board.getActiveBlock();
        Point position = board.getActiveBlockPosition().move(direction,1);

        if (boardService.canPlaceBlock(board, block, position)) {
            board = boardService.setActiveBlock(board, block, position);
            state = new GameState(board);
            updateBoard(board);
        }

        return state;
    }

    public GameState lowerActiveBlock(GameState state) {
        Board board = state.getBoard();
        Block block = board.getActiveBlock();
        Point position = board.getActiveBlockPosition().moveDown(1);

        log.debug("Lowering active block {}", block.getClass().getSimpleName());

        if (boardService.canPlaceBlock(board, block, position)) {
            board = boardService.setActiveBlock(board, block, position);
            state = new GameState(board);
        } else {
            board = boardService.lockActiveBlock(board);
            board = boardService.setActiveBlock(board, blockFactory.getBlock(), Point.of(board.getColumnCount() / 2, 0));
            state = new GameState(board);
        }

        updateBoard(board);
        return state;
    }

    public GameState clearFullRows(GameState state) {
        Board board = state.getBoard();
        Set<Integer> fullRows = boardService.getFullRows(board);

        if (!fullRows.isEmpty()) {
            board = boardService.removeRows(board, fullRows);
            state = new GameState(board);
            updateBoard(board);
        }

        return state;
    }

    private void updateBoard(Board board) {
        log.debug("Updating board panel with new game state");
        EventQueue.invokeLater(() -> boardPanel.setBoard(board));
    }
}
