package com.cks.tetris.config;

import com.cks.tetris.board.Board;
import com.cks.tetris.board.Dropper;
import com.cks.tetris.board.Info;
import com.cks.tetris.board.Scorer;
import com.cks.tetris.ui.AboutDialog;
import com.cks.tetris.ui.CommandDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {

    @Autowired
    public AppConfig() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }

    @Bean
    public JFrame mainWindow(AboutDialog aboutDialog, CommandDialog commandDialog) {
        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(mainMenuBar(frame, aboutDialog, commandDialog));

        Info info = new Info();
        Board board = Board.getInstance();
        board.setInfo(info);
        ScheduledExecutorService svc = Executors.newSingleThreadScheduledExecutor();
        svc.scheduleWithFixedDelay(new Dropper(500), 0, 500, TimeUnit.MILLISECONDS);
        svc.scheduleWithFixedDelay(new Scorer(board), 0, 250, TimeUnit.MILLISECONDS);

        frame.add(info, BorderLayout.NORTH);
        frame.add(board, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        return frame;
    }

    public JMenuBar mainMenuBar(JFrame mainWindow, AboutDialog aboutDialog, CommandDialog commandDialog) {
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
        commands.addActionListener(e -> commandDialog.showDialog(mainWindow, true));
        help.add(commands);

        help.addSeparator();

        JMenuItem about = new JMenuItem("About");
        about.setMnemonic(KeyEvent.VK_A);
        about.addActionListener(e -> aboutDialog.showDialog(mainWindow, true));
        help.add(about);

        return bar;
    }
}
