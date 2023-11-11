package edu.snake.game.controller.multi.Client;

import edu.snake.game.model.Apple;
import edu.snake.game.GameConstants;
import edu.snake.game.MainFrame;
import edu.snake.game.model.Snake;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientController implements Runnable, GameConstants {
    private final ClientGamePanel clientGamePanel;
    private final Socket serverSocket;
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private boolean gameRunning;
    private Snake mySnake;
    private Snake enemySnake;
    private Apple apple;

    public ClientController(ClientGamePanel clientGamePanel, Socket serverSocket) throws IOException {
        this.clientGamePanel = clientGamePanel;
        this.serverSocket = serverSocket;

        oos = new ObjectOutputStream(serverSocket.getOutputStream());
        ois = new ObjectInputStream(serverSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            prepareGame();
            gameRunning = true;

            while (gameRunning) {
                // !!! Purpose: direction change using keys AND tell the server that we're ready with the job
                Direction directionChanged = clientGamePanel.getDirectionChanged();
                oos.writeObject(directionChanged);

                // Check what will the server send to us
                Integer expectation = (Integer) ois.readObject();
                switch (expectation) {
                    case 0 -> {
                        gameRunning = false;
                    }
                    case 1 -> {
                        mySnake = (Snake) ois.readObject();
                        enemySnake = (Snake) ois.readObject();
                    }
                    case 2 -> {
                        mySnake = (Snake) ois.readObject();
                        enemySnake = (Snake) ois.readObject();
                        apple = (Apple) ois.readObject();
                    }
                }

                if (gameRunning) {
                    if (expectation == 2) {
                        clientGamePanel.setApple(apple);
                    }

                    clientGamePanel.setSnakes(mySnake, enemySnake);
                    clientGamePanel.repaint();

                    clientGamePanel.setKeyPaused(false);
                }
            }

            gameOver();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Receive data from server (snake for example)
    private void prepareGame() throws IOException, ClassNotFoundException {
        mySnake = (Snake) ois.readObject();
        enemySnake = (Snake) ois.readObject();
        apple = (Apple) ois.readObject();

        clientGamePanel.setSnakes(mySnake, enemySnake);
        clientGamePanel.setDirectionChanged(mySnake.getSnakeDirection());
        clientGamePanel.setApple(apple);

        clientGamePanel.repaint();
    }

    private void gameOver() throws IOException, ClassNotFoundException {
        // Receive my score
        Integer score = (Integer) ois.readObject();
        boolean winStatus = (boolean) ois.readObject();

        MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(clientGamePanel);
        topFrame.signalGameOver(score, winStatus);
    }
}
