package com.cks.tetris.util;

import java.util.Arrays;

public class ArrayUtils {

    private ArrayUtils() {
        /* non-instantiated utility class */
    }

    public static <T> boolean equals(T[][] left, T[][] right) {
        if (left == right) {
            return true;
        }

        if (left == null || right == null) {
            return false;
        }

        if (left.length != right.length) {
            return false;
        }

        for (int r = 0; r < left.length; r++) {
            if (!Arrays.equals(left[r], right[r])) {
                return false;
            }
        }

        return true;
    }

    public static boolean equals(int[][] left, int[][] right) {
        if (left == right) {
            return true;
        }

        if (left == null || right == null) {
            return false;
        }

        if (left.length != right.length) {
            return false;
        }

        for (int r = 0; r < left.length; r++) {
            if (!Arrays.equals(left[r], right[r])) {
                return false;
            }
        }

        return true;
    }

    public static String toString(int[][] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(System.lineSeparator());
        for (int[] row : array) {
            sb.append("  [");
            for (int num : row) {
                sb.append(String.format("%3d\t", num));
            }
            sb.append("]").append(System.lineSeparator());
        }
        sb.append("]");
        return sb.toString();
    }

    public static <T> String toString(T[][] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(System.lineSeparator());
        for (T[] row : array) {
            sb.append("  [");
            for (T val : row) {
                sb.append(val).append("\t");
            }
            sb.append("]").append(System.lineSeparator());
        }
        sb.append("]");
        return sb.toString();
    }

}
