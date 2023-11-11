package edu.snake.game.controller.multi.Server;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ServerFrame extends JFrame {
    private final ServerPanel serverPanel;
    private Integer portDesired;

    public ServerFrame() {
        serverPanel = new ServerPanel();

        setContentPane(serverPanel);
        revalidate();

        setBounds(0, 0, 300, 200);
        setTitle("Snake Ultimate Reloaded - Server");
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void changePane(String paneName) {
        if (paneName.equals("main")) {
            ServerStatusPanel serverStatusPanel = new ServerStatusPanel();
            setContentPane(serverStatusPanel);
            revalidate();

            Server server = new Server(serverStatusPanel, portDesired);
            Thread serverThread = new Thread(server);
            serverThread.start();
        }
    }

    public void changePane(String paneName, Integer port) {
        if (paneName.equals("status")) {
            portDesired = port;
            ServerStatusPanel serverStatusPanel = new ServerStatusPanel();
            setContentPane(serverStatusPanel);
            revalidate();
            serverStatusPanel.requestFocus();

            Server server = new Server(serverStatusPanel, port);
            Thread serverThread = new Thread(server);
            serverThread.start();
        }
    }
}
