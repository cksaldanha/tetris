package com.cks.tetris.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.tetris.score")
public class ScoreProperties {

    public final Multiplier multiplier;

    @Data
    public static class Multiplier {
        private final int singleLine;
        private final int doubleLine;
        private final int tripleLine;
        private final int tetrisLine;
    }

}
