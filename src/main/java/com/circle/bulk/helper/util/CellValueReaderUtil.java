package com.circle.bulk.helper.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

public final class CellValueReaderUtil {

  public static String cellStringValueWhateverItsType(Cell cell) {
    if (cell == null) return null;
    if (cell.getCellTypeEnum().equals(NUMERIC)) {
      try {
        Double l = Double.parseDouble(cell.getNumericCellValue() + "");
        return l.longValue() + "";
      } catch (Exception e) {
        double val = cell.getNumericCellValue();
        int valInt = (int) val;
        if (val == valInt) return valInt + "";
        else return val + "";
      }
    } else return cell.getStringCellValue();
  }

  public static Double cellDoubleValue(Cell cell) {
    if (cell == null) return null;
    return cell.getNumericCellValue();
  }

  public static Integer cellIntValue(Cell cell) {
    if (cell == null) return null;
    if (cell.getCellTypeEnum().equals(NUMERIC)) {
      return (int) cell.getNumericCellValue();
    }
    return StringUtils.hasText(cell.getStringCellValue().trim())
        ? parseInt(cell.getStringCellValue().trim())
        : null;
  }

  public static Long cellLongValue(Cell cell) {
    if (cell == null) return null;
    return (long) cell.getNumericCellValue();
  }

  public static Boolean cellBooleanValue(Cell cell) {
    if (cell == null) return null;
    return yesOrNoToBool(cell.getStringCellValue().trim());
  }

  private static boolean yesOrNoToBool(String yesOrNo) {
    return yesOrNo != null && yesOrNo.equalsIgnoreCase("YES");
  }

  public static BigDecimal cellBigDecimalValue(Cell cell) {
    if (cell == null) return null;
    Double val = getNumberWhateverItsType(cell);
    return val != null ? BigDecimal.valueOf(val) : null;
  }

  private static Double getNumberWhateverItsType(Cell cell) {
    if (cell.getCellTypeEnum().equals(NUMERIC)) {
      return cell.getNumericCellValue();
    }
    return StringUtils.hasText(cell.getStringCellValue().trim())
        ? parseDouble(cell.getStringCellValue().trim())
        : null;
  }

  public static boolean isEmptyRow(Row row) {
    for (int i = 0; i < 9; i++) {
      if (row.getCell(i) != null
          && cellStringValueWhateverItsType(row.getCell(i)) != null
          && cellStringValueWhateverItsType(row.getCell(i)).trim().length() != 0) return false;
    }
    return true;
  }
}
