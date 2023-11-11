package edu.snake.game.controller;

public abstract class GameController implements Runnable {

  @Override
  public void run() {
    prepareGame();
    runGame();
  }

  protected abstract void prepareGame();
  protected abstract void runGame();
}
