package com.circle.bulk.helper.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.circle.bulk.helper.service.BulkBookSheetName.PRODUCT_COVER_IMAGE;

@Service
public class ProductCoverSheetGeneratorImpl implements ProductCoverSheetGenerator {

  @Override
  public void create(Workbook workbook, Set<Product> products) {
    Sheet sheet = workbook.createSheet(PRODUCT_COVER_IMAGE.name());
    Set<Product> productsWithoutAssets = new HashSet<>();
    int rowIndex = 0;
    for (Product p : products) {
      if (p.hasCoverImage()) {
        Row row = sheet.createRow(rowIndex++);
        row.createCell(0).setCellValue(p.getName());
        row.createCell(1).setCellValue(p.getCoverPath());
      } else productsWithoutAssets.add(p);
    }

    for (Product p : productsWithoutAssets) {
      Row row = sheet.createRow(rowIndex++);
      row.createCell(0).setCellValue(p.getName());
    }
  }
}
