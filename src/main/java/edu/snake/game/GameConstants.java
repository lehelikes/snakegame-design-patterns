package edu.snake.game;

public interface GameConstants {
    enum Direction {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }

    int SCREEN_WIDTH = 1024;
    int SCREEN_HEIGHT = 768;
    int BLOCK_SIZE = 40;
    int ALL_BLOCKS = (SCREEN_WIDTH * SCREEN_HEIGHT) / BLOCK_SIZE;
}
