package com.circle.bulk.helper.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.circle.bulk.helper.service.BulkBookSheetName.VARIATIONS_IMAGES;

@Service
public class VariationAssetsSheetGeneratorImpl implements VariationAssetsSheetGenerator {

  @Override
  public void create(Workbook workbook, Set<Product> products) {
    Sheet sheet = workbook.createSheet(VARIATIONS_IMAGES.name());
    Set<Product> productsWithoutAssets = new HashSet<>();
    int rowIndex = 0;
    for (Product p : products) {
      if (p.hasCoverImage()) {
        writeSkuInfo(p, rowIndex, sheet);
        rowIndex += p.getVariationsCount();
      } else productsWithoutAssets.add(p);
    }

    for (Product p : productsWithoutAssets) {
      writeSkuInfo(p, rowIndex, sheet);
      rowIndex += p.getVariationsCount();
    }
  }

  private void writeSkuInfo(Product p, int rowIndex, Sheet sheet) {
    if (p.getVariations() != null && !p.getVariations().isEmpty()) {
      for (Variation v : p.getVariations()) {
        Row row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue(p.getName());
        row.createCell(1).setCellValue(v.getSku());
        writeAssetsPaths(row, v.getAssetsPaths());
      }
    }
  }

  private void writeAssetsPaths(Row row, List<String> paths) {
    if (paths == null) return;
    AtomicInteger cellIndex = new AtomicInteger(2);
    paths.forEach(path -> row.createCell(cellIndex.getAndIncrement()).setCellValue(path));
  }
}
