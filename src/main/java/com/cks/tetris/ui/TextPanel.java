package com.cks.tetris.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class TextPanel extends JPanel {

    private final JLabel label;

    @Autowired
    public TextPanel(BoardPanel boardPanel) {
        super(new GridBagLayout());
        Font font = new Font("Courier New", Font.BOLD, 26);
        this.label = new JLabel();
        this.label.setFont(font);
        this.label.setForeground(Color.WHITE);
        add(label);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    public void setText(String text) {
        this.label.setText(text);
        revalidate();
    }
}
