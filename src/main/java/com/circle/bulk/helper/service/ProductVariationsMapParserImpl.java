package com.circle.bulk.helper.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.circle.bulk.helper.util.CellValueReaderUtil.cellStringValueWhateverItsType;
import static com.circle.bulk.helper.util.CellValueReaderUtil.isEmptyRow;

@Service
public class ProductVariationsMapParserImpl implements ProductVariationsMapParser {

  @Override
  public Map<String, Product> parseProducts(Sheet productsCoverSheet) {
    Map<String, Product> products = new HashMap<>();
    for (Row row : productsCoverSheet) {
      if (isEmptyRow(row)) break;
      String productName = cellStringValueWhateverItsType(row.getCell(0));
      if (productName != null) productName = productName.trim();
      String productCoverImage = cellStringValueWhateverItsType(row.getCell(1));
      products.put(productName, Product.of(productName, productCoverImage));
    }
    return products;
  }

  @Override
  public void parseProductsVariation(Map<String, Product> products, Sheet variationsAssetsSheet) {
    for (Row row : variationsAssetsSheet) {
      if (isEmptyRow(row)) break;
      String productName = cellStringValueWhateverItsType(row.getCell(0));
      if (productName != null) productName = productName.trim();
      String sku = cellStringValueWhateverItsType(row.getCell(1));
      if (sku != null) sku = sku.trim();
      products.get(productName).addVariation(Variation.of(sku, getAssetsPaths(row)));
    }
  }

  private List<String> getAssetsPaths(Row row) {
    List<String> assetsPaths = new ArrayList<>();
    for (Cell cell : row)
      if (cell.getColumnIndex() > 1) assetsPaths.add(cellStringValueWhateverItsType(cell));
    return assetsPaths;
  }

  //  @Override
  //  public Set<Product> parseProductsWithoutImages(
  //      Map<String, Product> products, Sheet variationsAssetsSheet) {
  //    Set<Product> productsWithoutAsset = new HashSet<>();
  //    Iterator<Row> iterator = variationsAssetsSheet.iterator();
  //    if (iterator.hasNext()) iterator.next();
  //    while (iterator.hasNext()) {
  //      Row row = iterator.next();
  //      if (isEmptyRow(row)) break;
  //      String productName = cellStringValueWhateverItsType(row.getCell(0));
  //      if (productName != null) productName = productName.trim();
  //      if (!products.containsKey(productName))
  //        productsWithoutAsset.add(Product.of(productName, null));
  //    }
  //    return productsWithoutAsset;
  //  }
}
