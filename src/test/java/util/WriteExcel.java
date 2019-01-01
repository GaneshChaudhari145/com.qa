package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {
	private FileOutputStream fos;
//	
//	public WriteExcel() throws FileNotFoundException {
//		
//	}
	public WriteExcel() {
		try {
			this.fos = new FileOutputStream(new File("D:\\TestData\\Result\\Sample1.xlsx"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void fillExcelFile(Row currentRow, String output) {
		
//		for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
//			if(sheet.getRow(i).getCell(0).getStringCellValue().equalsIgnoreCase(tcId)) {
//				sheet.getRow(i).createCell(2).setCellValue(output);
//			}
//		}
		currentRow.createCell(2).setCellValue(output);
	}
	
	public void writeResultFile(XSSFWorkbook wb) {
		try {
			wb.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
