package com.cks.tetris.model;

import com.cks.tetris.math.RotationMatrix;

public interface Rotatable {

    Rotatable rotate(RotationMatrix rotationMatrix);
}
