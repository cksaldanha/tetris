package com.cks.tetris.math;

import com.cks.tetris.util.ArrayUtils;

import java.util.Objects;
import java.util.stream.IntStream;

public class IntMatrix {

    private final int[][] matrix;
    private final int rowCount;
    private final int colCount;

    public IntMatrix(int[][] matrix) {
        this.matrix = Objects.requireNonNull(matrix, "matrix cannot be null").clone();
        validateMatrix(matrix);
        this.rowCount = matrix.length;
        this.colCount = matrix[0].length;
    }

    /**
     * Copy constructor. Creates a copy of the original matrix.
     *
     * @param orig to be copied
     */
    public IntMatrix(IntMatrix orig) {
        this(orig.matrix);
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return colCount;
    }

    public int get(int r, int c) {
        return matrix[r][c];
    }

    public int[] getRow(int r) {
        return matrix[r].clone();
    }

    public int[] getColumn(int c) {
        return IntStream.range(0, rowCount).map(r -> matrix[r][c]).toArray();
    }

    public int[][] getAll() {
        return matrix.clone();
    }

    public IntMatrix times(IntMatrix other) {
        if (this.colCount != other.rowCount) {
            throw new IllegalArgumentException("Column count of first matrix must be equal to row count of second matrix");
        }

        int[][] result = new int[this.rowCount][other.colCount];
        for (int r = 0; r < this.rowCount; r++) {
            for (int c = 0; c < other.colCount; c++) {
                int sum = 0;
                for (int i = 0; i < this.colCount; i++) {
                    sum += this.matrix[r][i] * other.matrix[i][c];
                }
                result[r][c] = sum;
            }
        }
        return new IntMatrix(result);
    }

    public IntMatrix transpose() {
        int[][] transposed = new int[colCount][rowCount];
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                transposed[c][r] = matrix[r][c];
            }
        }
        return new IntMatrix(transposed);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IntMatrix other
                && ArrayUtils.equals(matrix, other.matrix);
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                hashCode += 13 * matrix[r][c];
            }
        }
        return hashCode;
    }

    @Override
    public String toString() {
        return ArrayUtils.toString(matrix);
    }

    private static void validateMatrix(int[][] matrix) {
        validateHasRow(matrix);
        validateHasSameColumnLength(matrix);
    }

    private static void validateHasRow(int[][] matrix) {
        if (matrix.length <= 0) {
            throw new IllegalArgumentException("Matrix must have at least one row");
        }
    }

    private static void validateHasSameColumnLength(int[][] matrix) {
        int cols = matrix[0].length;
        for (int r = 1; r < matrix.length; r++) {
            if (matrix[r].length != cols) {
                throw new IllegalArgumentException("All rows must have same column size");
            }
        }
    }

    public static IntMatrix of(int[][] matrix) {
        return new IntMatrix(matrix);
    }

    public static IntMatrix of(int[] matrix, int[]... rest) {
        int[][] combined = new int[1 + rest.length][];

        combined[0] = matrix.clone();
        for (int i = 0; i < rest.length; i++) {
            combined[i + 1] = rest[i].clone();
        }

        return new IntMatrix(combined);
    }

}
