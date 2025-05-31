package org.example;

import javax.swing.*;

public class GameStateManager {
    private boolean inGame = true;
    private boolean gameStarted = false;
    private Timer timer;

    public GameStateManager(int delay, java.awt.event.ActionListener listener) {
        this.timer = new Timer(delay, listener);
        this.timer.start();
        this.timer.stop(); // Game paused at start
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
        if (!inGame && timer != null) {
            timer.stop();
        }
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
        if (gameStarted && timer != null) {
            timer.start();
        }
    }

    public Timer getTimer() {
        return timer;
    }
}
