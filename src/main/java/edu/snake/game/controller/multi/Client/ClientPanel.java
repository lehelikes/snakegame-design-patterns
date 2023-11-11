package edu.snake.game.controller.multi.Client;

import edu.snake.game.MainFrame;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientPanel extends JPanel implements ActionListener {
    private final JSpinner portSpinner;
    private final JButton connectButton;
    private final JButton backButton;
    private InetAddress host;
    private Socket serverSocket;

    public ClientPanel() {
        JLabel portLabel = new JLabel("Please enter port to connect:");
        portLabel.setForeground(Color.BLACK);
        SpinnerModel model = new SpinnerNumberModel(5000, 1, 10000, 1);
        portSpinner = new JSpinner(model);

        connectButton = new JButton("Connect");
        connectButton.setPreferredSize(new Dimension(100, 50));
        connectButton.setForeground(Color.BLACK);
        connectButton.setBackground(Color.WHITE);
        connectButton.addActionListener(this);
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(75, 50));
        backButton.setForeground(Color.BLACK);
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        add(portLabel, gbc);
        add(portSpinner, gbc);

        gbc.gridy = 1;
        add(connectButton, gbc);
        add(backButton, gbc);

        setBackground(Color.BLACK);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connectButton) {
            try {
                host = InetAddress.getLocalHost();
                serverSocket = new Socket(host.getHostName(), (Integer) portSpinner.getValue());

                MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.changePaneMultiGame(serverSocket);
            } catch (IOException unknownHostException) {
                System.out.println("Could not connect to the server.");
                unknownHostException.printStackTrace();
            }
        } else if (e.getSource() == backButton) {
            MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.changePane("main");
        }
    }
}
