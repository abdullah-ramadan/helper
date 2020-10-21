package com.circle.bulk.helper.util;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilesUtils {

  private static final Logger log = LoggerFactory.getLogger(FilesUtils.class);

  public static final String pathSeparator = "/";

  public static byte[] workBookToByteArray(Workbook workbook) {
    byte[] documentContent = null;
    try (ByteArrayOutputStream archivo = new ByteArrayOutputStream()) {
      workbook.write(archivo);
      documentContent = archivo.toByteArray();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return documentContent;
  }

  public static Workbook workbookFromURL(String url) {
    try {
      FileInputStream fis = new FileInputStream(new File(url));
      return new XSSFWorkbook(fis);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static boolean isFolderExist(String folderPath) {
    try {
      return new File(folderPath).exists();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public static boolean isFileExist(String filePath) {
    try {
      return new File(filePath).exists();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public static String getCoverImageFileName(String folderPath) {
    try {
      File parent = new File(folderPath);
      for (File file : parent.listFiles()) {
        if (file.isFile()) return file.getName();
      }
    } catch (Exception ex) {
    }
    return null;
    //    try (Stream<Path> paths = walk(Paths.get(folderPath))) {
    //      return paths
    //          .filter(Files::isRegularFile)
    //          .findFirst()
    //          .map(p -> p.getParent().getFileName() + "/" + p.getFileName())
    //          .orElse(null);
    //    } catch (IOException e) {
    //      return null;
    //    }
  }

  public static List<String> getAssetImagesPaths(String folderPath) {
    List<String> paths = new ArrayList<>();
    try {
      File parent = new File(folderPath);
      for (File file : parent.listFiles()) {
        if (file.isFile()) paths.add(parent.getName() + "/" + file.getName());
      }
    } catch (Exception ex) {
    }
    return paths;
    //    try (Stream<Path> paths = walk(Paths.get(folderPath))) {
    //      return paths
    //          .filter(Files::isRegularFile)
    //          .map(p -> p.getParent().getFileName() + "/" + p.getFileName())
    //          .collect(Collectors.toSet());
    //    } catch (IOException e) {
    //      return null;
    //    }
  }
}
