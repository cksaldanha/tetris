package com.cks.tetris.block;

import com.cks.tetris.board.Board;
import com.cks.tetris.offset.BlockOffsets;

import java.util.Map;
import java.util.HashMap;
import java.awt.Color;
import java.util.Random;

/**
 * Supplies Block objects throughout game
 *
 * @author colin.saldanha
 */
public final class BlockFactory {

    private static final Map<BlockShape, Color> COLORS;
    private static final Random random;

    static {
        COLORS = new HashMap<>();
        COLORS.put(BlockShape.I_SHAPE, Color.RED);
        COLORS.put(BlockShape.L_SHAPE, Color.GREEN);
        COLORS.put(BlockShape.O_SHAPE, Color.BLUE);
        COLORS.put(BlockShape.S_SHAPE, Color.ORANGE);
        COLORS.put(BlockShape.T_SHAPE, Color.MAGENTA);

        random = new Random();
    }

    private BlockFactory() {
        /* Empty */ }

    public static Color getColor(BlockShape shape) {
        return COLORS.get(shape);
    }

    public static Block createRandomBlock() {
        int i = random.nextInt(BlockShape.values().length);
        return createBlock(BlockShape.values()[i]);
    }

    public static Block createBlock(BlockShape shape) {
        switch (shape) {
            case I_SHAPE:
                return createIBlock();
            case L_SHAPE:
                return createLBlock();
            case O_SHAPE:
                return createOBlock();
            case S_SHAPE:
                return createSBlock();
            case T_SHAPE:
                return createTBlock();
            default:
                assert false : "Unsupported shape provided";
                return null;
        }
    }

    public static Block createIBlock() {
        return new IBlock();
    }

    public static Block createLBlock() {
        return new LBlock();
    }

    public static Block createOBlock() {
        return new OBlock();
    }

    public static Block createSBlock() {
        return new SBlock();
    }

    public static Block createTBlock() {
        return new TBlock();
    }

    private static class IBlock extends Block {

        private IBlock() {
            super(BlockShape.I_SHAPE);
            currOrient = BlockOffsets.getDefaultOrientation(shape);
            setLocation(Board.MAX_COLS / 2 - 2, 0);
        }
    }

    private static class LBlock extends Block {

        private LBlock() {
            super(BlockShape.L_SHAPE);
            currOrient = BlockOffsets.getDefaultOrientation(shape);
            setLocation(Board.MAX_COLS / 2, 0);
        }
    }

    private static class OBlock extends Block {

        private OBlock() {
            super(BlockShape.O_SHAPE);
            currOrient = BlockOffsets.getDefaultOrientation(shape);
            setLocation(Board.MAX_COLS / 2 - 1, 0);
        }
    }

    private static class SBlock extends Block {

        private SBlock() {
            super(BlockShape.S_SHAPE);
            currOrient = BlockOffsets.getDefaultOrientation(shape);
            setLocation(Board.MAX_COLS / 2 - 1, 0);
        }
    }

    private static class TBlock extends Block {

        private TBlock() {
            super(BlockShape.T_SHAPE);
            currOrient = BlockOffsets.getDefaultOrientation(shape);
            setLocation(Board.MAX_COLS / 2, 0);
        }
    }
}
