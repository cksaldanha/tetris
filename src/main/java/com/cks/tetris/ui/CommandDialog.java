package com.cks.tetris.ui;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class CommandDialog extends JPanel {

    private JDialog dialog;
    private final Font font;

    public CommandDialog() {
        super(new GridLayout(6, 2, 5, 5));

        font = new Font("Courier New", Font.PLAIN, 12);

        add(createLabel("'Cursor Down'"));
        add(createLabel("Move down"));
        add(createLabel("'Cursor Left'"));
        add(createLabel("Move left"));
        add(createLabel("'Cursor Right'"));
        add(createLabel("Move right"));
        add(createLabel("'R'"));
        add(createLabel("Rotate clockwise"));
        add(createLabel("'Q'"));
        add(createLabel("Rotate counter-clockwise"));
        add(createLabel("'Space Bar'"));
        add(createLabel("Pause / Resume"));

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private JLabel createLabel(String msg) {
        JLabel l = new JLabel(msg);
        l.setFont(font);
        return l;
    }

    public void showDialog(Frame parent, boolean modal) {
        Frame owner = null;
        if (parent instanceof Frame) {
            owner = parent;
        } else {
            owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);
        }

        if (dialog == null || !dialog.getOwner().equals(owner)) {
            dialog = new JDialog(owner, modal);
            dialog.add(this);
            dialog.setTitle("Commands");
            dialog.pack();
            dialog.setResizable(false);
        }
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }
}
