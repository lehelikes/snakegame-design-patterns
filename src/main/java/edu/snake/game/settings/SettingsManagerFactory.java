package edu.snake.game.settings;

public abstract class SettingsManagerFactory {

  public static SettingsManager settingsManager;

  public static SettingsManager getSettingsManager() {
    if (settingsManager == null) {
      settingsManager = new SettingsManager();
    }
    return settingsManager;
  }
}
