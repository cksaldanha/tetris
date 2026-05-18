package com.cks.tetris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Matrix<T> {

    private final List<List<T>> elements;

    public Matrix(int rows, int cols) {
        validateDimensions(rows, cols);
        elements = new ArrayList<>(rows);
        for (int r = 0; r < rows; r++) {
            elements.add(new ArrayList<>(cols));
            for (int c = 0; c < cols; c++) {
                elements.get(r).add(null);
            }
        }
    }

    public Matrix(Matrix<T> original) {
        Objects.requireNonNull(original, "Original matrix cannot be null");
        this.elements = listCopy2d(original.elements);
    }

    public Matrix(List<List<T>> elements) {
        Objects.requireNonNull(elements, "Elements cannot be null");
        this.elements = listCopy2d(elements);
    }

    private static <T> List<List<T>> listCopy2d(List<List<T>> original) {
        return original.stream()
                .map(Matrix::listCopy)
                .toList();
    }

    private static <T> List<T> listCopy(List<T> original) {
        return new ArrayList<>(original);
    }

    public T get(int row, int col) {
        return isOut(row, col) ? null : elements.get(row).get(col);
    }

    public List<List<T>> getAll() {
        return elements.stream()
                .map(ArrayList::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isOut(int row, int col) {
        return row < 0 || row >= getRowCount() || col < 0 || col >= getColumnCount();
    }

    public List<T> getRow(int row) {
        return List.copyOf(elements.get(row));
    }

    public List<T> getColumn(int col) {
        return IntStream.range(0, getRowCount())
                .mapToObj(r -> elements.get(r).get(col))
                .toList();
    }

    public int getRowCount() {
        return elements.size();
    }

    public int getColumnCount() {
        return elements.get(0).size();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Matrix<?> other
                && elements.equals(other.elements);
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    private static void validateDimensions(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Row and column counts must be positive");
        }
    }
}
