package com.cks.tetris.service;

import com.cks.tetris.config.ScoreProperties;
import com.cks.tetris.model.state.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    private final ScoreProperties scoreProperties;

    @Autowired
    public ScoreService(ScoreProperties scoreProperties) {
        this.scoreProperties = scoreProperties;
    }

    public Score increase(Score score, int amount) {
        return increaseInternal(score, amount);
    }

    public Score increaseWithLevel(Score score, int amount) {
        return increaseInternal(score, amount * score.level());
    }

    private Score increaseInternal(Score score, int amount) {
        return score.addTotal(amount);
    }

    public Score increaseByLines(Score score, int lineCount) {
        score = switch (lineCount) {
            case 1 -> score.addTotal(scoreProperties.getMultiplier().getSingleLine() * score.level());
            case 2 -> score.addTotal(scoreProperties.getMultiplier().getDoubleLine() * score.level());
            case 3 -> score.addTotal(scoreProperties.getMultiplier().getTripleLine() * score.level());
            case 4 -> score.addTotal(scoreProperties.getMultiplier().getTetrisLine() * score.level());
            default -> throw new IllegalArgumentException("Unsupported line increase");
        };
        return score.addLines(lineCount);
   }
}
