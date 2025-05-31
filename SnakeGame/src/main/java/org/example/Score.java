package org.example;

public class Score {
    private int applesEaten = 0;

    public void increment() {
        applesEaten++;
    }

    public int getScore() {
        return applesEaten;
    }

    public void reset() {
        applesEaten = 0;
    }
}
