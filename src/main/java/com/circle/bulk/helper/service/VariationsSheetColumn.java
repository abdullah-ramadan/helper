package com.circle.bulk.helper.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VariationsSheetColumn {
  PRODUCT("Product", 0),
  S_ENGLISH_NAME("English Name", 1),
  S_ARABIC_NAME("Arabic Name", 2),
  STOCK("Stock", 3),
  STOCK_ALERT("Low Stock Alert", 4),
  PRICE("Price", 5),
  SALE_PRICE("Sale Price", 6),
  S_SIZE("Size", 7),
  S_COLOR("Color", 8),
  SKU("Sku", 9),
  AUTO_GENERATE_SKU_REF("Auto Generate SKU Ref Number", 10),
  DEFAULT_VARIATION("Default Variation", 11);

  private String header;
  private int index;
}
