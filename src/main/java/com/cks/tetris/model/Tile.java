package com.cks.tetris.model;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public record Tile(Color color) {

    public Tile {
        Objects.requireNonNull(color, "color cannot be null");
    }

    private static final Map<Color, Tile> COLOR_TO_TILE =
            Arrays.stream(Color.values())
            .map(Tile::new)
            .collect(Collectors.toMap(Tile::color, Function.identity()));

    public static Tile ofColor(Color color) {
        return Optional.ofNullable(COLOR_TO_TILE.get(color))
                .orElse(new Tile(color));
    }
}
