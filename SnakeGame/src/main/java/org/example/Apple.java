package org.example;

import java.util.List;
import java.util.Random;

import static org.example.GameConstants.*;

public class Apple {
    private int x;
    private int y;

    private final Random random = new Random();

    public Apple() {
        locateApple(null, null); // default
    }

    public void locateApple(List<SnakeSegment> snake, List<Obstacle> obstacles) {
        while (true) {
            int newX = random.nextInt(RAND_POS) * DOT_SIZE;
            int newY = random.nextInt(RAND_POS) * DOT_SIZE;

            boolean collidesWithSnake = false;
            boolean collidesWithObstacle = false;

            if (snake != null) {
                for (SnakeSegment segment : snake) {
                    if (segment.x == newX && segment.y == newY) {
                        collidesWithSnake = true;
                        break;
                    }
                }
            }

            if (obstacles != null) {
                for (Obstacle obstacle : obstacles) {
                    if (obstacle.getX() == newX && obstacle.getY() == newY) {
                        collidesWithObstacle = true;
                        break;
                    }
                }
            }

            if (!collidesWithSnake && !collidesWithObstacle) {
                x = newX;
                y = newY;
                break;
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
