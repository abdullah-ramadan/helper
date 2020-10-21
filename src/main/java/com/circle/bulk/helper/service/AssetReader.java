package com.circle.bulk.helper.service;

import java.util.Set;

public interface AssetReader {

  void read(String assetsPath, Set<Product> products);
}
