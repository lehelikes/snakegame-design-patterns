package edu.snake.game.settings;

import java.io.Serializable;

public class Settings implements Serializable {
    private Integer speed;

    public Settings() {
        this(100);
    }

    public Settings(Integer speed) {
        this.speed = speed;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
}
