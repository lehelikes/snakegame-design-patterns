package edu.snake.game;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverPanel extends JPanel implements ActionListener, GameConstants {
    private final JButton returnMenu;
    private final int score;
    private boolean gameWon;

    public GameOverPanel(int score, boolean gameWon) {
        this.score = score;
        this.gameWon = gameWon;

        returnMenu = new JButton("Main menu");
        returnMenu.setForeground(Color.BLACK);
        returnMenu.setBackground(Color.WHITE);
        returnMenu.addActionListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.weighty = 1;

        gbc.insets = new Insets(0, 0, 200, 0);
        add(returnMenu, gbc);

        setBackground(Color.BLACK);
    }

    private void draw(Graphics g) {
        Font font = new Font("SansSerif", Font.BOLD, 50);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(font);
        FontMetrics fontMetrics = g2d.getFontMetrics();

        String text1;
        if (gameWon) {
            g2d.setColor(Color.GREEN);
            text1 = "YOU WON";
        } else {
            g2d.setColor(Color.WHITE);
            text1 = "GAME OVER";
        }
        String text2 = "Your last score was: " + score;

        int x = (SCREEN_WIDTH - fontMetrics.stringWidth(text1)) / 2;
        int y = ((SCREEN_HEIGHT - fontMetrics.getHeight()) / 2);
        g2d.drawString(text1, x, y);

        Font font2 = new Font("SansSerif", Font.BOLD, 25);
        g2d.setFont(font2);
        g2d.setColor(Color.WHITE);
        fontMetrics = g2d.getFontMetrics();

        x = (SCREEN_WIDTH - fontMetrics.stringWidth(text2)) / 2;
        y = ((SCREEN_HEIGHT - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();
        g2d.drawString(text2, x, y + 50);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnMenu) {
            MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.changePane("main");
        }
    }
}
