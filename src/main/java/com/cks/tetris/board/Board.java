package com.cks.tetris.board;

import com.cks.tetris.block.Block;
import com.cks.tetris.block.BlockTile;
import com.cks.tetris.offset.Offset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import java.util.logging.*;
import java.util.concurrent.locks.*;

/**
 *
 * @author colin.saldanha
 */
public final class Board extends JPanel {

    public static final int MAX_COLS = 10;
    public static final int MAX_ROWS = 20;

    private final Logger logger = Logger.getLogger("main");

    private Block active;
    private static Board board;
    private Info info;

    private volatile boolean paused;

    public static final Object mainLock = new Object();
    public static final Lock reentLock = new ReentrantLock();

    private final int[][] tiles = new int[MAX_COLS][MAX_ROWS];

    private Board() {
        super(new TileLayout());

        //KEY BINDS
        InputMap imap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        imap.put(KeyStroke.getKeyStroke("LEFT"), "left");
        imap.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        imap.put(KeyStroke.getKeyStroke("DOWN"), "down");
        imap.put(KeyStroke.getKeyStroke("R"), "rotateCW");
        imap.put(KeyStroke.getKeyStroke("Q"), "rotateCCW");
        imap.put(KeyStroke.getKeyStroke("SPACE"), "pause");

        ActionMap amap = this.getActionMap();
        amap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (active != null) {
                    active.moveLeft();
                    board.revalidate();
                }
            }
        });

        amap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (active != null) {
                    active.moveRight();
                    revalidate();
                }
            }
        });

        amap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (active != null && active.isDropping()) {
                    active.moveDown();
                    revalidate();
                }
            }
        });

        amap.put("rotateCW", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (active != null && active.isDropping() && !active.isRotating()) {
                    System.out.println("Rotate button pressed");
                    active.setRotating(true);
                    active.rotateRight();
                    revalidate();
                    active.setRotating(false);
                }
            }
        });

        amap.put("rotateCCW", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (active != null && active.isDropping() && !active.isRotating()) {
                    active.setRotating(true);
                    active.rotateLeft();
                    revalidate();
                    active.setRotating(false);
                }
            }
        });

        amap.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPaused(!paused);
            }
        });
    }

    public static final Board getInstance() {
        if (board == null) {
            synchronized (mainLock) {
                if (board == null) {
                    board = new Board();
                }
            }
        }
        return board;
    }

    public void setInfo(Info i) {
        info = i;   //connects to Info panel to sync pausing.
    }

    public void addScore(long amt) {
        info.addScore(amt);
    }

    public void setPaused(boolean b) {
        paused = b;
        if (paused) {
            info.pause();
        } else {
            info.resume();
        }
        repaint();
    }

    public boolean isPaused() {
        return paused;
    }

    public void removeTileset(Point loc, List<Offset> off) {
        for (Offset o : off) {
            int x = loc.x + o.getOffsetX();
            int y = loc.y + o.getOffsetY();
            setTileAt(x, y, 0);
        }
    }

    public void addTileset(Point loc, List<Offset> off) {
        for (Offset o : off) {
            int x = loc.x + o.getOffsetX();
            int y = loc.y + o.getOffsetY();
            setTileAt(x, y, 1);
        }
    }

    public int getTileAt(Point loc) {
        return getTileAt(loc.x, loc.y);
    }

    public int getTileAt(int x, int y) {
        return tiles[x][y];
    }

    public void setTileAt(int x, int y, int val) {
        tiles[x][y] = val;
    }

    public void setActiveBlock(Block b) {
        logger.entering("Board", "setActiveBlock");
        active = b;
        logger.exiting("Board", "setActiveBlock");
    }

    public boolean isActive() {
        return active != null;
    }

    public Block getActiveBlock() {
        return active;
    }

    @Override
    public Component add(Component c) {
        if (!(c instanceof BlockTile)) {
            throw new IllegalArgumentException("Board can only accept BlockTile objects");
        }

        return super.add(c);
    }

    public int getMaxRow() {
        return MAX_ROWS - 1;
    }

    public int getMaxCol() {
        return MAX_COLS - 1;
    }

    public void addBlock(Block b) {
        logger.entering("Board", "addBlock");
        System.out.println(Thread.currentThread().getName() + " in 'addBlock()'");
        setActiveBlock(b);
        b.setDropping(true);

        //Adds the block tile to board panel
        for (int i = 0; i < 4; i++) {
            BlockTile t = b.getTileAt(i);
            int x = t.getPosX();
            int y = t.getPosY();
            if (getTileAt(x, y) == 0) {
                setTileAt(x, y, 1);
            } else {
                setPaused(true);
                JOptionPane.showMessageDialog(board, "Game over!");
                System.exit(0);
            }

            add(t); //add to panel
        }
        repaint();
        logger.exiting("Board", "addBlock");
    }

}
