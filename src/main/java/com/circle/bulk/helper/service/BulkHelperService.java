package com.circle.bulk.helper.service;

public interface BulkHelperService {

  byte[] linkProductToItsAssets(String assetFolderPath, String bulkWorkBookPath);

  String generateHtmlTemplate(String assetFolderPath, String updatedBulkWorkBookPath);
}
