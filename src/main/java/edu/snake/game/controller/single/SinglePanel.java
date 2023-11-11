package edu.snake.game.controller.single;

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

public class SinglePanel extends JPanel implements GameConstants {
    private Snake snake;
    private Apple apple;
    private BufferedImage appleImage = null;
    // KeyPaused so user can't change the value of direction too fast
    private boolean keyPaused;

    public SinglePanel() {
        try {
            URL resource = SinglePanel.class.getResource("/apple.png");
            appleImage = ImageIO.read(new File(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Direction direction = snake.getSnakeDirection();
                if (!keyPaused) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP -> {
                            if (direction != Direction.UP && direction != Direction.DOWN) {
                                snake.setSnakeDirection(Direction.UP);
                                keyPaused = true;
                            }
                        }
                        case KeyEvent.VK_DOWN -> {
                            if (direction != Direction.DOWN && direction != Direction.UP) {
                                snake.setSnakeDirection(Direction.DOWN);
                                keyPaused = true;
                            }
                        }
                        case KeyEvent.VK_RIGHT -> {
                            if (direction != Direction.RIGHT && direction != Direction.LEFT) {
                                snake.setSnakeDirection(Direction.RIGHT);
                                keyPaused = true;
                            }
                        }
                        case KeyEvent.VK_LEFT -> {
                            if (direction != Direction.LEFT && direction != Direction.RIGHT) {
                                snake.setSnakeDirection(Direction.LEFT);
                                keyPaused = true;
                            }
                        }
                    }
                }
            }
        });

        setBackground(Color.PINK);
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public void setKeyPaused(boolean keyPaused) {
        this.keyPaused = keyPaused;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (snake != null && apple != null) {
            Font font = new Font("SansSerif", Font.BOLD, 20);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(font);
            g2d.setColor(Color.WHITE);
            FontMetrics fontMetrics = g2d.getFontMetrics();

            String text = "Score: " + snake.getSnakeApplesEaten();

            int x = (SCREEN_WIDTH - fontMetrics.stringWidth(text)) / 2;
            g2d.drawString(text, x, 25);

            if (appleImage != null) {
                g.drawImage(appleImage, apple.getX(), apple.getY(), BLOCK_SIZE, BLOCK_SIZE, null);
            } else {
                g.setColor(Color.WHITE);
                g.fillRect(apple.getX(), apple.getY(), BLOCK_SIZE, BLOCK_SIZE);
            }

            for (int i = 0; i < snake.getSnakeSize(); i++) {
                if (i == 0) {
                    g.setColor(new Color(0, 255, 0));
                } else {
                    g.setColor(new Color(0, 100, 0));
                }
                Point snakePart = snake.getSnakeBody().get(i);
                g.fillRect(snakePart.x, snakePart.y, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }
}
