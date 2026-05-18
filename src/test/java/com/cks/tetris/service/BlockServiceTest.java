package com.cks.tetris.service;

import com.cks.tetris.math.RotationMatrix;
import com.cks.tetris.model.block.Block;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlockServiceTest {

    @InjectMocks
    BlockService service;

    @Nested
    class RotateBlockTest {
        @Test
        void whenCalled() {
            Block original = mock(Block.class);
            Block rotated = mock(Block.class);
            when(original.rotate(RotationMatrix.CLOCK_WISE_90)).thenReturn(rotated);

            Block actual = service.rotateBlock(original, RotationMatrix.CLOCK_WISE_90);

            assertThat(actual).isSameAs(rotated);
            verify(original).rotate(RotationMatrix.CLOCK_WISE_90);
        }
    }
}
