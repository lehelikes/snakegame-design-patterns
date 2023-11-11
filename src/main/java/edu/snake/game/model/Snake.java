package edu.snake.game.model;

import edu.snake.game.GameConstants;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Snake implements GameConstants, Serializable {
    private final ArrayList<Point> snakeBody;
    private int snakeSize;
    private int snakeApplesEaten;
    private Direction snakeDirection;
    private final Random rand;

    public Snake() {
        // Initial parameters for snake
        snakeBody = new ArrayList<>(ALL_BLOCKS / BLOCK_SIZE);
        snakeSize = 3;
        snakeApplesEaten = 0;
        snakeDirection = Direction.RIGHT;
        rand = new Random();

        // Spawn the snake - create it's body
        Point startPos;
        do  {
            startPos = new Point(rand.nextInt((SCREEN_WIDTH / 2) / BLOCK_SIZE) * BLOCK_SIZE,
                    rand.nextInt(SCREEN_HEIGHT / BLOCK_SIZE) * BLOCK_SIZE);
            // Check starting collision
        } while (startPos.x < 0 || startPos.x > SCREEN_WIDTH || startPos.y < 0 || startPos.y > SCREEN_HEIGHT);

        snakeBody.add(new Point(startPos.x + 2 * BLOCK_SIZE, startPos.y));
        snakeBody.add(new Point(startPos.x + BLOCK_SIZE, startPos.y));
        snakeBody.add(new Point(startPos.x, startPos.y));
    }

    public ArrayList<Point> getSnakeBody() {
        return snakeBody;
    }

    public int getSnakeSize() {
        return snakeSize;
    }

    public int getSnakeApplesEaten() {
        return snakeApplesEaten;
    }

    public Direction getSnakeDirection() {
        return snakeDirection;
    }

    public void setSnakeDirection(Direction direction) {
        this.snakeDirection = direction;
    }

    public void move() {
        for (int i = snakeSize - 1; i > 0; i--) {
            snakeBody.set(i, snakeBody.get(i-1));
        }

        switch (snakeDirection) {
            case UP -> {
                Point temp = snakeBody.get(0);
                int new_x = temp.x;
                int new_y = temp.y;
                snakeBody.set(0, new Point(new_x, new_y - BLOCK_SIZE));
            }
            case DOWN -> {
                Point temp = snakeBody.get(0);
                int new_x = temp.x;
                int new_y = temp.y;
                snakeBody.set(0, new Point(new_x, new_y + BLOCK_SIZE));
            }
            case RIGHT -> {
                Point temp = snakeBody.get(0);
                int new_x = temp.x;
                int new_y = temp.y;
                snakeBody.set(0, new Point(new_x + BLOCK_SIZE, new_y));
            }
            case LEFT -> {
                Point temp = snakeBody.get(0);
                int new_x = temp.x;
                int new_y = temp.y;
                snakeBody.set(0, new Point(new_x - BLOCK_SIZE, new_y));
            }
        }
    }

    public void grow() {
        snakeBody.add(new Point(snakeBody.get(snakeSize - 1)));

        snakeSize++;
        snakeApplesEaten++;
    }

    public boolean checkCollision() {
        Point check = snakeBody.get(0);

        if (check.x < 0 || check.x > SCREEN_WIDTH || check.y < 0 || check.y > SCREEN_HEIGHT) {
            return true;
        }

        for (int i = 1; i < snakeSize; i++) {
            Point temp = snakeBody.get(i);
            if (check.x == temp.x && check.y == temp.y) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCollisionWith(Snake enemySnake) {
        Point check = snakeBody.get(0);

        for (int i = 0; i < enemySnake.getSnakeSize(); i++) {
            Point temp = enemySnake.getSnakeBody().get(i);
            if (check.x == temp.x && check.y == temp.y) {
                return true;
            }
        }
        return false;
    }

    public void checkAppleCollision(Apple apple) {
        Point check = snakeBody.get(0);

        // If the snake has eaten the apple (it's head is in the apple's position)
        if (check.x == apple.getX() && check.y == apple.getY()) {
            apple.respawn();
            this.grow();
        }
    }

}
