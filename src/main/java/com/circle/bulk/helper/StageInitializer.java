package com.circle.bulk.helper;

import com.circle.bulk.helper.gui.event.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

  @Value("classpath:/main.fxml")
  private Resource resource;

  private final String appTitle;
  private ApplicationContext applicationContext;

  public StageInitializer(
      @Value("${bulk.helper.app-title}") String helperTitle,
      ApplicationContext applicationContext) {
    this.appTitle = helperTitle;
    this.applicationContext = applicationContext;
  }

  @Override
  public void onApplicationEvent(StageReadyEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(resource.getURL());
      loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
      Parent parent = loader.load();
      Stage stage = event.getStage();
      stage.setTitle(appTitle);
      stage.setScene(new Scene(parent, 600, 300));
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
