package com.cks.tetris.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TileTest {

    @Nested
    class ConstructorTest {
        @Test
        void whenColorNull() {
            assertThatThrownBy(() -> new Tile(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("color");
        }
    }

    @Nested
    class OfColorTest {
        @ParameterizedTest
        @EnumSource(Color.class)
        @DisplayName("should return the same Tile instance for the same Color (if known)")
        void whenColorKnown(Color color) {
            Tile tile1 = Tile.ofColor(color);
            Tile tile2 = Tile.ofColor(color);

            assertThat(tile1).isSameAs(tile2);
        }

        @Test
        void whenColorNull() {
            assertThatThrownBy(() -> Tile.ofColor(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("color");
        }
    }
}
