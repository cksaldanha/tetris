package com.cks.tetris.ui;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class CommandDialog extends JPanel {

    private JDialog dialog;
    private final Font font;

    public CommandDialog() {
        super(new GridLayout(10, 2, 5, 5));

        font = new Font("Courier New", Font.PLAIN, 12);

        add(createLabel("↓"));
        add(createLabel("Soft drop"));

        add(createLabel("←"));
        add(createLabel("Move left"));

        add(createLabel("→"));
        add(createLabel("Move right"));

        add(createLabel("↑, 'X'"));
        add(createLabel("Rotate clockwise"));

        add(createLabel("'Z'"));
        add(createLabel("Rotate counter-clockwise"));

        add(createLabel("'C', 'Shift'"));
        add(createLabel("Hold block"));

        add(createLabel("'Escape'"));
        add(createLabel("Pause / Resume"));

        add(createLabel("'Space Bar'"));
        add(createLabel("Hard drop"));

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
