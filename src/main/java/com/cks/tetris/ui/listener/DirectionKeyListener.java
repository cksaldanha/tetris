package com.cks.tetris.ui.listener;

import com.cks.tetris.controller.GameController;
import com.cks.tetris.model.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Component
public class DirectionKeyListener implements KeyListener {

    private final GameController gameController;

    @Autowired
    public DirectionKeyListener(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                gameController.moveActiveBlock(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                gameController.moveActiveBlock(Direction.RIGHT);
                break;
            case KeyEvent.VK_DOWN:
                gameController.lowerActiveBlock();
                break;
         }
   }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
