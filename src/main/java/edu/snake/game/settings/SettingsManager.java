package edu.snake.game.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class SettingsManager {

  private static final Logger LOG = LoggerFactory.getLogger(SettingsManager.class);
  private static final String SETTINGS_FILE = "/settings.json";

  private final ObjectMapper objectMapper;

  public SettingsManager() {
    this.objectMapper = new ObjectMapper();
  }

  public Settings getSettings() {
    try (InputStream inputStream = SettingsManager.class.getResourceAsStream(SETTINGS_FILE)) {
      Settings settings = objectMapper.readValue(inputStream, Settings.class);
      LOG.info("Settings loaded successfully.");
      return settings;
    } catch (IOException exc) {
      LOG.error("Failed loading settings.", exc);
      return null;
    }
  }

  public void saveSettings(Settings settings) {
    try {
      URL settingFileURL = SettingsManager.class.getResource(SETTINGS_FILE);
      File settingsFile = new File(settingFileURL.toURI());
      objectMapper.writeValue(settingsFile, settings);
      LOG.info("Settings saved successfully. {}", settings.getSpeed());
    } catch (IOException | URISyntaxException exc) {
      LOG.error("Failed saving settings.", exc);
    }
  }
}
