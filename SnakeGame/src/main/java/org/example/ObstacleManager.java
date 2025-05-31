package org.example;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.example.GameConstants.*;

public class ObstacleManager {
    private final List<Obstacle> obstacles = new ArrayList<>();

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void generateObstacles(List<SnakeSegment> snake, Apple apple) {
        Set<String> used = new HashSet<>();
        for (SnakeSegment s : snake) used.add(s.x + "," + s.y);

        List<List<Point>> shapes = List.of(
                List.of(new Point(0, 0), new Point(DOT_SIZE, 0), new Point(0, DOT_SIZE)),             // L shape
                List.of(new Point(0, 0), new Point(-DOT_SIZE, 0), new Point(0, DOT_SIZE)),            // reverse L
                List.of(new Point(0, 0), new Point(0, DOT_SIZE), new Point(0, 2 * DOT_SIZE)),         // vertical line
                List.of(new Point(0, 0), new Point(DOT_SIZE, 0), new Point(2 * DOT_SIZE, 0)),         // horizontal line
                List.of(new Point(0, 0), new Point(DOT_SIZE, 0), new Point(0, DOT_SIZE), new Point(DOT_SIZE, DOT_SIZE)) // square
        );

        int attempts = 0;
        int shapesPlaced = 0;

        while (shapesPlaced < 10 && attempts < 100) {
            attempts++;
            List<Point> shape = shapes.get((int) (Math.random() * shapes.size()));

            int anchorX = ((int) (Math.random() * RAND_POS)) * DOT_SIZE;
            int anchorY = ((int) (Math.random() * RAND_POS)) * DOT_SIZE;

            List<Point> absolutePoints = new ArrayList<>();
            boolean valid = true;

            for (Point p : shape) {
                int x = anchorX + p.x;
                int y = anchorY + p.y;

                if (x < 0 || y < 0 || x >= B_WIDTH || y >= B_HEIGHT) {
                    valid = false;
                    break;
                }

                String key = x + "," + y;
                if (used.contains(key) || (x == apple.getX() && y == apple.getY())) {
                    valid = false;
                    break;
                }

                absolutePoints.add(new Point(x, y));
            }

            if (valid) {
                for (Point pt : absolutePoints) {
                    obstacles.add(new Obstacle(pt.x, pt.y));
                    used.add(pt.x + "," + pt.y);
                }
                shapesPlaced++;
            }
        }
    }
}
