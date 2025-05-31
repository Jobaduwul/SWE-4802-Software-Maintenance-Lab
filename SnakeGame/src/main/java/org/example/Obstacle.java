package org.example;

import java.awt.*;

public class Obstacle {
    private final int x;
    private final int y;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point getPoint() {
        return new Point(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
