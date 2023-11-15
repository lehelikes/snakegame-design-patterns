package edu.snake.game.controller.single;

import edu.snake.game.controller.GameController;
import edu.snake.game.model.Apple;
import edu.snake.game.MainFrame;
import edu.snake.game.model.Snake;
import edu.snake.game.settings.SettingsManager;
import edu.snake.game.settings.SettingsManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;

public class SingleController extends GameController {

    private static final Logger LOG = LoggerFactory.getLogger(SingleController.class);

    private final SinglePanel singlePanel;
    private final Snake snake;
    private final Apple apple;
    private final int gameSpeed;

    private boolean gameRunning;

    public SingleController(SinglePanel singlePanel) {
        this.singlePanel = singlePanel;

        SettingsManager settingsManager = SettingsManagerFactory.getSettingsManager();
        Integer speedSetting = settingsManager.getSettings().getSpeed();
        if (speedSetting <= 0) {
            speedSetting *= -1;
        }
        gameSpeed = speedSetting;
        gameRunning = false;
        snake = new Snake();
        apple = new Apple();

        LOG.info("Game speed is: {}", gameSpeed);
    }

    protected void prepareGame() {
        singlePanel.setSnake(snake);
        singlePanel.setApple(apple);

        singlePanel.setKeyPaused(false);
        gameRunning = true;
    }

    protected void runGame() {
        try {
            while (true) {
                singlePanel.setKeyPaused(false);
                snake.move();
                snake.checkAppleCollision(apple);
                if (snake.checkCollision()) { // true = collided with something
                    gameRunning = false;
                }

                if (!gameRunning) {
                    Thread.currentThread().interrupt();
                }

                if (!Thread.currentThread().isInterrupted()) {
                    singlePanel.repaint();
                }

                Thread.sleep(gameSpeed);
            }
        } catch (InterruptedException e) {
            MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(singlePanel);
            topFrame.signalGameOver(snake.getSnakeApplesEaten(), false);
        }
    }

}
