package com.cks.tetris.boot;

import com.cks.tetris.config.GameProperties;
import com.cks.tetris.controller.GameController;
import com.cks.tetris.factory.BlockFactory;
import com.cks.tetris.model.Block;
import com.cks.tetris.model.Board;
import com.cks.tetris.model.Point;
import com.cks.tetris.model.Tile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationBootstrapRunner implements ApplicationRunner {

    private final GameProperties gameProperties;
    private final GameController gameController;
    private final BlockFactory blockFactory;

    @Autowired
    public ApplicationBootstrapRunner(GameProperties gameProperties,
                                      GameController gameController,
                                      BlockFactory blockFactory) {
        this.gameProperties = gameProperties;
        this.gameController = gameController;
        this.blockFactory = blockFactory;
    }

    @Override
    public void run(ApplicationArguments args) {
        int rows = gameProperties.getRowCount();
        int cols = gameProperties.getColumnCount();
        Block startingBlock = blockFactory.getRandomBlock();
        Board startingBoard = new Board(new Tile[rows][cols], startingBlock, Point.of(cols / 2, 0));
        gameController.updateBoard(startingBoard);
    }
}
