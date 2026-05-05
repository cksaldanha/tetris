package com.cks.tetris.board;

import com.cks.tetris.block.Block;
import com.cks.tetris.block.BlockFactory;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author colin.saldanha
 */
@Slf4j
public class Dropper implements Runnable {

    private int delay;

    public Dropper(int delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        Board board = Board.getInstance();
        Block block = BlockFactory.createRandomBlock();
        board.addBlock(block);

        while (block.isDropping()) {
            //check for game pause
            while (board.isPaused()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException x) {
                }
            }
            try {
                block.moveDown();
                board.revalidate();
                Thread.sleep(delay);
            } catch (InterruptedException x) {
                log.debug("Dropper was interrupted");
                Thread.currentThread().interrupt();;
            }
        }
    }
}
