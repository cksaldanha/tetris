/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cks.tetris;

import com.cks.tetris.board.Board;
import com.cks.tetris.board.Dropper;
import com.cks.tetris.board.Info;
import com.cks.tetris.board.Scorer;
import com.cks.tetris.helper.GBC;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

/**
 *
 * @author colin.saldanha
 */
public class App {

    public static final Logger logger = Logger.getLogger("main");
    private static JFrame frame;

    public static void main(String[] args) {

        //Logging info
        logger.setLevel(Level.ALL);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.OFF);
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception x) {
        }

        EventQueue.invokeLater(() -> {
            frame = new JFrame("Tetris");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setJMenuBar(createMenuBar());

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
            frame.setVisible(true);
        });
    }

    public static JMenuBar createMenuBar() {
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
        final CommandDialog cmdDialog = new CommandDialog();
        commands.addActionListener(e -> cmdDialog.showDialog(frame, true));
        help.add(commands);

        help.addSeparator();

        JMenuItem about = new JMenuItem("About");
        about.setMnemonic(KeyEvent.VK_A);
        final AboutDialog aboutDialog = new AboutDialog();
        about.addActionListener(e -> aboutDialog.showDialog(frame, true));
        help.add(about);

        return bar;
    }

    private static class AboutDialog extends JPanel {

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

    private static class CommandDialog extends JPanel {

        private JDialog dialog;
        private Font font;

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
}
