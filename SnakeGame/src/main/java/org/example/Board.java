package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static org.example.GameConstants.*;

public class Board extends JPanel implements ActionListener {

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
        timer = new Timer(DELAY, this);
        timer.start();
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
