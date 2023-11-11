package edu.snake.game.controller.multi.Server;

import edu.snake.game.controller.GameController;
import edu.snake.game.model.Apple;
import edu.snake.game.GameConstants;
import edu.snake.game.model.Snake;
import edu.snake.game.settings.Settings;
import edu.snake.game.settings.SettingsManager;
import edu.snake.game.settings.SettingsManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerGameController extends GameController {

    private static final Logger LOG = LoggerFactory.getLogger(ServerGameController.class);

    private boolean gameRunning;
    private int gameSpeed;
    private final Snake snake1;
    private final Snake snake2;
    private final Apple apple;
    private final ObjectOutputStream oos1;
    private final ObjectOutputStream oos2;
    private final ObjectInputStream ois1;
    private final ObjectInputStream ois2;
    private final SettingsManager settingsManager;

    public ServerGameController(Socket player1, Socket player2) throws IOException {
        gameRunning = false;
        snake1 = new Snake();
        snake2 = new Snake();
        apple = new Apple();

        oos1 = new ObjectOutputStream(player1.getOutputStream());
        oos2 = new ObjectOutputStream(player2.getOutputStream());
        ois1 = new ObjectInputStream(player1.getInputStream());
        ois2 = new ObjectInputStream(player2.getInputStream());

        settingsManager = SettingsManagerFactory.getSettingsManager();
        loadSettings();
    }

    private void loadSettings() {
        Settings settings = settingsManager.getSettings();
        gameSpeed = settings.getSpeed();
    }

    // Send data to clients
    protected void prepareGame() {
        // send to client 1
        try {
            oos1.writeObject(snake1);
            oos1.writeObject(snake2);
            oos1.writeObject(apple);

            // send to player 2
            oos2.writeObject(snake2);
            oos2.writeObject(snake1);
            oos2.writeObject(apple);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }

        gameRunning = true;
    }

    private void sendSnakes() throws IOException {
        // send to client 1
        oos1.writeObject(snake1);
        oos1.writeObject(snake2);

        // send to player 2
        oos2.writeObject(snake2);
        oos2.writeObject(snake1);

        oos1.reset();
        oos2.reset();
    }

    private void sendApple() throws IOException {
        oos1.writeObject(apple);
        oos2.writeObject(apple);

        oos1.reset();
        oos2.reset();
    }

    private void sendScores(boolean player1Win, boolean player2Win) throws IOException {
        oos1.writeObject(snake1.getSnakeApplesEaten());
        oos1.writeObject(player1Win);
        oos2.writeObject(snake2.getSnakeApplesEaten());
        oos2.writeObject(player2Win);
    }

    protected void runGame() {
        try {

            while (gameRunning) {
                // Only continue when clients are ready and they've sent their snake's directions
                GameConstants.Direction direction1 = (GameConstants.Direction) ois1.readObject();
                GameConstants.Direction direction2 = (GameConstants.Direction) ois2.readObject();

                if (snake1.getSnakeDirection() != direction1) {
                    snake1.setSnakeDirection(direction1);
                }
                if (snake2.getSnakeDirection() != direction2) {
                    snake2.setSnakeDirection(direction2);
                }

                // Move the snakes
                snake1.move();
                snake2.move();

                // Check if they ate the apple
                Point oldApple = new Point(apple.getX(), apple.getY());
                snake1.checkAppleCollision(apple);
                snake2.checkAppleCollision(apple);
                // Check if they have collided with something
                if (snake1.checkCollision()) {
                    oos1.writeObject(0);
                    oos2.writeObject(0);
                    sendScores(false, true);

                    gameRunning = false;
                }
                if (snake2.checkCollision()) {
                    oos1.writeObject(0);
                    oos2.writeObject(0);
                    sendScores(true, false);

                    gameRunning = false;
                }
                if (snake1.checkCollisionWith(snake2)) {
                    oos1.writeObject(0);
                    oos2.writeObject(0);
                    sendScores(false, true);

                    gameRunning = false;
                }
                if (snake2.checkCollisionWith(snake1)) {
                    oos1.writeObject(0);
                    oos2.writeObject(0);
                    sendScores(true, false);

                    gameRunning = false;
                }

                if (gameRunning) {
                    if (oldApple.x == apple.getX() && oldApple.y == apple.getY()) { // check if apple is the last one
                        oos1.writeObject(1);
                        oos2.writeObject(1);
                        sendSnakes();
                    } else {
                        oos1.writeObject(2);
                        oos2.writeObject(2);
                        sendSnakes();
                        sendApple();
                    }
                }

                Thread.sleep(gameSpeed);
            }

            Thread.currentThread().interrupt();

        } catch (IOException | ClassNotFoundException exc) {
            LOG.error("An error occurred.", exc);
        } catch (InterruptedException e) {
            LOG.info("Game finished.");
        }
    }
}
