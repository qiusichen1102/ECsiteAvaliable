package com.company.demo.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
@Component
public class Common{
	public Date getDate(String string) {
			try {
				java.util.Date javaDate = new SimpleDateFormat("yyyy-MM-dd")
						.parse(string );

				return new Date(javaDate.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
	}

	//sql.date　から　string へ　フォーマット変換
	public String getDate(Date date) {
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			return dateformat.format(date);
	}

	//システムタイム取得する
	public String getDate() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		return 	dateformat.format(date.getTime());
	}

	public  boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK){
	            return false;
	        }
	    }
	    return true;
	}


}
