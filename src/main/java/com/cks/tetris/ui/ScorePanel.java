package com.cks.tetris.ui;

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
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(informationFont);
        add(scoreLabel, new GBC(0, 0).setInsets(10));
    }

    public void setScore(long score) {
        this.scoreLabel.setText("Score: " + score);
    }

}
