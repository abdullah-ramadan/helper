package com.circle.bulk.helper.service;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;

public interface ProductVariationsMapParser {

  Map<String, Product> parseProducts(Sheet productsCoverSheet);

  void parseProductsVariation(Map<String, Product> products, Sheet variationsAssetsSheet);

  //  Set<Product> parseProductsWithoutImages(
  //      Map<String, Product> products, Sheet variationsAssetsSheet);
}
