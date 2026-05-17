package com.cks.tetris.config;

import com.cks.tetris.model.block.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
}
