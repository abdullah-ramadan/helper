package com.circle.bulk.helper.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.circle.bulk.helper.util.FilesUtils.getAssetImagesPaths;
import static com.circle.bulk.helper.util.FilesUtils.isFolderExist;
import static com.circle.bulk.helper.util.FilesUtils.pathSeparator;

@Service
public class AssetReaderImpl implements AssetReader {

  @Override
  public void read(String assetsPath, Set<Product> products) {
    products.forEach(p -> read(p, assetsPath));
  }

  private void read(Product product, String assetsPath) {
    String folderName = assetsPath.substring(assetsPath.lastIndexOf('/') + 1);
    List<Variation> variations = product.getVariations();
    for (Variation v : variations) {
      String fullPath = assetsPath + pathSeparator + v.getSku().trim();
      if (isFolderExist(fullPath)) {
        List<String> skuAssetPaths = getAssetImagesPaths(fullPath);
        skuAssetPaths =
            skuAssetPaths.stream()
                .map(path -> folderName + '/' + path)
                .collect(Collectors.toList());
        if (!product.hasCoverImage() && !skuAssetPaths.isEmpty()) {
          product.addCoverImage(skuAssetPaths.get(0));
          skuAssetPaths.remove(0);
        }
        v.setAssetsPaths(skuAssetPaths);
      }
    }
  }
}
