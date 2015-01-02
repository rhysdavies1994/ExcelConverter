package excelconverter.model;

import com.smartxls.WorkBook;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.dialog.Dialogs;

/*
 Class: DataWriter
 Description: Takes a DataFile object and writes it to a CSV,TXT,XLS OR XLSX
 */
public class DataWriter
{

	//Constructor
	public DataWriter()
	{

	}

	//Function for writing a DataFile object to a Text(Tab Delimited) File
	public void writeFileTXT(String directory, String outFileName, DataFile spreadsheet)
	{
		//If file can be created
		if (initialCheck(directory, outFileName) == true)
		{
			//Append .txt extension to filename
			outFileName += ".txt";

			writeDelimitedFile(directory, outFileName, spreadsheet, "\t");
		}
		else
		{

		}
	}

	//Function for writing a DataFile object to a CSV(Comma Delimited) File
	public void writeFileCSV(String directory, String outFileName, DataFile spreadsheet)
	{
		//If file can be created
		if (initialCheck(directory, outFileName) == true)
		{
			//Append .txt extension to filename
			outFileName += ".csv";

			writeDelimitedFile(directory, outFileName, spreadsheet, ",");
		}
		else
		{

		}

	}

	public void writeDelimitedFile(String directory, String outFileName, DataFile spreadsheet, String delimiter)
	{
		//Create File to write to
		File outputFile = new File(directory, outFileName);

		//If DataFile has data to be written/is not empty
		if (spreadsheet.hasData())
		{
			//Create an Object to write to file
			PrintWriter writer = null;
			try
			{
				writer = new PrintWriter(outputFile);

				//Iterate through rows in data file
				int amountRows = spreadsheet.getRowCount();
				for (int currentRow = 0; currentRow < amountRows; currentRow++)
				{
					//Get current row
					ArrayList<String> row = spreadsheet.getRow(currentRow);

					//Iterate through columns
					for (int currentCell = 0; currentCell < row.size(); currentCell++)
					{
						//Get cell in row/column
						String word = row.get(currentCell);

						//Write this cell to file and delimit by a tab
						writer.write(word);
						writer.write(delimiter);
					}

					//Add new line character to end of row
					writer.write("\r\n");
				}

				//Once finished writing, close file
				writer.close();
			}
			catch (FileNotFoundException ex) //If file can not be created, log it
			{
				Logger.getLogger(DataWriter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	//Function for writing a DataFile object to a Excel XLS File
	public void writeFileXLS(String directory, String outFileName, DataFile spreadsheet)
	{
		//If file can be created
		if (initialCheck(directory, outFileName) == true)
		{
			//Create XLS type workbook
			Workbook wb = new HSSFWorkbook();
			FileOutputStream fileOut;

			//Make sure file extension is .xls
			outFileName += ".xls";

			try
			{
				//Create output File to write to
				File outputFile = new File(directory, outFileName);

				//Create output Stream to do the writing
				fileOut = new FileOutputStream(outputFile);

				//Create new sheet in workbook
				Sheet sheet = wb.createSheet("Sheet1");
				CreationHelper createHelper = wb.getCreationHelper();

				//Iterate through rows
				for (int currentRow = 0; currentRow < spreadsheet.getRowCount(); currentRow++)
				{
					//Create a row in workbook
					Row row = sheet.createRow(currentRow);

					//Get Row from DataFile
					ArrayList<String> rowList = spreadsheet.getRow(currentRow);

					//Iterate through cells
					for (int currentCell = 0; currentCell < rowList.size(); currentCell++)
					{
						//Create cell in workbook
						Cell cell = row.createCell(currentCell);

						//Write cell from datafile to workbook
						cell.setCellValue(rowList.get(currentCell));
					}
				}

				// Write the output to a file
				wb.write(fileOut);

				//Close File
				fileOut.close();
			}
			catch (FileNotFoundException ex)
			{
				Logger.getLogger(DataWriter.class.getName()).log(Level.SEVERE, null, ex);
			}
			catch (IOException ex)
			{
				Logger.getLogger(DataWriter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		else
		{

		}

	}

	//Function for writing a DataFile object to a Excel XLSX File
	public void writeFileXLSX(String directory, String outFileName, DataFile spreadsheet)
	{
		//If file can be created
		if (initialCheck(directory, outFileName) == true)
		{
			//Create XLSX Style workbook
			Workbook wb = new XSSFWorkbook();
			FileOutputStream fileOut;

			//Make sure file extension is .xlsx
			outFileName += ".xlsx";
			try
			{
				//Create output File to write to
				File outputFile = new File(directory, outFileName);

				//Create output Stream to do the writing
				fileOut = new FileOutputStream(outputFile);

				//Create new sheet in workbook
				Sheet sheet = wb.createSheet("Sheet1");
				CreationHelper createHelper = wb.getCreationHelper();

				//Iterate through rows
				for (int currentRow = 0; currentRow < spreadsheet.getRowCount(); currentRow++)
				{
					//Create a row in workbook
					Row row = sheet.createRow(currentRow);

					//Get Row from DataFile
					ArrayList<String> rowList = spreadsheet.getRow(currentRow);

					//Iterate through cells
					for (int currentCell = 0; currentCell < rowList.size(); currentCell++)
					{
						//Create cell in workbook
						Cell cell = row.createCell(currentCell);

						//Write cell from datafile to workbook
						cell.setCellValue(rowList.get(currentCell));
					}
				}

				// Write the output to a file
				wb.write(fileOut);

				//Close File
				fileOut.close();
			}
			catch (FileNotFoundException ex)
			{
				Logger.getLogger(DataWriter.class.getName()).log(Level.SEVERE, null, ex);

			}
			catch (IOException ex)
			{
				Logger.getLogger(DataWriter.class.getName()).log(Level.SEVERE, null, ex);

			}
		}
		else
		{

		}

	}

	//Function used for creating a SmartXLS Workbook from a DataFile Object
	public WorkBook sx_createWorkBook(DataFile spreadsheet) throws Exception
	{
		//Create workbook for storing data
		WorkBook workBook;
		workBook = new WorkBook();
		
		workBook.insertSheets(0,1);
		workBook.setSheet(0);
		
		//Find amount of rows in datafile
		int amountRows = spreadsheet.getRowCount();

		//Iterate through each row
		for (int currentRow = 0; currentRow < amountRows; currentRow++)
		{
			//Get the row from the Datafile object
			ArrayList<String> row = spreadsheet.getRow(currentRow);

			//Get the amount of columns in this row
			int amountColumns = row.size();

			//Iterate through each column
			for (int currentCell = 0; currentCell < amountColumns; currentCell++)
			{
				//Get the cell from the current row
				String cell = row.get(currentCell);
				
				//Add the cell to the workbook
				workBook.setText(currentRow, currentCell, cell);
			}
		}

		//Return the workbook
		return workBook;
	}

	//Function used for writing a DataFile object to a Excel XLSX File
	public void sx_writeFileXLSX(String directory, String outFileName, DataFile spreadsheet)
	{
		//If file can be created
		if (initialCheck(directory, outFileName) == true)
		{
			try
			{
				//Create workbook from DataFile object
				WorkBook workBook;
				workBook = sx_createWorkBook(spreadsheet);

				//Save workbook as XLSX
				File outputFile = new File(directory, outFileName + ".xlsx");
				workBook.writeXLSX(outputFile.getPath());
			}
			catch (Exception ex)
			{
				Logger.getLogger(DataWriter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		else
		{

		}
	}
	
	//Function used for writing a DataFile object to a TXT(Tab Delimited) File
	public void sx_writeFileTXT(String directory, String outFileName, DataFile spreadsheet)
	{
		//If file can be created
		if (initialCheck(directory, outFileName) == true)
		{
			try
			{
				//Create workbook from DataFile object
				WorkBook workBook;
				workBook = sx_createWorkBook(spreadsheet);

				//Save workbook as TXT
				File outputFile = new File(directory, outFileName + ".txt");
				workBook.setCSVSeparator('\t');
				workBook.writeCSV(outputFile.getPath());
			}
			catch (Exception ex)
			{
				Logger.getLogger(DataWriter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		else
		{

		}
	}
	
	//Function used for writing a DataFile object to a CSV(Comma Delimited) File
	//Does not work!!
	public void sx_writeFileCSV(String directory, String outFileName, DataFile spreadsheet)
	{
		//If file can be created
		if (initialCheck(directory, outFileName) == true)
		{
			try
			{
				//Create workbook from DataFile object
				WorkBook workBook;
				workBook = sx_createWorkBook(spreadsheet);

				//Save workbook as CSV
				File outputFile = new File(directory, outFileName + ".csv");
				workBook.setCSVSeparator(',');
				workBook.writeXLSX(outputFile.getPath());
			}
			catch (Exception ex)
			{
				Logger.getLogger(DataWriter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		else
		{

		}
	}
	
	//Function used for writing a DataFile object to a Excel XLSX File
	public void sx_writeFileXLS(String directory, String outFileName, DataFile spreadsheet)
	{
		//If file can be created
		if (initialCheck(directory, outFileName) == true)
		{
			try
			{
				//Create workbook from DataFile object
				WorkBook workBook;
				workBook = sx_createWorkBook(spreadsheet);

				//Save workbook as XLS
				File outputFile = new File(directory, outFileName + ".xls");
				workBook.write(outputFile.getPath());
			}
			catch (Exception ex)
			{
				Logger.getLogger(DataWriter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		else
		{

		}
	}

	//Function for checking output fields and if file can be created
	public boolean initialCheck(String directory, String outFileName)
	{
		boolean isValidFields = true;
		String errorMessage = new String();
		File outputFolder;

		FileOutputStream fileOut;

		if (directory.isEmpty())
		{
			isValidFields = false;
			errorMessage += "The output folder has not been chosen.\n";
		}
		else
		{
			outputFolder = new File(directory);
			if (!outputFolder.isDirectory())
			{
				outputFolder = outputFolder.getParentFile();
			}
			if (outputFolder.exists())
			{

			}
			else
			{
				isValidFields = false;
				errorMessage += "The Chosen Directory does not exist, Please create it and try again.\n";
			}
		}

		if (outFileName.isEmpty())
		{
			isValidFields = false;
			errorMessage += "The file name for output has not been chosen.\n";
		}

		if (isValidFields == false)
		{
			Dialogs.create()
					.title("Error")
					.masthead("Problem with Output Path")
					.message(errorMessage)
					.showError();
		}

		return isValidFields;
	}
}
