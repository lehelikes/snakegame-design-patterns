package edu.snake.game.controller.multi.Server;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerStatusPanel extends JPanel implements ActionListener {
    private final JLabel serverStatus;
    private final JLabel clientStatusOne;
    private final JLabel clientStatusTwo;
    private final JButton restartButton;

    public ServerStatusPanel() {
        serverStatus = new JLabel("Server: Waiting for connections");
        serverStatus.setForeground(Color.YELLOW);
        clientStatusOne = new JLabel("Client 1: Not connected");
        clientStatusOne.setForeground(Color.WHITE);
        clientStatusTwo = new JLabel("Client 2: Not connected");
        clientStatusTwo.setForeground(Color.WHITE);
        restartButton = new JButton("Restart server");
        restartButton.addActionListener(this);
        restartButton.setEnabled(false);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        add(serverStatus, gbc);
        gbc.gridy = 1;
        add(clientStatusOne, gbc);
        gbc.gridy = 2;
        add(clientStatusTwo, gbc);
        gbc.gridy = 3;
        add(restartButton, gbc);

        setBackground(Color.BLACK);
    }

    public void updateClientStatus(Integer clientNr, boolean status) {
        switch (clientNr) {
            case 1 -> {
                if (status) {
                    clientStatusOne.setText("Client 1: Connected");
                    clientStatusOne.setForeground(Color.GREEN);
                } else {
                    clientStatusOne.setText("Client 1: Disconnected");
                    clientStatusOne.setForeground(Color.RED);
                }
            }
            case 2 -> {
                if (status) {
                    clientStatusTwo.setText("Client 2: Connected");
                    clientStatusTwo.setForeground(Color.GREEN);
                } else {
                    clientStatusTwo.setText("Client 2: Disconnected");
                    clientStatusTwo.setForeground(Color.RED);
                }
            }
        }
    }

    public void updateServerStatus(String status) {
        if (status.equals("running")) {
            serverStatus.setText("Server: Running");
            serverStatus.setForeground(Color.GREEN);
        } else if (status.equals("stopped")) {
            serverStatus.setText("Server: Stopped");
            serverStatus.setForeground(Color.RED);
            restartButton.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restartButton) {
            System.out.println("LOL");
            ServerFrame topFrame = (ServerFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.changePane("main");
        }
    }
}
