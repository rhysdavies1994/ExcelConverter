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
	//Datafile main object to store all spreadsheet data
	private DataFile dataFile;

	//Constructor
	public DataReader()
	{
		dataFile = new DataFile();
	}

	//Function used to read a text(tab or bar delimited) file and append the DataFile
	public void readFileTXT(String inFileName)
	{
		readDelimitedFile(inFileName, "[|\t]");
	}

	//Function used to read a csv (comma delimited) file and append the DataFile
	public void readFileCSV(String inFileName)
	{
		readDelimitedFile(inFileName,"[,]");
	}
	
	//Function used to read any plain text file which is simply delimited
	public void readDelimitedFile(String inFileName, String delimiters)
	{
		try
		{
			//Create scanner for reading through file
			Scanner read = new Scanner(new File(inFileName));

			while (read.hasNextLine())
			{
				//Read line and split into array of strings based on regex delimiters
				String line = read.nextLine();
				String[] currentRow = line.split(delimiters);

				//Add the row to the datafile object
				dataFile.addRow(currentRow);
			}
			
			//Close Scanner
			read.close();
		}
		catch (FileNotFoundException e) //If file does not exist
		{
			System.out.println("File not found");
		}
	}

	

	//Function used to read a Excel xls file and append the DataFile Object
	public void readFileXLS(String inFileName)
	{
		//Create Input Stream to read data from file
		FileInputStream file = null;
		try
		{
			file = new FileInputStream(new File(inFileName));

			//Create HSSFWorkbook for handling XLS type Files
			HSSFWorkbook workbook = null;
			try
			{
				//Get the workbook instance for XLS file 
				workbook = new HSSFWorkbook(file);

				//Get sheets from the workbook
				int numberSheets = workbook.getNumberOfSheets();

				//Iterate through each sheet
				for (int currentSheet = 0; currentSheet < numberSheets; currentSheet++)
				{
					//Create a row iterator from the current sheet
					HSSFSheet sheet = workbook.getSheetAt(currentSheet);
					Iterator<Row> rowIterator = sheet.rowIterator();

					//If there is atleast one row, get amount of columns and process the rows
					int maxNumOfCells = 0;
					if (rowIterator.hasNext())
					{
						maxNumOfCells = sheet.getRow(0).getLastCellNum();
						processRowIterator(rowIterator, maxNumOfCells);
					}
				}

				//Close the input stream
				file.close();
			}
			catch (IOException ex)
			{
				Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
				System.out.println("Problem with writing to file!");
			}
		}
		catch (FileNotFoundException ex)
		{
			Logger.getLogger(DataReader.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("File not found");
		}
	}

	//Function used to read a Excel xlsx file and append the DataFile Object
	public void readFileXLSX(String inFileName)
	{

		//Create Input Stream to read data from file
		FileInputStream file = null;
		try
		{
			file = new FileInputStream(new File(inFileName));

			//Create XSSFWorkbook for handling XLSX type Files
			XSSFWorkbook workbook = null;
			try
			{
				//Get the workbook instance for XLSX file 
				workbook = new XSSFWorkbook(file);

				//Get sheets from the workbook
				int numberSheets = workbook.getNumberOfSheets();

				//Iterate through each sheet in workbook
				for (int currentSheet = 0; currentSheet < numberSheets; currentSheet++)
				{
					//Create a row iterator from the current sheet
					XSSFSheet sheet = workbook.getSheetAt(currentSheet);
					Iterator<Row> rowIterator = sheet.rowIterator();

					//If there is atleast one row, get amount of columns and process the rows
					int maxNumOfCells = 0;
					if (rowIterator.hasNext())
					{
						maxNumOfCells = sheet.getRow(0).getLastCellNum();
						processRowIterator(rowIterator, maxNumOfCells);
					}
				}

				//Close the input stream
				file.close();
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
	}
	
	//Helper function for reading XLS and XLSX files
	public void processRowIterator(Iterator<Row> rowIterator, int maxNumOfCells)
	{

		//Iterate through rows while another exists
		while (rowIterator.hasNext())
		{
			Row row = rowIterator.next();
			ArrayList<String> rowValues = new ArrayList();
			Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();
			String currentValue = new String();
			
			//Iterate through every column cell
			for (int cellCounter = 0; cellCounter < maxNumOfCells; cellCounter++)
			{
			//If the cell is null, create a blank cell, otherwise add the cell data
				org.apache.poi.ss.usermodel.Cell cell;
				
				if (row.getCell(cellCounter) == null)
				{
					cell = row.createCell(cellCounter);
				}
				else
				{
					cell = row.getCell(cellCounter);
				}

				//Check type of cell and store value correctly as a string
				switch (cell.getCellType())
				{
					//If the value is a boolean
					case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
						currentValue = String.valueOf(cell.getBooleanCellValue());
						break;
						
					//If the value is a number
					case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
						currentValue = String.valueOf(cell.getNumericCellValue());

						//If the value is a date
						if (HSSFDateUtil.isCellDateFormatted(cell))
						{
							DataFormatter df = new DataFormatter();
							currentValue = df.formatCellValue(cell);
						}
						break;
						
					//If the value is a string	
					case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
						currentValue = cell.getStringCellValue();
						break;
						
					//If the value is a blank	
					case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK:
						currentValue = "";
						break;
				}

				//Add cell value to row
				rowValues.add(currentValue);

			}

			//Add row to the data file
			dataFile.addRow(rowValues);
		}
	}

	//Function used to clear all data from dataFile object
	public void clearDataFile()
	{
		dataFile = null;
	}

	//Retrieve the datafile object
	public DataFile getDataFile()
	{
		return dataFile;
	}

}
