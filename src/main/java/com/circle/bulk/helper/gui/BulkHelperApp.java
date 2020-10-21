package com.circle.bulk.helper.gui;

import com.circle.bulk.helper.HelperApplication;
import com.circle.bulk.helper.gui.event.StageReadyEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class BulkHelperApp extends Application {

  private ConfigurableApplicationContext applicationContext;

  @Override
  public void init() {
    applicationContext = new SpringApplicationBuilder(HelperApplication.class).run();
  }

  @Override
  public void start(Stage stage) {
    applicationContext.publishEvent(new StageReadyEvent(stage));
  }

  @Override
  public void stop() {
    applicationContext.close();
    Platform.exit();
  }
}
