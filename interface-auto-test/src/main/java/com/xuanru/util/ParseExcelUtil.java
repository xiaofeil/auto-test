package com.xuanru.util;

import com.xuanru.common.Constants;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ParseExcelUtil {

	public static String getValue(XSSFCell xssfRow) {
		if (xssfRow != null) {
			if (xssfRow.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(xssfRow.getBooleanCellValue());
			} else if (xssfRow.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
				return String.valueOf(xssfRow.getNumericCellValue());
			} else {
				return xssfRow.getStringCellValue() == null ? "" : xssfRow.getStringCellValue()
						.trim();
			}
		} else {
			return "";
		}
	}

	public static String getValue(HSSFCell hssfCell) {
		if (hssfCell != null) {
			if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(hssfCell.getBooleanCellValue());
			} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				return String.valueOf(hssfCell.getNumericCellValue());
			} else {
				return hssfCell.getStringCellValue() == null ? "" : hssfCell.getStringCellValue()
						.trim();
			}
		} else {
			return "";
		}
	}

	/**
	 * 
	 * desc:获取文件后缀
	 * <p>
	 * 创建人：Liaoxf , 2015-12-21 下午3:16:34
	 * </p>
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getPostfix(String fileName) {
		if (StringUtil.isNotBlank(fileName) && fileName.contains(Constants.POINT)) {
			return fileName.substring(fileName.lastIndexOf(Constants.POINT) + 1, fileName.length());
		}
		return "";
	}
}
