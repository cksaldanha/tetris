package com.cks.tetris.model.state;

public record Score(long total, int lines) {

    public Score {
        requireGreaterThanOrEqualTo(total, 0);
        requireGreaterThanOrEqualTo(lines, 0);
    }

    Score(Builder builder) {
        this(builder.total, builder.lines);
    }

    public Score addTotal(int amount) {
        return amount == 0 ? this : mutate().total(total + amount).build();
    }

    public Score addLines(int amount) {
        return amount == 0 ? this : mutate().lines(lines + amount).build();
    }

    public int level() {
        return lines / 10 + 1;
    }

    public Builder mutate() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long total;
        private int lines;

        public Builder() {
        }

        public Builder(Score original) {
            this.total = original.total;
            this.lines = original.lines;
        }

        public Builder total(long total) {
            this.total = total;
            return self();
        }

        public Builder lines(int lines) {
            this.lines = lines;
            return self();
        }

        protected Builder self() {
            return this;
        }

        public Score build() {
            return new Score(this);
        }
    }

    public static void requireGreaterThanOrEqualTo(long value, int floor) {
        if (value < floor) {
            throw new IllegalArgumentException("value cannot be lower than " + floor);
        }
    }
}
