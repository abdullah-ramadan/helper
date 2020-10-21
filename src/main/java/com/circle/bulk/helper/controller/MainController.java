package com.circle.bulk.helper.controller;

import com.circle.bulk.helper.service.BulkHelperService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class MainController {

  @FXML private TextField bulkSheetPathTxtField;
  @FXML private TextField productsAssetPathTxtField;
  @FXML private Button startBtn;
  @FXML private TextField updatedBulkSheetPathTxtField;
  @Autowired private BulkHelperService bulkHelperService;

  private String bulkSheetPath;
  private String bulkSheetName;

  public void bulkSheetPathBtnAction() {
    FileChooser fileChooser = new FileChooser();
    File file = fileChooser.showOpenDialog(startBtn.getContextMenu());
    if (file != null) {
      setBulkSheetNameAndPath(file);
      bulkSheetPathTxtField.setText((bulkSheetPath + "/" + bulkSheetName).replace("//", "/"));
    }
  }

  private void setBulkSheetNameAndPath(File file) {
    bulkSheetPath = file.getParentFile().getAbsolutePath().replace('\\', '/');
    bulkSheetName = file.getName();
  }

  public void productsAssetPathBtnAction() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    File file = directoryChooser.showDialog(startBtn.getContextMenu());
    if (file != null) {
      productsAssetPathTxtField.setText(file.getAbsolutePath().replace('\\', '/'));
    }
  }

  public void startAction() {
    startBtn.getParent().setDisable(true);
    byte[] bookAsBytes =
        bulkHelperService.linkProductToItsAssets(
            productsAssetPathTxtField.getText(), bulkSheetPathTxtField.getText());
    if (bulkSheetPath == null || bulkSheetName == null) {
      File file = new File(bulkSheetPathTxtField.getText());
      setBulkSheetNameAndPath(file);
    }
    write(bookAsBytes);
    startBtn.getParent().setDisable(false);
    new Alert(Alert.AlertType.INFORMATION, "Done", ButtonType.OK).show();
  }

  private void write(byte[] book) {
    String filePath;
    File file;
    if (bulkSheetPath == null || bulkSheetPath.trim().isEmpty()) {
      DirectoryChooser directoryChooser = new DirectoryChooser();
      file = directoryChooser.showDialog(startBtn.getContextMenu());
      if (file == null) return;
      filePath = file.getAbsolutePath().replace('\\', '/');
    } else filePath = (bulkSheetPath + "/withAssets_" + bulkSheetName).replace("//", "/");

    updatedBulkSheetPathTxtField.setText(filePath);
    file = new File(filePath);
    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(book);
      fos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void generateHtmlFile() {
    String filePath = updatedBulkSheetPathTxtField.getText();
    String assetsFolderPath = productsAssetPathTxtField.getText();
    String html = bulkHelperService.generateHtmlTemplate(assetsFolderPath, filePath);
    write(html);
    new Alert(Alert.AlertType.INFORMATION, "Done", ButtonType.OK).show();
  }

  private void write(String html) {
    String filePath;
    File file;
    if (bulkSheetPath == null || bulkSheetPath.trim().isEmpty()) {
      DirectoryChooser directoryChooser = new DirectoryChooser();
      file = directoryChooser.showDialog(startBtn.getContextMenu());
      if (file == null) return;
      filePath = file.getAbsolutePath().replace('\\', '/');
    } else {
      int dotIndex = bulkSheetName.indexOf('.');
      filePath =
          (bulkSheetPath + "/").replace("//", "/")
              + ((dotIndex == -1) ? bulkSheetName : bulkSheetName.substring(0, dotIndex))
              + "_products.html";
    }

    file = new File(filePath);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write(html);
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
