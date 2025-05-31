package org.example;

import static org.example.GameConstants.*;

public class Apple {
    private int x;
    private int y;

    public Apple() {
        locateApple();
    }

    public void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        x = r * DOT_SIZE;
        r = (int) (Math.random() * RAND_POS);
        y = r * DOT_SIZE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
