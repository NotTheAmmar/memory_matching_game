package com.example.memorymatching;

public class Card {
    private final int color;
    private boolean matched = false;

    public Card(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public boolean isMatched() {
        return matched;
    }

    public boolean match(Card other) {
        if (color == other.color) {
            matched = true;
            other.matched = true;

            return true;
        } else {
            return false;
        }
    }

}
