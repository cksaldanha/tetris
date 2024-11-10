package com.cks.tetris.offset;

import com.cks.tetris.block.BlockShape;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author colin.saldanha
 */
public final class BlockOffsets {

    private static final Map<BlockShape, BlockOffset> offsets = new HashMap<>();

    static {
        setup();
    }

    private BlockOffsets() {
    }

    private static void setup() {
        offsets.put(BlockShape.I_SHAPE, createIBlockOffsets());
        offsets.put(BlockShape.L_SHAPE, createLBlockOffsets());
        offsets.put(BlockShape.O_SHAPE, createOBlockOffsets());
        offsets.put(BlockShape.S_SHAPE, createSBlockOffsets());
        offsets.put(BlockShape.T_SHAPE, createTBlockOffsets());
    }

    private static BlockOffset createIBlockOffsets() {
        Map<BlockOrientation, List<Offset>> map = new HashMap<>();
        // UP = vertical line
        List<Offset> list = new ArrayList<>();
        list.add(new Offset(0, 0));
        list.add(new Offset(0, 1));
        list.add(new Offset(0, 2));
        list.add(new Offset(0, 3));
        map.put(BlockOrientation.UP, list);

        // RIGHT = horizontal
        list = new ArrayList<>();
        list.add(new Offset(0, 0));
        list.add(new Offset(1, 0));
        list.add(new Offset(2, 0));
        list.add(new Offset(3, 0));
        map.put(BlockOrientation.RIGHT, list);

        BlockOffset bo = new BlockOffset(map);
        bo.setDefaultOrientation(BlockOrientation.RIGHT);
        return bo;
    }

    private static BlockOffset createLBlockOffsets() {
        Map<BlockOrientation, List<Offset>> map = new HashMap<>();

        List<Offset> list = new ArrayList<>();
        list.add(new Offset(0, 0));
        list.add(new Offset(0, 1));
        list.add(new Offset(0, 2));
        list.add(new Offset(1, 2));
        map.put(BlockOrientation.UP, list);

        list = new ArrayList<>();
        list.add(new Offset(0, 0));
        list.add(new Offset(1, 0));
        list.add(new Offset(2, 0));
        list.add(new Offset(2, -1));
        map.put(BlockOrientation.LEFT, list);

        list = new ArrayList<>();
        list.add(new Offset(0, 0));
        list.add(new Offset(-1, 0));
        list.add(new Offset(-2, 0));
        list.add(new Offset(-2, 1));
        map.put(BlockOrientation.RIGHT, list);

        list = new ArrayList<>();
        list.add(new Offset(0, 0));
        list.add(new Offset(0, -1));
        list.add(new Offset(0, -2));
        list.add(new Offset(-1, -2));
        map.put(BlockOrientation.DOWN, list);

        BlockOffset bo = new BlockOffset(map);
        bo.setDefaultOrientation(BlockOrientation.UP);
        return bo;
    }

    private static BlockOffset createOBlockOffsets() {
        Map<BlockOrientation, List<Offset>> map = new HashMap<>();
        List<Offset> list = new ArrayList<>();
        list.add(new Offset(0, 0));
        list.add(new Offset(1, 0));
        list.add(new Offset(0, 1));
        list.add(new Offset(1, 1));
        map.put(BlockOrientation.UP, list);

        BlockOffset bo = new BlockOffset(map);
        bo.setDefaultOrientation(BlockOrientation.UP);
        return bo;
    }

    private static BlockOffset createSBlockOffsets() {
        Map<BlockOrientation, List<Offset>> map = new HashMap<>();
        List<Offset> list = new ArrayList<>();
        list.add(new Offset(0, 0));
        list.add(new Offset(0, 1));
        list.add(new Offset(1, 1));
        list.add(new Offset(1, 2));
        map.put(BlockOrientation.UP, list);

        list = new ArrayList<>();
        list.add(new Offset(0, 0));
        list.add(new Offset(1, 0));
        list.add(new Offset(1, -1));
        list.add(new Offset(2, -1));
        map.put(BlockOrientation.RIGHT, list);

        BlockOffset bo = new BlockOffset(map);
        bo.setDefaultOrientation(BlockOrientation.UP);
        return bo;
    }

    private static BlockOffset createTBlockOffsets() {
        Map<BlockOrientation, List<Offset>> map = new HashMap<>();
        List<Offset> list = new ArrayList<>();
        list.add(new Offset(0, 0));
        list.add(new Offset(0, 1));
        list.add(new Offset(-1, 1));
        list.add(new Offset(1, 1));
        map.put(BlockOrientation.UP, list);

        list = new ArrayList<>();
        list.add(new Offset(0, 0));
        list.add(new Offset(1, 0));
        list.add(new Offset(1, -1));
        list.add(new Offset(1, 1));
        map.put(BlockOrientation.RIGHT, list);

        list = new ArrayList<>();
        list.add(new Offset(0, 0));
        list.add(new Offset(-1, 0));
        list.add(new Offset(-1, 1));
        list.add(new Offset(-1, -1));
        map.put(BlockOrientation.LEFT, list);

        list = new ArrayList<>();
        list.add(new Offset(0, 0));
        list.add(new Offset(0, -1));
        list.add(new Offset(-1, -1));
        list.add(new Offset(1, -1));
        map.put(BlockOrientation.DOWN, list);

        BlockOffset bo = new BlockOffset(map);
        bo.setDefaultOrientation(BlockOrientation.UP);
        return bo;
    }

    public static List<BlockOrientation> getAvailableOrientations(BlockShape shape) {
        if (!offsets.containsKey(shape)) {
            throw new IllegalArgumentException("Shape not in map");
        }

        return offsets.get(shape).getAvailableOrientations();
    }

    public static BlockOrientation getDefaultOrientation(BlockShape shape) {
        if (!offsets.containsKey(shape)) {
            throw new IllegalArgumentException("Shape is not in map");
        }

        return offsets.get(shape).getDefaultOrientation();
    }

    public static int getNumOrientations(BlockShape shape) {
        if (!offsets.containsKey(shape)) {
            throw new IllegalArgumentException("Shape is not in map");
        }

        return offsets.get(shape).getNumOrientations();
    }

    public static boolean hasOrientation(BlockShape shape, BlockOrientation orient) {
        if (!offsets.containsKey(shape)) {
            return false;
        }

        return offsets.get(shape).containsOrientation(orient);
    }

    public static List<Offset> getOffsetList(BlockShape shape, BlockOrientation orient) {
        return Collections.unmodifiableList(offsets.get(shape).getOffsetList(orient));
    }
}
