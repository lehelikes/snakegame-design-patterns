package edu.snake.game;

import edu.snake.game.controller.single.SinglePanel;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class MainPanel extends JPanel implements ActionListener, GameConstants {
    private final JButton singleStartButton;
    private final JButton multiStartButton;
    private final JButton settingsButton;
    private BufferedImage logoImage = null;

    public MainPanel() {
        // Button init
        singleStartButton = new JButton("Single Player");
        singleStartButton.setPreferredSize(new Dimension(150, 50));
        singleStartButton.setForeground(Color.BLACK);
        singleStartButton.setBackground(Color.WHITE);
        singleStartButton.addActionListener(this);
        multiStartButton = new JButton("Multi Player");
        multiStartButton.setPreferredSize(new Dimension(150, 50));
        multiStartButton.setForeground(Color.BLACK);
        multiStartButton.setBackground(Color.WHITE);
        multiStartButton.addActionListener(this);
        settingsButton = new JButton("Settings");
        settingsButton.setPreferredSize(new Dimension(150, 50));
        settingsButton.setForeground(Color.BLACK);
        settingsButton.setBackground(Color.WHITE);
        settingsButton.addActionListener(this);

        try {
            URL resource = SinglePanel.class.getResource("/logo.png");
            logoImage = ImageIO.read(new File(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        // GridBagLayout settings
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(25, 10, 0, 10);

        // Logo
        gbc.gridx = 0; gbc.gridy = 0;


        // Button adding
        gbc.gridy = 1;
        add(singleStartButton, gbc);

        gbc.gridy = 2;
        add(multiStartButton, gbc);

        gbc.gridy = 3;
        add(settingsButton, gbc);

        setBackground(Color.BLACK);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (logoImage != null) {
            g.drawImage(logoImage, 350, 50, 300, 200, null);
        }
    }

    // Button actions
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get JFrame object
        MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);

        if (e.getSource() == singleStartButton) {
            topFrame.changePane("single");
        } else if (e.getSource() == multiStartButton) {
            topFrame.changePane("connectMulti");
        } else if (e.getSource() == settingsButton) {
            topFrame.changePane("settings");
        }
    }
}
