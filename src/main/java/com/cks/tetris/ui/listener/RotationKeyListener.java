package com.cks.tetris.ui.listener;

import com.cks.tetris.event.GameEventPublisher;
import com.cks.tetris.math.RotationMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Component
public class RotationKeyListener implements KeyListener {

    private final GameEventPublisher gameEventPublisher;

    @Autowired
    public RotationKeyListener(GameEventPublisher gameEventPublisher) {
        this.gameEventPublisher = gameEventPublisher;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_R:
                gameEventPublisher.publishGameEvent(
                        (state, controller) -> controller.rotateActiveBlock(state, RotationMatrix.CLOCK_WISE_90)
                );
                break;
            case KeyEvent.VK_F:
                gameEventPublisher.publishGameEvent(
                        (state, controller) -> controller.rotateActiveBlock(state, RotationMatrix.COUNTER_CLOCK_WISE_90)
                );
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
