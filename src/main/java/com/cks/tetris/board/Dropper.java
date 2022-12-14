/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cks.tetris.board;

import com.cks.tetris.block.Block;
import com.cks.tetris.block.BlockFactory;

/**
 *
 * @author colin.saldanha
 */
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
                System.out.println("Interrupted");
                Thread.currentThread().interrupt();;
            }
        }
    }
}
