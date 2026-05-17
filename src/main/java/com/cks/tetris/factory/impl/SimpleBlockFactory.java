package com.cks.tetris.factory.impl;

import com.cks.tetris.factory.BlockFactory;
import com.cks.tetris.model.block.Block;
import com.cks.tetris.model.block.TBlock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleBlockFactory implements BlockFactory {

    @Override
    public Block getBlock() {
        return new TBlock();
    }
}
