package com.example.memorymatching;

public enum Difficulty {
    Easy(2, 4),
    Medium(4, 4),
    Hard(6, 6);

    public final int rows;
    public final int columns;

    Difficulty(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }
}
