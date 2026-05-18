package com.cks.tetris.event;

import com.cks.tetris.controller.GameController;
import com.cks.tetris.model.state.GameState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Slf4j
@Component
public class GameEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public GameEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishGameEvent(BiFunction<GameState, GameController, GameState> function) {
        applicationEventPublisher.publishEvent(new GameEvent(this, function));
    }
}
