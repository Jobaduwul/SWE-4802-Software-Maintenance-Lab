package org.example;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter extends KeyAdapter {
    private final Board board;

    public TAdapter(Board board) {
        this.board = board;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        Direction dir = board.getDirection();

        switch (key) {
            case KeyEvent.VK_LEFT:
                if (dir != Direction.RIGHT) board.setDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                if (dir != Direction.LEFT) board.setDirection(Direction.RIGHT);
                break;
            case KeyEvent.VK_UP:
                if (dir != Direction.DOWN) board.setDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                if (dir != Direction.UP) board.setDirection(Direction.DOWN);
                break;
        }
    }
}
