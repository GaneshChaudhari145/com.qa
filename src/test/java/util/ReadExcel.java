package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import property.LoadProperties;

public class ReadExcel {

	static File file;
	static FileInputStream inpr;
	static XSSFWorkbook wb;
	static XSSFSheet sheet;
	//static XSSFCell cell;
	static WebDriver driver;
	//static Properties prop;


	public ReadExcel() throws IOException{
		file=new File("D:\\TestData\\Sample.xlsx");
		inpr=new FileInputStream(file);
		wb=new XSSFWorkbook(inpr);
	}	
	public static HashMap<String, String> getData(String sheetName) throws IOException{

		ReadExcel read=new ReadExcel();
		sheet=wb.getSheet(sheetName);
		int rowCount=(sheet.getLastRowNum())+1;
		int colCount=sheet.getRow(0).getLastCellNum();

		//HashMap<String, String> map = new HashMap<String, String>();
		//String tcID="";
		List list=new ArrayList<String>();
		List list1=new ArrayList<String>();

		for(int i=0;i<rowCount;i++){
			Row row=sheet.getRow(i);
			if(i==0){
				for(int j=0;j<colCount;j++){
					list.add(row.getCell(j).getStringCellValue());
				}
			}
		/*tcID=row.getCell(0).getStringCellValue();
		//prop=LoadProperties.propertyLoader();
		String  s= prop.getProperty("TestID");
	    if(tcID.equalsIgnoreCase(s)){*/
				
			for(int j=1;j<colCount;j++){
					list1.add(row.getCell(j).getStringCellValue());
				}
			}
		//}
		return (HashMap<String, String>) list;
	}
	public static XSSFWorkbook getWb() {
		return ReadExcel.wb;
	}
	public static XSSFSheet getSheet() {
		return ReadExcel.sheet;
	}
}
