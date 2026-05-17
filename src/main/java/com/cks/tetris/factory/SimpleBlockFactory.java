package com.cks.tetris.factory;

import com.cks.tetris.model.Block;
import com.cks.tetris.model.Color;
import com.cks.tetris.model.Point;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class SimpleBlockFactory implements BlockFactory {

    @Override
    public Block getRandomBlock() {
        return new Block(Set.of(
                Point.of(+0, +0),
                Point.of(-1, +0),
                Point.of(+1, +0),
                Point.of(+0, -1)
        ), Color.BLUE);
    }
}
