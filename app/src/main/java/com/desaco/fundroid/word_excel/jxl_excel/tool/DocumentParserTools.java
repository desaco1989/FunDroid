package com.desaco.fundroid.word_excel.jxl_excel.tool;

import com.desaco.fundroid.word_excel.jxl_excel.bean.LocationPoint;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

public class DocumentParserTools {

	public static List<LocationPoint> parseExcel(InputStream in) {
		List<LocationPoint> list = new ArrayList<LocationPoint>();
		Workbook workbook = null;
		try {
			try {
				workbook = Workbook.getWorkbook(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Sheet sheet = workbook.getSheet(0);
			//得到行数
			//int columnCount = sheet.getColumns();
			//得到列数
			int rowCount = sheet.getRows();
			for (int j = 1; j < rowCount; j++) {
				// getCell(列，行);
				int id = (int) ((NumberCell) sheet.getCell(0, j)).getValue();
				String date = sheet.getCell(1, j).getContents();
				double lon = ((NumberCell) sheet.getCell(2, j)).getValue();
				double lat = ((NumberCell) sheet.getCell(3, j)).getValue();

				LocationPoint point = new LocationPoint();
				point.setLongitude(lon);
				point.setLatitude(lat);
				point.setId(id);
				point.setLocateTime(date);
				
				list.add(point);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				workbook.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

}
