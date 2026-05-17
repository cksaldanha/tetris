package com.cks.tetris.factory.impl;

import com.cks.tetris.factory.BlockFactory;
import com.cks.tetris.model.block.Block;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Primary
@Component
public class RandomBlockFactory implements BlockFactory {

    private final List<Block> availableBlocks;

    public RandomBlockFactory(List<Block> availableBlocks) {
        this.availableBlocks = availableBlocks;
    }

    @Override
    public Block getBlock() {
        int randomIndex = ThreadLocalRandom.current().nextInt(availableBlocks.size());

        return availableBlocks.get(randomIndex);
    }
}
