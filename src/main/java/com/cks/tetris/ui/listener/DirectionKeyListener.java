package com.cks.tetris.ui.listener;

import com.cks.tetris.controller.GameController;
import com.cks.tetris.event.BoardEvent;
import com.cks.tetris.event.GameEventPublisher;
import com.cks.tetris.math.RotationMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.cks.tetris.model.Direction.LEFT;
import static com.cks.tetris.model.Direction.RIGHT;

@Component
public class DirectionKeyListener implements KeyListener {

    private final GameController gameController;
    private final GameEventPublisher gameEventPublisher;

    @Autowired
    public DirectionKeyListener(GameController gameController, GameEventPublisher gameEventPublisher) {
        this.gameController = gameController;
        this.gameEventPublisher = gameEventPublisher;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                gameEventPublisher.publishGameEvent(new BoardEvent(this, state -> gameController.moveActiveBlock(state, LEFT)));
                break;
            case KeyEvent.VK_RIGHT:
                gameEventPublisher.publishGameEvent(new BoardEvent(this, state -> gameController.moveActiveBlock(state, RIGHT)));
                break;
            case KeyEvent.VK_DOWN:
                gameEventPublisher.publishGameEvent(new BoardEvent(this, gameController::softDropActiveBlock));
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
