package com.circle.bulk.helper;

import com.circle.bulk.helper.gui.BulkHelperApp;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelperApplication {

  public static void main(String[] args) {
    Application.launch(BulkHelperApp.class, args);
  }
}
