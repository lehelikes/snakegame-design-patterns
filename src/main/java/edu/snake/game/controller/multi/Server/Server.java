package edu.snake.game.controller.multi.Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private final ServerStatusPanel serverStatusPanel;
    private final Integer port;
    private ServerSocket serverSocket = null;
    private Socket clientSocketOne;
    private Socket clientSocketTwo;

    public Server(ServerStatusPanel serverStatusPanel, Integer port) {
        this.serverStatusPanel = serverStatusPanel;
        this.port = port;
    }

    public void stopServer() {
        try {
            clientSocketOne.close();
            clientSocketTwo.close();
            serverSocket.close();
        } catch (IOException exc) {
            LOG.error("Error while closing server socket: {}", exc.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);

            // First player connection
            clientSocketOne = serverSocket.accept();
            // update client 1 status in UI
            serverStatusPanel.updateClientStatus(1, true);
            serverStatusPanel.repaint();

            clientSocketTwo = serverSocket.accept();
            // update client 2 status in UI
            serverStatusPanel.updateClientStatus(2, true);
            serverStatusPanel.repaint();

            ServerGameController serverGameController = new ServerGameController(clientSocketOne, clientSocketTwo);
            Thread serverGameThread = new Thread(serverGameController);
            serverGameThread.start();

            serverStatusPanel.updateServerStatus("running");
            serverStatusPanel.repaint();

            // Stop when the game has been finished
            serverGameThread.join();
            stopServer();
            serverStatusPanel.updateServerStatus("stopped");
            serverStatusPanel.updateClientStatus(1, false);
            serverStatusPanel.updateClientStatus(2, false);
            serverStatusPanel.repaint();

        } catch (IOException | InterruptedException exc) {
            LOG.error("Error while running server: {}", exc.getMessage());
        }
    }
}
