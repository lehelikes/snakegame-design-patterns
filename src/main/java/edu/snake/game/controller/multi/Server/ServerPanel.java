package edu.snake.game.controller.multi.Server;

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

public class ServerPanel extends JPanel implements ActionListener {
    private final JSpinner portSpinner;
    private final JButton startButton;

    public ServerPanel() {
        JLabel portLabel = new JLabel("Please enter port to start:");
        portLabel.setForeground(Color.BLACK);
        SpinnerModel model = new SpinnerNumberModel(5000, 1, 10000, 1);
        portSpinner = new JSpinner(model);

        startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(75, 50));
        startButton.setForeground(Color.BLACK);
        startButton.setBackground(Color.WHITE);
        startButton.addActionListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        add(portLabel, gbc);
        add(portSpinner, gbc);

        gbc.gridy = 1;
        add(startButton, gbc);

        setBackground(Color.BLACK);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            ServerFrame topFrame = (ServerFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.changePane("status", (Integer) portSpinner.getValue());
        }
    }
}
