package org.example;

import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static org.example.GameConstants.*;

public class Board extends JPanel implements ActionListener {

    private List<Obstacle> obstacles = new ArrayList<>();
    private final int NUM_OBSTACLES = 10;
    private Image obstacleImg = GameImages.loadImage("/obstacle.png"); // Create this image file
    private List<SnakeSegment> snake;
    private Direction direction = Direction.RIGHT;
    private boolean inGame = true;

    private Apple apple;
    private Timer timer;

    private Image ball = GameImages.loadImage("/dot.png");
    private Image appleImg = GameImages.loadImage("/apple.png");
    private Image head = GameImages.loadImage("/head.png");

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter(this));
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        initGame();
    }

    private void initGame() {
        snake = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            snake.add(new SnakeSegment(50 - i * DOT_SIZE, 50));
        }

        apple = new Apple();
        generateObstacles(); // <<--- Add this
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void generateObstacles() {
        Set<String> used = new HashSet<>();
        for (SnakeSegment s : snake) used.add(s.x + "," + s.y);
        // Shapes list, you can add more shapes here
        List<List<Point>> shapes = List.of(
                List.of(new Point(0, 0), new Point(DOT_SIZE, 0), new Point(0, DOT_SIZE)),             // L shape
                List.of(new Point(0, 0), new Point(-DOT_SIZE, 0), new Point(0, DOT_SIZE)),            // reverse L
                List.of(new Point(0, 0), new Point(0, DOT_SIZE), new Point(0, 2 * DOT_SIZE)),         // vertical line
                List.of(new Point(0, 0), new Point(DOT_SIZE, 0), new Point(2 * DOT_SIZE, 0)),         // horizontal line
                List.of(new Point(0, 0), new Point(DOT_SIZE, 0), new Point(0, DOT_SIZE), new Point(DOT_SIZE, DOT_SIZE)) // square
        );

        int attempts = 0;
        int shapesPlaced = 0;

        while (shapesPlaced < NUM_OBSTACLES && attempts < 100) {
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



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(appleImg, apple.getX(), apple.getY(), this);
            for (int i = 0; i < snake.size(); i++) {
                SnakeSegment s = snake.get(i);
                g.drawImage(i == 0 ? head : ball, s.x, s.y, this);
            }
            for (Obstacle obs : obstacles) {
                g.drawImage(obstacleImg, obs.getX(), obs.getY(), this);
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkApple() {
        SnakeSegment head = snake.get(0);
        if (head.x == apple.getX() && head.y == apple.getY()) {
            snake.add(new SnakeSegment(0, 0)); // Dummy values; corrected in move()
            apple.locateApple();
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

        for (int i = 4; i < snake.size(); i++) {
            SnakeSegment s = snake.get(i);
            if (head.x == s.x && head.y == s.y) inGame = false;
        }

        if (head.x < 0 || head.x >= B_WIDTH || head.y < 0 || head.y >= B_HEIGHT) {
            inGame = false;
        }

        for (Obstacle obs : obstacles) {
            if (head.x == obs.getX() && head.y == obs.getY()) {
                inGame = false;
                break;
            }
        }

        if (!inGame) timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
