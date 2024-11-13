package com.cks.tetris.block;

import com.cks.tetris.board.Board;
import com.cks.tetris.offset.BlockOffsets;
import com.cks.tetris.offset.BlockOrientation;
import com.cks.tetris.offset.Offset;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author colin.saldanha
 */
public abstract class Block implements Moveable, Rotateable {

    private static final Logger logger = LogManager.getLogger(Block.class);

    protected BlockTile[] tiles;
    protected BlockShape shape;
    protected Color color;
    protected boolean isDropping;
    protected boolean isRotating;

    protected Point loc;  //location / pivot pt
    protected BlockOrientation currOrient;

    protected Block(BlockShape shape) {
        this.shape = shape;
        loc = new Point(0, 0);
        tiles = new BlockTile[4];
        color = BlockFactory.getColor(shape);
        tiles[0] = new BlockTile(color);
        tiles[1] = new BlockTile(color);
        tiles[2] = new BlockTile(color);
        tiles[3] = new BlockTile(color);
    }

    public Point getLocation() {
        return loc;
    }

    public void setLocation(Point p) {
        setLocation(p.x, p.y);
    }

    public void setLocation(int x, int y) {
        loc.x = x;  //x position in grid
        loc.y = y;  //y position in grid
        setTiles(loc);
    }

    private void setTiles(Point loc) {
        logger.traceEntry("setTiles() [Location: {}]", loc);
        List<Offset> offs = BlockOffsets.getOffsetList(shape, currOrient);

        for (int i = 0; i < offs.size(); i++) {
            Offset o = offs.get(i);
            BlockTile t = tiles[i];
            assert t != null : "BlockTile is null??";
            t.setPosX(loc.x + o.getOffsetX());
            t.setPosY(loc.y + o.getOffsetY());
        }
        logger.traceExit("setTiles()", null);
    }

    private boolean checkLocation(Point loc, Board board) {
        logger.traceEntry("checkLocation() [Location: {}] [Board: {}]", loc, board);
        List<Offset> offs = BlockOffsets.getOffsetList(shape, currOrient);

        for (int i = 0; i < offs.size(); i++) {
            Offset o = offs.get(i);
            //check boundaries
            int x = loc.x + o.getOffsetX();
            int y = loc.y + o.getOffsetY();

            if (x < 0 || x > board.getMaxCol()) {
                return false;
            }
            if (y < 0 || y > board.getMaxRow()) {
                return false;
            }

            //check for other tiles
            if (board.getTileAt(x, y) == 1) {
                return false;
            }
        }

        return true;
    }

    public BlockTile getTileAt(int i) {
        return tiles[i];
    }

    public BlockShape getShape() {
        return shape;
    }

    public BlockOrientation getOrientation() {
        return currOrient;
    }

    public boolean hasOrientation(BlockOrientation orient) {
        return BlockOffsets.hasOrientation(shape, orient);
    }

    public int getNumOrientations() {
        return BlockOffsets.getNumOrientations(shape);
    }

    public boolean isDropping() {
        return isDropping;
    }

    public void setDropping(boolean b) {
        isDropping = b;
    }

    public boolean isRotating() {
        return isRotating;
    }

    public void setRotating(boolean b) {
        isRotating = b;
    }

    @Override
    public void moveDown() {
        Board b = Board.getInstance();
        if (!b.isPaused()) {
            Board.reentLock.lock();
            logger.traceEntry("moveDown()");

            try {
                Point curr = getLocation();

                b.removeTileset(curr, BlockOffsets.getOffsetList(shape, currOrient));

                if (checkLocation(new Point(curr.x, curr.y + 1), b)) {
                    setLocation(curr.x, ++curr.y);
                } else {
                    isDropping = false;
                    logger.debug("isDropping is false");
                }
                b.addTileset(loc, BlockOffsets.getOffsetList(shape, currOrient));
            } finally {
                Board.reentLock.unlock();
            }
        }
    }

    @Override
    public void moveLeft() {
        Board b = Board.getInstance();
        if (!b.isPaused()) {
            Board.reentLock.lock();
            try {
                Point curr = getLocation();
                b.removeTileset(curr, BlockOffsets.getOffsetList(shape, currOrient));
                if (checkLocation(new Point(curr.x - 1, curr.y), b)) {
                    setLocation(--curr.x, curr.y);
                }
                b.addTileset(loc, BlockOffsets.getOffsetList(shape, currOrient));
            } finally {
                Board.reentLock.unlock();
            }
        }
    }

    @Override
    public void moveRight() {
        Board b = Board.getInstance();
        if (!b.isPaused()) {
            Board.reentLock.lock();
            try {
                Point curr = getLocation();
                b.removeTileset(curr, BlockOffsets.getOffsetList(shape, currOrient));
                if (checkLocation(new Point(curr.x + 1, curr.y), b)) {
                    setLocation(++curr.x, curr.y);
                }
                b.addTileset(loc, BlockOffsets.getOffsetList(shape, currOrient));
            } finally {
                Board.reentLock.unlock();
            }
        }
    }

    //Rotate clockwise
    @Override
    public void rotateRight() {
        Board b = Board.getInstance();
        if (!b.isPaused()) {
            Board.reentLock.lock();
            try {
                Point pivot = getLocation();
                List<BlockOrientation> avail = BlockOffsets.getAvailableOrientations(shape);
                int i = avail.indexOf(currOrient) + 1;
                if (i >= avail.size()) {
                    i = 0;   //wrap around
                }
                BlockOrientation old = currOrient;
                b.removeTileset(loc, BlockOffsets.getOffsetList(shape, currOrient));
                currOrient = avail.get(i);
                if (!checkLocation(pivot, b)) {
                    currOrient = old;
                }

                setTiles(loc);
                b.addTileset(loc, BlockOffsets.getOffsetList(shape, currOrient));   //add tileset to board
            } finally {
                Board.reentLock.unlock();
            }
        }
    }

    //rotate counter clockwise
    @Override
    public void rotateLeft() {
        Board b = Board.getInstance();

        if (!b.isPaused()) {
            Board.reentLock.lock();
            try {
                Point pivot = getLocation();
                List<BlockOrientation> avail = BlockOffsets.getAvailableOrientations(shape);
                int i = avail.indexOf(currOrient) - 1;
                if (i < 0) {
                    i = avail.size() - 1;   //wrap around
                }
                BlockOrientation old = currOrient;
                b.removeTileset(loc, BlockOffsets.getOffsetList(shape, currOrient));
                currOrient = avail.get(i);
                if (!checkLocation(pivot, b)) {
                    currOrient = old;
                }

                setTiles(loc);
                b.addTileset(loc, BlockOffsets.getOffsetList(shape, currOrient));
            } finally {
                Board.reentLock.unlock();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Block[shape=").append(shape);
        sb.append(", orient=").append(currOrient);
        for (BlockTile t : tiles) {
            sb.append(t);
        }
        sb.append("]");
        return sb.toString();
    }
}
