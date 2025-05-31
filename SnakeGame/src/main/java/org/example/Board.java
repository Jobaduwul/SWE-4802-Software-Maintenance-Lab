package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import static org.example.GameConstants.*;

public class Board extends JPanel implements java.awt.event.ActionListener {

    private final List<SnakeSegment> snake = new ArrayList<>();
    private final Score score = new Score();
    private final ObstacleManager obstacleManager = new ObstacleManager();
    private final GameRenderer renderer = new GameRenderer();
    private final GameStateManager gameStateManager;
    private final Image ball = GameImages.loadImage("/dot.png");
    private final Image appleImg = GameImages.loadImage("/apple.png");
    private final Image head = GameImages.loadImage("/head.png");
    private final Image obstacleImg = GameImages.loadImage("/obstacle.png");

    private final Apple apple = new Apple();
    private Direction direction = Direction.RIGHT;
    protected boolean directionChanged = false;

    public Board() {
        setFocusable(true);
        setBackground(Color.black);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        addKeyListener(new TAdapter(this));
        initGame();
        gameStateManager = new GameStateManager(DELAY, this);
    }
    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }


    private void initGame() {
        snake.clear();
        for (int i = 0; i < 3; i++) {
            snake.add(new SnakeSegment(50 - i * DOT_SIZE, 50));
        }

        score.reset();
        obstacleManager.getObstacles().clear();
        obstacleManager.generateObstacles(snake, apple);
        apple.locateApple(snake, obstacleManager.getObstacles());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameStateManager.isInGame()) {
            if (!gameStateManager.isGameStarted()) {
                renderer.drawStartMessage(g);
            } else {
                renderer.renderGame(g, snake, apple, obstacleManager.getObstacles(),
                        head, ball, appleImg, obstacleImg, score);
            }
        } else {
            renderer.drawGameOver(g, score.getScore());
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void checkApple() {
        SnakeSegment head = snake.get(0);
        if (head.x == apple.getX() && head.y == apple.getY()) {
            snake.add(new SnakeSegment(0, 0)); // dummy segment
            apple.locateApple(snake, obstacleManager.getObstacles());
            score.increment();
        }
    }

    private void move() {
        for (int i = snake.size() - 1; i > 0; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        SnakeSegment head = snake.get(0);
        switch (direction) {
            case LEFT -> head.x -= DOT_SIZE;
            case RIGHT -> head.x += DOT_SIZE;
            case UP -> head.y -= DOT_SIZE;
            case DOWN -> head.y += DOT_SIZE;
        }
    }

    private void checkCollision() {
        SnakeSegment head = snake.get(0);
        if (CollisionUtil.isSnakeSelfColliding(snake)
                || CollisionUtil.isOutOfBounds(head)
                || CollisionUtil.isObstacleCollision(head, obstacleManager.getObstacles())) {
            gameStateManager.setInGame(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStateManager.isInGame()) {
            checkApple();
            checkCollision();
            move();
            directionChanged = false;
        }
        repaint();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        if (!directionChanged) {
            this.direction = direction;
            directionChanged = true;
            if (!gameStateManager.isGameStarted()) {
                gameStateManager.setGameStarted(true);
            }
        }
    }
}
