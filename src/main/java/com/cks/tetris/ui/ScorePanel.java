package com.cks.tetris.ui;

import com.cks.tetris.model.state.Score;
import com.cks.tetris.util.GBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;


@Component
public class ScorePanel extends JPanel {

    private final JLabel scoreLabel;

    @Autowired
    public ScorePanel(Font informationFont) {
        super(new GridBagLayout());
        scoreLabel = new JLabel(buildDisplayText(new Score(0, 0)));
        scoreLabel.setFont(informationFont);
        add(scoreLabel, new GBC(0, 0).setInsets(10));
    }

    public void setScore(Score score) {
        this.scoreLabel.setText(buildDisplayText(score));
    }

    private String buildDisplayText(Score score) {
        return String.format("Score: %d   Lines: %d   Level: %d", score.total(), score.lines(), score.level());
    }

}
