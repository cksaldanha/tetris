package com.cks.tetris.controller;

import com.cks.tetris.factory.BlockFactory;
import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.Board;
import com.cks.tetris.model.Direction;
import com.cks.tetris.model.Point;
import com.cks.tetris.model.state.Score;
import com.cks.tetris.model.block.Block;
import com.cks.tetris.model.state.GameState;
import com.cks.tetris.service.BlockService;
import com.cks.tetris.service.BoardService;
import com.cks.tetris.service.ScoreService;
import com.cks.tetris.ui.BoardPanel;
import com.cks.tetris.ui.ScorePanel;
import com.cks.tetris.ui.TextPanel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.util.Set;

@Slf4j
@Controller
public class GameController {

    private final BoardPanel boardPanel;
    private final TextPanel textPanel;
    private final ScorePanel scorePanel;
    private final BoardService boardService;
    private final ScoreService scoreService;
    private final BlockService blockService;
    private final BlockFactory blockFactory;

    @Autowired
    public GameController(BoardPanel boardPanel, TextPanel textPanel, ScorePanel scorePanel, BoardService boardService, ScoreService scoreService, BlockService blockService, BlockFactory blockFactory) {
        this.boardPanel = boardPanel;
        this.textPanel = textPanel;
        this.scorePanel = scorePanel;
        this.boardService = boardService;
        this.scoreService = scoreService;
        this.blockService = blockService;
        this.blockFactory = blockFactory;
    }

    public GameState rotateActiveBlock(GameState state, RotationMatrix rotationMatrix) {
        log.debug("Rotating active block with rotation matrix: {}", rotationMatrix);

        Board board = state.board();
        Block block = board.getActiveBlock();
        Point position = board.getActiveBlockPosition();

        Block rotatedBlock = blockService.rotateBlock(block, rotationMatrix);

        if (boardService.canPlaceBlock(board, rotatedBlock, position)) {
            board = boardService.setActiveBlock(board, rotatedBlock, position);
            state = state.mutate().board(board).build();
            updateBoard(board);
        }

        return state;
    }

    public GameState moveActiveBlock(GameState state, Direction direction) {
        log.debug("Moving active block in direction: {}", direction);

        Board board = state.board();
        Block block = board.getActiveBlock();
        Point position = board.getActiveBlockPosition().move(direction, 1);

        if (boardService.canPlaceBlock(board, block, position)) {
            board = boardService.setActiveBlock(board, block, position);
            state = state.mutate().board(board).build();
            updateBoard(board);
        }

        return state;
    }

    public GameState lowerActiveBlock(GameState state) {
        return dropActiveBlock(state, 1, 0);
    }

    public GameState softDropActiveBlock(GameState state) {
        return dropActiveBlock(state, 1, 1);
    }

    public GameState hardDropActiveBlock(GameState state) {
        int distance = boardService.getMaximumDistanceToBottom(state.board());

        return dropActiveBlock(state, distance, 2);
    }

    private GameState dropActiveBlock(GameState state, int distance, int pointMultiplier) {
        Board board = state.board();
        Block block = board.getActiveBlock();
        Point position = board.getActiveBlockPosition();
        Score score = state.score();

        if (boardService.canPlaceBlock(board, block, position.moveDown(distance))) {
            board = boardService.setActiveBlock(board, block, position.moveDown(distance));
            score = scoreService.increase(score, distance * pointMultiplier);
            state = state.mutate().board(board).score(score).build();
        } else {
            board = boardService.lockActiveBlock(board);
            state = state.mutate().board(board).build();
            state = addNewBlock(state);
            board = state.board();
        }

        updateBoard(board);
        updateScore(score);
        return state;
    }

    private GameState addNewBlock(GameState state) {
        Board board = state.board();
        Block nextBlock = blockFactory.getBlock();
        Point nextBlockPosition = Point.of(board.getColumnCount() / 2, 0);
        boolean blockOut = !boardService.canPlaceBlock(board, nextBlock, nextBlockPosition);
        board = boardService.setActiveBlock(board, nextBlock, nextBlockPosition);
        return state.mutate().board(board).gameOver(blockOut).build();
    }

    public GameState clearFullRows(GameState state) {
        Board board = state.board();
        Score score = state.score();
        Set<Integer> fullRows = boardService.getFullRows(board);

        if (!fullRows.isEmpty()) {
            board = boardService.removeRows(board, fullRows);
            score = scoreService.increaseByLines(score, fullRows.size());
            state = state.mutate().board(board).score(score).build();
            updateBoard(board);
            updateScore(score);
        }

        return state;
    }

    public void updateText(GameState state) {
        if (state.gameOver()) {
            EventQueue.invokeLater(() -> textPanel.setText("Game Over"));
        } else if (state.paused()) {
            EventQueue.invokeLater(() -> textPanel.setText("Paused"));
        } else {
            EventQueue.invokeLater(() -> textPanel.setText(""));
        }
    }

    private void updateBoard(Board board) {
        log.debug("Updating board panel with new game state");
        EventQueue.invokeLater(() -> boardPanel.setBoard(board));
    }

    private void updateScore(Score score) {
        EventQueue.invokeLater(() -> scorePanel.setScore(score));
    }
}
