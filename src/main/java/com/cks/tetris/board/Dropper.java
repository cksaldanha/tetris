package com.cks.tetris.board;

import com.cks.tetris.block.Block;
import com.cks.tetris.block.BlockFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author colin.saldanha
 */
public class Dropper implements Runnable {

    private static final Logger logger = LogManager.getLogger(Dropper.class);
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
                logger.debug("Dropper was interrupted");
                Thread.currentThread().interrupt();;
            }
        }
    }
}
