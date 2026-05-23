package com.cks.tetris.ui.listener;

import com.cks.tetris.controller.GameController;
import com.cks.tetris.event.BoardEvent;
import com.cks.tetris.event.GameEventPublisher;
import com.cks.tetris.event.PauseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Component
public class ControlKeyListener implements KeyListener {

    private final GameEventPublisher gameEventPublisher;
    private final GameController gameController;

    @Autowired
    public ControlKeyListener(GameEventPublisher gameEventPublisher, GameController gameController) {
        this.gameEventPublisher = gameEventPublisher;
        this.gameController = gameController;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                gameEventPublisher.publishGameEvent(new PauseEvent(this));
                break;

            case KeyEvent.VK_SPACE:
                gameEventPublisher.publishGameEvent(new BoardEvent(this, gameController::hardDropActiveBlock));
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
