package excelconverter.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Cell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 Class: DataReader
 Description: Reads data files such as CSV,TXT,XLS and XLSX and saves them into 
 a DataFile object
 */
public class DataReader
{

	private DataFile dataFile;

	public DataReader()
	{
		dataFile = new DataFile();

	}

	//Function used to read a text(tab or bar delimited) file and append the DataFile
	public void readFileTXT(String inFileName)
	{

		try
		{
			Scanner read = new Scanner(new File(inFileName));

			while (read.hasNextLine())
			{
				//Read line and split into array of strings
				String line = read.nextLine();
				String delims = "[|\t]";
				String[] currentRow = line.split(delims);

				dataFile.addRow(currentRow);
			}
			read.close();
		}
		catch (FileNotFoundException e)
		{

		}
	}

	//Function used to read a csv (comma delimited) file and append the DataFile
	public void readFileCSV(String inFileName)
	{
		try
		{
			Scanner read = new Scanner(new File(inFileName));

			while (read.hasNextLine())
			{
				//Read line and split into array of strings
				String line = read.nextLine();
				String delims = "[,]";
				String[] currentRow = line.split(delims);

				dataFile.addRow(currentRow);
			}
			read.close();
		}
		catch (FileNotFoundException e)
		{

		}
	}

	//Function used to read a Excel xls file and append the DataFile
	public void readFileXLS(String inFileName)
	{
		FileInputStream file = null;
		try
		{
			System.out.println(inFileName);
			file = new FileInputStream(new File(inFileName));

			HSSFWorkbook workbook = null;
			try
			{
				//Get the workbook instance for XLS file 
				workbook = new HSSFWorkbook(file);

				//Get sheets from the workbook
				int numberSheets = workbook.getNumberOfSheets();

				for (int currentSheet = 0; currentSheet < numberSheets; currentSheet++)
				{
					HSSFSheet sheet = workbook.getSheetAt(currentSheet);
					int maxNumOfCells = sheet.getRow(0).getLastCellNum();
					Iterator<Row> rowIterator = sheet.rowIterator();

					while (rowIterator.hasNext())
					{
						Row row = rowIterator.next();

						//For each row, iterate through each columns
						ArrayList<String> rowValues = new ArrayList();
						Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();

						String currentValue = new String();
// Loop through cells
						for (int cellCounter = 0; cellCounter < maxNumOfCells; cellCounter++)
						{

							org.apache.poi.ss.usermodel.Cell cell;

							if (row.getCell(cellCounter) == null)
							{
								cell = row.createCell(cellCounter);
							}
							else
							{
								cell = row.getCell(cellCounter);
							}

							//Check Type of Cell Data
							switch (cell.getCellType())
							{
								case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
									currentValue = String.valueOf(cell.getBooleanCellValue());
									break;
								case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
									currentValue = String.valueOf(cell.getNumericCellValue());

									if (HSSFDateUtil.isCellDateFormatted(cell))
									{
										DataFormatter df = new DataFormatter();
										currentValue = df.formatCellValue(cell);
									}
									break;
								case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
									currentValue = cell.getStringCellValue();
									break;
								case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK:
									currentValue = "";
									break;

							}

							rowValues.add(currentValue);

						}

						dataFile.addRow(rowValues);
					}
				}
			}
			catch (IOException ex)
			{
				Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
		catch (FileNotFoundException ex)
		{
			Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		finally
		{
			try
			{
				file.close();
			}
			catch (IOException ex)
			{
				Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	//Function used to read a Excel xlsx file and append the DataFile
	public void readFileXLSX(String inFileName)
	{
		FileInputStream file = null;
		try
		{
			file = new FileInputStream(new File(inFileName));

			XSSFWorkbook workbook = null;
			try
			{
				//Get the workbook instance for XLS file 
				workbook = new XSSFWorkbook(file);

				//Get sheets from the workbook
				int numberSheets = workbook.getNumberOfSheets();

				for (int currentSheet = 0; currentSheet < numberSheets; currentSheet++)
				{
					XSSFSheet sheet = workbook.getSheetAt(currentSheet);
					int maxNumOfCells = sheet.getRow(0).getLastCellNum();
					Iterator<Row> rowIterator = sheet.rowIterator();

					while (rowIterator.hasNext())
					{
						Row row = rowIterator.next();

						//For each row, iterate through each columns
						ArrayList<String> rowValues = new ArrayList();
						Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();

						String currentValue = new String();

						// Loop through cells
						for (int cellCounter = 0; cellCounter < maxNumOfCells; cellCounter++)
						{

							org.apache.poi.ss.usermodel.Cell cell;

							if (row.getCell(cellCounter) == null)
							{
								cell = row.createCell(cellCounter);
							}
							else
							{
								cell = row.getCell(cellCounter);
							}

							//Check Type of Cell Data
							switch (cell.getCellType())
							{
								case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
									currentValue = String.valueOf(cell.getBooleanCellValue());
									break;
								case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
									currentValue = String.valueOf(cell.getNumericCellValue());

									if (HSSFDateUtil.isCellDateFormatted(cell))
									{
										DataFormatter df = new DataFormatter();
										currentValue = df.formatCellValue(cell);
									}
									break;
								case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
									currentValue = cell.getStringCellValue();
									break;
								case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK:
									currentValue = "";
									break;

							}

							rowValues.add(currentValue);

						}

						dataFile.addRow(rowValues);
					}
				}
			}
			catch (IOException ex)
			{
				Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
		catch (FileNotFoundException ex)
		{
			Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		finally
		{
			try
			{
				file.close();
			}
			catch (IOException ex)
			{
				Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	public void clearDataFile()
	{
		dataFile = null;
	}

	public DataFile getDataFile()
	{
		return dataFile;
	}

}
