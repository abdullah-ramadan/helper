package com.circle.bulk.helper.service;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.circle.bulk.helper.service.BulkBookSheetName.PRODUCT_COVER_IMAGE;
import static com.circle.bulk.helper.service.BulkBookSheetName.VARIATIONS;
import static com.circle.bulk.helper.service.BulkBookSheetName.VARIATIONS_IMAGES;
import static com.circle.bulk.helper.util.FilesUtils.workBookToByteArray;
import static com.circle.bulk.helper.util.FilesUtils.workbookFromURL;

@Service
@AllArgsConstructor
public class BulkHelperServiceImpl implements BulkHelperService {

  private final VariationProductMapParser variationProductMapParser;
  private final AssetReader assetReader;
  private final ProductCoverSheetGenerator productCoverSheetGenerator;
  private final VariationAssetsSheetGenerator variationAssetsSheetGenerator;
  private final ProductVariationsMapParser productVariationsMapParser;

  @Override
  public byte[] linkProductToItsAssets(String assetFolderPath, String bulkWorkBookPath) {
    Workbook workbook = workbookFromURL(bulkWorkBookPath);
    Set<Product> products=
        variationProductMapParser.parse(workbook.getSheet(VARIATIONS.name()));
    assetReader.read(assetFolderPath, products);
    productCoverSheetGenerator.create(workbook, products);
    variationAssetsSheetGenerator.create(workbook, products);
    return workBookToByteArray(workbook);
  }

  @Override
  public String generateHtmlTemplate(String assetFolderPath, String updatedBulkWorkBookPath) {
    Workbook workbook = workbookFromURL(updatedBulkWorkBookPath);
    Map<String, Product> productMap =
        productVariationsMapParser.parseProducts(workbook.getSheet(PRODUCT_COVER_IMAGE.name()));
    productVariationsMapParser.parseProductsVariation(
        productMap, workbook.getSheet(VARIATIONS_IMAGES.name()));
    return getHTMLString(new HashSet<>(productMap.values()), getFolderName(assetFolderPath));
  }

  private String getFolderName(String assetFolderPath){
    return assetFolderPath.substring(0, assetFolderPath.lastIndexOf('/')+1);
  }

  private String getHTMLString(Set<Product> products, String folderName) {
    Set<Product> productsWithoutAsset = new HashSet<>();
    StringBuilder builder = new StringBuilder("<html> <body>").append(getCSSCode());
    for (Product p : products){
      if(p.hasCoverImage())
      builder.append(p.getHtmlString(folderName));
      else
        productsWithoutAsset.add(p);
    }

    for(Product p: productsWithoutAsset) builder.append(p.getHtmlString(folderName));

    return builder.append("</body>").append("</html>").toString();
  }

  private String getCSSCode(){
     return """
  <style>
    body {
      padding: 0;
      margin: 0;
    }
    .product {
      padding: 1rem;
      margin-bottom: 2rem;
    }
    .product__header {
      display: flex;
      flex-direction: row;
      justify-content: flex-start;
      position: relative;
      height: 350px;
    }
    .product__title,
    .variation__title {
      text-transform: capitalize;
    }
    .product__image {
      position: absolute;
      left: 50%;
      transform: translateX(-50%);
      max-height: 350px;
      max-width: 350px;
      box-shadow: 0px 0px 5px 0px rgba(0, 0, 0, 0.75);
    }
    .product__variations {
      display: flex;
      flex-direction: row;
      overflow: auto;
    }
    .product__variations__image {
      max-height: 250px;
      max-width: 250px;
      margin-right: 0.5rem;
    }
  </style>
  """;
  }
}
