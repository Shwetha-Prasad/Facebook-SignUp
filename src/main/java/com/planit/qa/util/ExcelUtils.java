package com.planit.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.planit.qa.base.TestBase;
import com.planit.qa.exceptions.DataNotFoundException;

public class ExcelUtils extends TestBase{
	
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+"/src/main/resources/TestData.xlsx";
	FileInputStream inputStream = null;
	Workbook workbook = null;
	Sheet sheet = null;
	Row row = null;
	int rowValue =0;
	
	
	public ArrayList<String> getTestDataBasedOnTestCase(String sheetName,String testCaseName) throws DataNotFoundException, IOException 

	{

		ArrayList<String> list = new ArrayList<String>();
		try {
			try {
				File inputFile = new File(TESTDATA_SHEET_PATH);
				inputStream = new FileInputStream(inputFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			String fileNameExtension = TESTDATA_SHEET_PATH.substring(TESTDATA_SHEET_PATH.indexOf("."));
			if(fileNameExtension.equals(".xlsx"))
			{

				try {
					workbook= new XSSFWorkbook(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			else if(fileNameExtension.equals(".xls")){

				try {
					workbook= new HSSFWorkbook(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			boolean sheetExists = sheetExistsOrNot(sheetName);
			if(sheetExists)

				sheet =workbook.getSheet(sheetName);

			else 

				throw new DataNotFoundException("Sheet does not exists");

			int totalRowCount = sheet.getLastRowNum();
			for(int i =1 ;i<=totalRowCount;i++)
			{
				row = sheet.getRow(i);
				if(!(row == null))
				{
					if(row.getCell(0).toString().contains(testCaseName))
					{
						rowValue = row.getRowNum();
						row = sheet.getRow(rowValue);
						break;
					}
				}
			}

			int columnCount = row.getLastCellNum();

			for (int i = 0; i < columnCount; i++) {
				if (row.getCell(i) != null) {
					if(row.getCell(i).getCellType() != 1)
					{
						DataFormatter formatter = new DataFormatter();
						FormulaEvaluator evaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
						evaluator.evaluate(row.getCell(i));
						String cellValue = formatter.formatCellValue(row.getCell(i), evaluator);
						list.add(cellValue);
					}
					else {
						list.add(row.getCell(i).toString());
					}

				}else {

					throw new DataNotFoundException("Required data not found in file");

				}
			}
		}catch (Exception e) {

			e.printStackTrace();
		}
		finally {
			inputStream.close();
		}
		return list;

	}




	public boolean sheetExistsOrNot(String sheetName){


		int index = workbook.getSheetIndex(sheetName);
		if(index==-1){
			index=workbook.getSheetIndex(sheetName.toUpperCase());
			if(index==-1)
				return false;
			else
				return true;
		}
		else
			return true;
	}
	


}
