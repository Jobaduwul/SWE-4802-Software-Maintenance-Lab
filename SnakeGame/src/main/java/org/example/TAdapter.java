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
        GameStateManager gsm = board.getGameStateManager();

        if (!gsm.isGameStarted()) {
            gsm.setGameStarted(true);
            gsm.getTimer().start();
        }

        if (board.directionChanged) return;

        int key = e.getKeyCode();
        Direction current = board.getDirection();

        switch (key) {
            case KeyEvent.VK_LEFT:
                if (current != Direction.RIGHT) {
                    board.setDirection(Direction.LEFT);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (current != Direction.LEFT) {
                    board.setDirection(Direction.RIGHT);
                }
                break;
            case KeyEvent.VK_UP:
                if (current != Direction.DOWN) {
                    board.setDirection(Direction.UP);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (current != Direction.UP) {
                    board.setDirection(Direction.DOWN);
                }
                break;
        }
    }
}
