package edu.snake.game.model;

import edu.snake.game.GameConstants;

import java.awt.Point;
import java.io.Serializable;
import java.util.Random;

public class Apple implements GameConstants, Serializable {
    private final Point position;
    private final Random rand;

    public Apple() {
        position = new Point();
        rand = new Random();

        respawn();
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public void respawn() {
        position.x = rand.nextInt(SCREEN_WIDTH / BLOCK_SIZE) * BLOCK_SIZE;
        position.y = rand.nextInt(SCREEN_HEIGHT / BLOCK_SIZE) * BLOCK_SIZE;
    }

}

