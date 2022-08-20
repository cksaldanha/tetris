/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cks.tetris.offset;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Stores the offset sets for each orientation of a block Not meant to be used
 * outside of package (use BlockOffsets for static methods)
 *
 * @author colin.saldanha
 */
final class BlockOffset {

    private final Map<BlockOrientation, List<Offset>> map;
    private BlockOrientation defaultOrient;

    BlockOffset(Map<BlockOrientation, List<Offset>> m) {
        map = new HashMap<>();
        m.forEach((k, v) -> map.put(k, new ArrayList<>(v)));
    }

    int getNumOrientations() {
        return map.size();
    }

    List<BlockOrientation> getAvailableOrientations() {
        return map.keySet().stream().sorted().collect(Collectors.toCollection(ArrayList::new));
    }

    BlockOrientation getDefaultOrientation() {
        return defaultOrient;
    }

    void setDefaultOrientation(BlockOrientation o) {
        if (!map.containsKey(o)) {
            throw new IllegalArgumentException("Must use an existing orientation");
        }

        defaultOrient = o;
    }

    boolean containsOrientation(BlockOrientation o) {
        return map.containsKey(o);
    }

    List<Offset> getOffsetList(BlockOrientation orient) {
        if (!map.containsKey(orient)) {
            throw new IllegalArgumentException("Orientation not available.");
        }

        return Collections.unmodifiableList(map.get(orient));
    }
}
