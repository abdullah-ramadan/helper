package com.circle.bulk.helper.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {

  private final String name;
  private String coverPath;
  private final List<Variation> variations;

  public static Product of(String name) {
    return new Product(name, null, new ArrayList<>());
  }

  public static Product of(String name, String coverPath) {
    return new Product(name, coverPath, new ArrayList<>());
  }

  public void addVariation(Variation variation)
  {
    variation.removeAssetsIfExists(coverPath);
    variations.add(variation);
  }

  public void addCoverImage(String coverPath){
    this.coverPath = coverPath;
  }

  public void addVariationByName(String sku){
    Variation v = Variation.of(sku, new ArrayList<>());
    variations.add(v);
  }

  public int getVariationsCount(){
    return (variations == null)? 0: variations.size();
  }

  public boolean hasCoverImage(){
    return this.coverPath != null;
  }

  public String getHtmlString(String assetFolderPath) {
    return
            """
            <div class="product">
                <div class="product__header">
                  <h1 class="product__title">
            """
        + name
        + "       </h1>"
        + ((coverPath != null)
            ? ("<img class=\"product__image\" src=\""
                + assetFolderPath
                + coverPath
                + "\""
                + "/> </div>")
            : "</div>")
        + ((variations == null || variations.isEmpty())
            ? ""
            : "<div>"+getVariationHtmlString(assetFolderPath))+"</div>"
        + " <hr /> </div>";
  }

  private String getVariationHtmlString(String assetFolderPath) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Variation var : variations) {
      stringBuilder.append(var.getHtmlString(assetFolderPath));
    }
    return stringBuilder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return name.equals(product.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
