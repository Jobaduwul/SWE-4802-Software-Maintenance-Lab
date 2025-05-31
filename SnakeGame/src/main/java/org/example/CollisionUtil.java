package org.example;

import java.util.List;

public class CollisionUtil {

    public static boolean isSnakeSelfColliding(List<SnakeSegment> snake) {
        SnakeSegment head = snake.get(0);
        for (int i = 4; i < snake.size(); i++) {
            if (head.x == snake.get(i).x && head.y == snake.get(i).y) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOutOfBounds(SnakeSegment head) {
        return head.x < 0 || head.x >= GameConstants.B_WIDTH ||
                head.y < 0 || head.y >= GameConstants.B_HEIGHT;
    }

    public static boolean isObstacleCollision(SnakeSegment head, List<Obstacle> obstacles) {
        for (Obstacle obs : obstacles) {
            if (head.x == obs.getX() && head.y == obs.getY()) {
                return true;
            }
        }
        return false;
    }
}
