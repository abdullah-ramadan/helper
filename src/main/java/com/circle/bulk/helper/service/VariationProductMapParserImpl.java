package com.circle.bulk.helper.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.circle.bulk.helper.service.VariationsSheetColumn.PRODUCT;
import static com.circle.bulk.helper.service.VariationsSheetColumn.SKU;
import static com.circle.bulk.helper.util.CellValueReaderUtil.cellStringValueWhateverItsType;
import static com.circle.bulk.helper.util.CellValueReaderUtil.isEmptyRow;

@Service
public class VariationProductMapParserImpl implements VariationProductMapParser {

  @Override
  public Set<Product> parse(Sheet sheet) {
    Map<String, Product> products = new HashMap<>();
    Iterator<Row> iterator = sheet.iterator();
    if (iterator.hasNext()) iterator.next();
    while (iterator.hasNext()) {
      Row row = iterator.next();
      if (isEmptyRow(row)) break;
      addSkuInfo(row, products);
    }
    return new HashSet<>(products.values());
  }

  private void addSkuInfo(Row row, Map<String, Product> products) {
    String sku = cellStringValueWhateverItsType(row.getCell(SKU.getIndex()));
    String product = cellStringValueWhateverItsType(row.getCell(PRODUCT.getIndex()));
    if (sku != null) sku = sku.trim();
    if (product != null) product = product.trim();
    if (sku != null && !sku.isEmpty() && product != null && !product.isEmpty())
      addSkuInfo(sku, product, products);
  }

  private void addSkuInfo(String sku, String product, Map<String, Product> products) {
    if (!products.containsKey(product)) {
      products.put(product, Product.of(product));
    }
    products.get(product).addVariationByName(sku);
  }
}
