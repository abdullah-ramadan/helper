package com.circle.bulk.helper.service;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.Set;

public interface VariationAssetsSheetGenerator {

  void create(Workbook workbook, Set<Product> products);
}
