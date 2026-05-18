package com.cks.tetris.ui.listener;

import com.cks.tetris.event.GameEventPublisher;
import com.cks.tetris.model.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Component
public class DirectionKeyListener implements KeyListener {

    private final GameEventPublisher gameEventPublisher;

    @Autowired
    public DirectionKeyListener(GameEventPublisher gameEventPublisher) {
        this.gameEventPublisher = gameEventPublisher;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                gameEventPublisher.publishGameEvent((state, controller) -> controller.moveActiveBlock(state, Direction.LEFT));
                break;
            case KeyEvent.VK_RIGHT:
                gameEventPublisher.publishGameEvent((state, controller) -> controller.moveActiveBlock(state, Direction.RIGHT));
                break;
            case KeyEvent.VK_DOWN:
                gameEventPublisher.publishGameEvent((state, controller) -> controller.lowerActiveBlock(state));
                break;
         }
   }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
