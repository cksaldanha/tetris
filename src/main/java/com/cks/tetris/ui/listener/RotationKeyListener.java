package com.cks.tetris.ui.listener;

import com.cks.tetris.controller.GameController;
import com.cks.tetris.event.BoardEvent;
import com.cks.tetris.event.GameEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.cks.tetris.math.RotationMatrix.CLOCK_WISE_90;
import static com.cks.tetris.math.RotationMatrix.COUNTER_CLOCK_WISE_90;

@Component
public class RotationKeyListener implements KeyListener {

    private final GameController gameController;
    private final GameEventPublisher gameEventPublisher;

    @Autowired
    public RotationKeyListener(GameController gameController, GameEventPublisher gameEventPublisher) {
        this.gameController = gameController;
        this.gameEventPublisher = gameEventPublisher;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_R:
                gameEventPublisher.publishGameEvent(new BoardEvent(this, state -> gameController.rotateActiveBlock(state, CLOCK_WISE_90)));
                break;
            case KeyEvent.VK_F:
                gameEventPublisher.publishGameEvent(new BoardEvent(this, state -> gameController.rotateActiveBlock(state, COUNTER_CLOCK_WISE_90)));
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
