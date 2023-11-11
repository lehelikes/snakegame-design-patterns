package edu.snake.game.controller.multi.Client;

import edu.snake.game.model.Apple;
import edu.snake.game.GameConstants;
import edu.snake.game.model.Snake;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class ClientGamePanel extends JPanel implements GameConstants {
    private Snake mySnake;
    private Snake enemySnake;
    private Apple apple;
    private BufferedImage appleImage = null;
    private boolean keyPaused = false;
    private Direction directionChanged;

    public ClientGamePanel() {
        try {
            URL resource = this.getClass().getClassLoader().getResource("apple.png");
            appleImage = ImageIO.read(new File(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                GameConstants.Direction direction = mySnake.getSnakeDirection();
                if (!keyPaused) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP -> {
                            if (direction != GameConstants.Direction.UP && direction != GameConstants.Direction.DOWN) {
                                directionChanged = Direction.UP;
                                keyPaused = true;
                            }
                        }
                        case KeyEvent.VK_DOWN -> {
                            if (direction != GameConstants.Direction.DOWN && direction != GameConstants.Direction.UP) {
                                directionChanged = Direction.DOWN;
                                keyPaused = true;
                            }
                        }
                        case KeyEvent.VK_RIGHT -> {
                            if (direction != GameConstants.Direction.RIGHT && direction != GameConstants.Direction.LEFT) {
                                directionChanged = Direction.RIGHT;
                                keyPaused = true;
                            }
                        }
                        case KeyEvent.VK_LEFT -> {
                            if (direction != GameConstants.Direction.LEFT && direction != GameConstants.Direction.RIGHT) {
                                directionChanged = Direction.LEFT;
                                keyPaused = true;
                            }
                        }
                    }
                }
            }
        });

        setBackground(Color.BLACK);
    }

    public void setSnakes(Snake mySnake, Snake enemySnake) {
        this.mySnake = mySnake;
        this.enemySnake = enemySnake;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public void setKeyPaused(boolean state) {
        keyPaused = state;
    }

    public Direction getDirectionChanged() {
        return directionChanged;
    }

    public void setDirectionChanged(Direction directionChanged) {
        this.directionChanged = directionChanged;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (mySnake != null && enemySnake != null && apple != null) {
            Font font = new Font("SansSerif", Font.BOLD, 20);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(font);
            g2d.setColor(Color.WHITE);
            FontMetrics fontMetrics = g2d.getFontMetrics();

            String text = "Score: " + mySnake.getSnakeApplesEaten(); // TODO

            int x = (SCREEN_WIDTH - fontMetrics.stringWidth(text)) / 2;
            g2d.drawString(text, x, 25);

            if (appleImage != null) {
                g.drawImage(appleImage, apple.getX(), apple.getY(), BLOCK_SIZE, BLOCK_SIZE, null);
            } else {
                g.setColor(Color.WHITE);
                g.fillRect(apple.getX(), apple.getY(), BLOCK_SIZE, BLOCK_SIZE);
            }

            for (int i = 0; i < mySnake.getSnakeSize(); i++) {
                if (i == 0) {
                    g.setColor(new Color(0, 255, 0));
                } else {
                    g.setColor(new Color(0, 100, 0));
                }
                Point snakePart = mySnake.getSnakeBody().get(i);
                g.fillRect(snakePart.x, snakePart.y, BLOCK_SIZE, BLOCK_SIZE);
            }

            for (int i = 0; i < enemySnake.getSnakeSize(); i++) {
                if (i == 0) {
                    g.setColor(new Color(255, 0, 0));
                } else {
                    g.setColor(new Color(100, 0, 0));
                }
                Point snakePart = enemySnake.getSnakeBody().get(i);
                g.fillRect(snakePart.x, snakePart.y, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }
}
