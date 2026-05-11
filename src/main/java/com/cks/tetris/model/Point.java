package com.cks.tetris.model;

import com.cks.tetris.math.RotationMatrix;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@EqualsAndHashCode
public class Point implements Rotatable {

    private static final Map<Integer, Map<Integer, Point>> POINTS_CACHE;
    public static final Point ORIGIN;

    static {
        POINTS_CACHE = buildCache(-5, 5);
        ORIGIN = getFromCache(0, 0);
    }

    public final int x;
    public final int y;

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        return containedInCache(x, y) ? getFromCache(x, y) : new Point(x, y);
    }

    private static Map<Integer, Map<Integer, Point>> buildCache(int lowerLimit, int upperLimit) {
        Map<Integer, Map<Integer, Point>> cache = new HashMap<>();
        for (int x = lowerLimit; x <= upperLimit; x++) {
            for (int y = lowerLimit; y <= upperLimit; y++) {
                Point pt = new Point(x, y);
                cache.computeIfAbsent(x, unused -> new HashMap<>()).put(y, pt);
            }
        }
        return cache;
    }

    private static boolean containedInCache(int x, int y) {
        return POINTS_CACHE.containsKey(x) && POINTS_CACHE.get(x).containsKey(y);
    }

    private static Point getFromCache(int x, int y) {
        return POINTS_CACHE.get(x).get(y);
    }

    @Override
    public Point rotate(RotationMatrix rotationMatrix) {
        return rotationMatrix.rotate(this);
    }
}
