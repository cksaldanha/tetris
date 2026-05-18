package com.cks.tetris.model.block;

import com.cks.tetris.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BlockTest {

    @Nested
    class GetOffsetsTest {
        Block block;

        @BeforeEach
        void setUp() {
            block = new ZBlock();
        }

        @Test
        @DisplayName("should return an immutable set")
        void whenCalled() {
               Set<Point> offsets = block.getOffsets();

               assertThat(offsets)
                       .isNotNull()
                       .hasSize(4);

               assertThatThrownBy(() -> offsets.add(Point.ORIGIN))
                       .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    class EqualsAndHashCodeTest {
        @Test
        void whenEqualOffsets() {
            Block b1 = new TBlock();
            Block b2 = new TBlock();

            assertThat(b1)
                    .isEqualTo(b2)
                    .hasSameHashCodeAs(b2)
                    .isNotSameAs(b2);
        }

        @Test
        void whenUnequalOffsets() {
            Block b1 = new TBlock();
            Block b2 = new SBlock();

            assertThat(b1)
                    .isNotEqualTo(b2)
                    .doesNotHaveSameHashCodeAs(b2)
                    .isNotSameAs(b2);
        }
    }
}
