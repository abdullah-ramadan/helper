package com.circle.bulk.helper.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Variation {

  @Getter private final String sku;
  @Getter @Setter private List<String> assetsPaths;

  public static Variation of(String sku, List<String> assetsPaths) {
    return new Variation(sku, assetsPaths);
  }

  public void removeAssetsIfExists(String asset) {
    assetsPaths.remove(asset);
  }

  public String getHtmlString(String assetFolderPath) {
    return "<h2 class=\"variation__title\">"
        + sku.trim()
        + ":</h2>"
        + getAssetsHtmlString(assetFolderPath);
  }

  private String getAssetsHtmlString(String assetFolderPath) {
    StringBuilder stringBuilder = new StringBuilder();
    for (String asset : assetsPaths) {
      stringBuilder
          .append("<img class=\"product__variations__image\" src=\"")
          .append(assetFolderPath + asset)
          .append("\"")
          .append("/>");
    }
    stringBuilder.append("<br/>");
    return stringBuilder.toString();
  }
}
