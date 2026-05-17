package com.cks.tetris.service;

import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.block.Block;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlockService {

    public Block rotateBlock(Block block, RotationMatrix rotationMatrix) {
        return block.rotate(rotationMatrix);
    }

}
