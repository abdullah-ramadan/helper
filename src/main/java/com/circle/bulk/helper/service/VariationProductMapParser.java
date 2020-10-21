package com.circle.bulk.helper.service;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.Set;

public interface VariationProductMapParser {

  Set<Product> parse(Sheet sheet);
}
