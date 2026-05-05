package com.cks.tetris.ui;

import com.cks.tetris.helper.GBC;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class AboutDialog extends JPanel {

    private JDialog dialog;

    public AboutDialog() {
        super(new GridBagLayout());
        int x = 0, y = 0;

        add(createTopPanel(), new GBC(x++, y).setInsets(5));

        x = 0;
        y++;
        add(createBotPanel(), new GBC(x++, y).setInsets(5));
    }

    private JPanel createTopPanel() {
        JPanel p = new JPanel(new BorderLayout());

        JLabel l1 = new JLabel("Tetris");
        l1.setFont(new Font("Courier New", Font.BOLD, 24));
        JLabel l2 = new JLabel("A simple app to arrange falling blocks.");
        l2.setFont(new Font("Courier New", Font.PLAIN, 12));

        p.add(l1, BorderLayout.CENTER);
        p.add(l2, BorderLayout.SOUTH);
        return p;
    }

    private JPanel createBotPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        String msg = "<html>Created by: Colin Saldanha<br>For any concerns, please contact at colin.saldanha@gmail.com</html>";
        p.add(new JLabel(msg));
        return p;
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
            dialog.setTitle("About");
            dialog.pack();
            dialog.setResizable(false);
        }
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }
}
