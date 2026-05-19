package com.cks.tetris.config;

import com.cks.tetris.factory.BlockFactory;
import com.cks.tetris.model.Board;
import com.cks.tetris.model.Point;
import com.cks.tetris.model.block.*;
import com.cks.tetris.model.state.GameState;
import com.cks.tetris.math.Matrix;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
@EnableConfigurationProperties(GameProperties.class)
public class GameConfig {

    @Bean
    public List<Block> blocks() {
        return List.of(
                new IBlock(),
                new JBlock(),
                new LBlock(),
                new OBlock(),
                new SBlock(),
                new TBlock(),
                new ZBlock()
        );
    }

    @Bean
    public AtomicReference<GameState> gameState(GameProperties gameProperties, BlockFactory blockFactory) {
        int rows = gameProperties.getRowCount();
        int cols = gameProperties.getColumnCount();
        Block startingBlock = blockFactory.getBlock();
        Board startingBoard = new Board(new Matrix<>(rows, cols), startingBlock, Point.of(cols / 2, 0));
        GameState initialState = new GameState(startingBoard, 0, false);
        return new AtomicReference<>(initialState);
    }
}
