package edu.snake.game.settings;

import edu.snake.game.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel implements ActionListener {

    private static final Logger LOG = LoggerFactory.getLogger(SettingsPanel.class);

    private final JButton backToMenuButton;
    private final JButton saveButton;
    private final JSlider speedSlider;
    private final JLabel speedLabel;
    private final SettingsManager settingsManager;
    private final Settings settings;

    public SettingsPanel() {
        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(75, 50));
        saveButton.setForeground(Color.BLACK);
        saveButton.setBackground(Color.WHITE);
        saveButton.addActionListener(this);
        backToMenuButton = new JButton("Back");
        backToMenuButton.setPreferredSize(new Dimension(75, 50));
        backToMenuButton.setOpaque(true);
        backToMenuButton.setBorderPainted(false);
        backToMenuButton.setForeground(Color.BLACK);
        backToMenuButton.setBackground(Color.WHITE);
        backToMenuButton.addActionListener(this);

        settingsManager = SettingsManagerFactory.getSettingsManager();
        settings = settingsManager.getSettings();

        speedSlider = new JSlider(25, 225, settings.getSpeed());
        speedSlider.setPaintTrack(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        // set spacing
        speedSlider.setMajorTickSpacing(50);
        speedSlider.setMinorTickSpacing(5);

        JLabel speedAdditionalInfoLabel = new JLabel("Lower speed value means faster game because of lower delay between moves.");
        speedAdditionalInfoLabel.setForeground(Color.WHITE);
        speedLabel = new JLabel("New value for speed = " + speedSlider.getValue());
        speedLabel.setForeground(Color.WHITE);
        speedSlider.addChangeListener(e -> speedLabel.setText("Current value of speed is =" + speedSlider.getValue()));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(25, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Element adding
        add(speedAdditionalInfoLabel, gbc);
        add(speedLabel, gbc);
        add(speedSlider, gbc);

        gbc.gridy = 1;
        add(saveButton, gbc);

        gbc.gridx = 1;
        add(backToMenuButton, gbc);

        setBackground(Color.BLACK);
    }

    private void saveSettings() {
        if (settings.getSpeed() != speedSlider.getValue()) {
            settingsManager.saveSettings(new Settings(speedSlider.getValue()));
            LOG.info("Speed changed to {}", speedSlider.getValue());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backToMenuButton) {
            MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.changePane("main");
        } else if (e.getSource() == saveButton) {
            saveSettings();
        }
    }
}
