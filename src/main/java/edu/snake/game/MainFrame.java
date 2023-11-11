package edu.snake.game;

import edu.snake.game.controller.multi.Client.ClientController;
import edu.snake.game.controller.multi.Client.ClientGamePanel;
import edu.snake.game.controller.multi.Client.ClientPanel;
import edu.snake.game.settings.SettingsPanel;
import edu.snake.game.controller.single.SingleController;
import edu.snake.game.controller.single.SinglePanel;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.io.IOException;
import java.net.Socket;

public class MainFrame extends JFrame {
    private final MainPanel mainPanel;

    public MainFrame() {
        mainPanel = new MainPanel();

        setContentPane(mainPanel);
        revalidate();

        setBounds(0, 0, 1024, 768);
        setTitle("Snake Ultimate Reloaded");
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        System.out.println(getContentPane().getWidth() + " " + getContentPane().getHeight());
    }

    public void changePane(String paneName) {
        switch (paneName) {
            case "main" -> {
                setContentPane(mainPanel);
                revalidate();
            }
            case "single" -> {
                SinglePanel singlePanel = new SinglePanel();
                setContentPane(singlePanel);
                revalidate();
                singlePanel.requestFocus();
                SingleController singleController = new SingleController(singlePanel);
                Thread newSingleGame = new Thread(singleController);
                newSingleGame.start();
            }
            case "connectMulti" -> {
                ClientPanel clientPanel = new ClientPanel();
                setContentPane(clientPanel);
                revalidate();
            }
            case "settings" -> {
                SettingsPanel settingsPanel = new SettingsPanel();
                setContentPane(settingsPanel);
                revalidate();
            }
        }
    }

    public void signalGameOver(int score, boolean gameWon) {
        GameOverPanel gameOverPanel = new GameOverPanel(score, gameWon);
        setContentPane(gameOverPanel);
        revalidate();
    }

    public void changePaneMultiGame(Socket socket) throws IOException {
        ClientGamePanel clientGamePanel = new ClientGamePanel();
        setContentPane(clientGamePanel);
        revalidate();
        clientGamePanel.requestFocus();

        ClientController clientController = new ClientController(clientGamePanel, socket);
        Thread newMultiGame = new Thread(clientController);
        newMultiGame.start();
    }
}
