package org.example;

import java.awt.*;
import java.util.List;

import static org.example.GameConstants.*;

public class GameRenderer {
    public void renderGame(Graphics g, List<SnakeSegment> snake, Apple apple,
                           List<Obstacle> obstacles, Image head, Image ball, Image appleImg,
                           Image obstacleImg, Score score) {
        g.drawImage(appleImg, apple.getX(), apple.getY(), null);

        for (int i = 0; i < snake.size(); i++) {
            SnakeSegment s = snake.get(i);
            g.drawImage(i == 0 ? head : ball, s.x, s.y, null);
        }

        for (Obstacle obs : obstacles) {
            g.drawImage(obstacleImg, obs.getX(), obs.getY(), null);
        }

        g.setColor(Color.BLACK);
        g.fillRect(B_WIDTH - 80, B_HEIGHT - 25, 80, 20); // Background for score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Score: " + score.getScore(), B_WIDTH - 70, B_HEIGHT - 10);
    }

    public void drawStartMessage(Graphics g) {
        String msg = "Press Arrow Key to Start";
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = g.getFontMetrics(font);

        g.setColor(Color.BLACK);
        g.fillRect((B_WIDTH - metr.stringWidth(msg)) / 2 - 5, B_HEIGHT / 2 - 15,
                metr.stringWidth(msg) + 10, 25);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    public void drawGameOver(Graphics g, int score) {
        String msg = "Game Over";
        String scoreMsg = "Final Score: " + score;
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = g.getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
        g.drawString(scoreMsg, (B_WIDTH - metr.stringWidth(scoreMsg)) / 2, B_HEIGHT / 2 + 20);
    }
}
