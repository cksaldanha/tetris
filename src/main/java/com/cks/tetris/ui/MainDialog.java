package com.cks.tetris.ui;

import com.cks.tetris.ui.listener.DirectionKeyListener;
import com.cks.tetris.ui.listener.RotationKeyListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

@Component
public class MainDialog extends JFrame {

    private final AboutDialog aboutDialog;
    private final CommandDialog commandDialog;

    @Autowired
    public MainDialog(AboutDialog aboutDialog,
                      CommandDialog commandDialog,
                      BoardPanel boardPanel,
                      DirectionKeyListener directionKeyListener,
                      RotationKeyListener rotationKeyListener) {

        this.aboutDialog = aboutDialog;
        this.commandDialog = commandDialog;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setJMenuBar(mainMenuBar());

        addKeyListener(directionKeyListener);
        addKeyListener(rotationKeyListener);

//        Info info = new Info();
//        svc.scheduleWithFixedDelay(new Scorer(board), 0, 250, TimeUnit.MILLISECONDS);

        add(boardPanel, BorderLayout.CENTER);

        pack();
        setResizable(false);
    }

    public JMenuBar mainMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File   ");
        file.setMnemonic(KeyEvent.VK_F);
        bar.add(file);

        JMenuItem save = new JMenuItem("Save");
        save.setMnemonic(KeyEvent.VK_S);
        save.setEnabled(false);
        file.add(save);

        file.addSeparator();

        JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_X);
        exit.addActionListener(e -> System.exit(0));
        file.add(exit);

        JMenu help = new JMenu("Help   ");
        help.setMnemonic(KeyEvent.VK_H);
        bar.add(help);

        JMenuItem commands = new JMenuItem("Commands");
        commands.setMnemonic(KeyEvent.VK_C);
        commands.addActionListener(e -> commandDialog.showDialog(this, true));
        help.add(commands);

        help.addSeparator();

        JMenuItem about = new JMenuItem("About");
        about.setMnemonic(KeyEvent.VK_A);
        about.addActionListener(e -> aboutDialog.showDialog(this, true));
        help.add(about);

        return bar;
    }
}
