package com.cks.tetris.ui;

import com.cks.tetris.util.GBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

import static java.awt.GridBagConstraints.WEST;

@Component
public class InformationPanel extends JPanel {

    @Autowired
    public InformationPanel(ScorePanel scorePanel) {
        super(new GridBagLayout());
        add(scorePanel, new GBC(0, 0).setAnchor(WEST));
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }

}
