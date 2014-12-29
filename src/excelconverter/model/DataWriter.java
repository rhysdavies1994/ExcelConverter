package excelconverter.model;

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

	public DataWriter()
	{

	}

	public void writeFileTXT(String directory, String outFileName, DataFile spreadsheet)
	{
		if (initialCheck(directory, outFileName) == true)
		{
			outFileName += ".txt";

			File outputFile = new File(directory, outFileName);

			if (spreadsheet.hasData())
			{
				PrintWriter writer = null;
				try
				{
					writer = new PrintWriter(outputFile);

					int amountRows = spreadsheet.getRowCount();

					for (int i = 0; i < amountRows; i++)
					{
						ArrayList<String> row = spreadsheet.getRow(i);

						for (int j = 0; j < row.size(); j++)
						{
							String word = row.get(j);

							writer.write(word);
							writer.write("\t");
						}
						writer.write("\n");

					}

					writer.close();

					Dialogs.create()
							.title("Complete")
							.masthead("Writing to File Complete")
							.message("You can find the file at:\n" + outputFile.getPath())
							.showInformation();

				}
				catch (FileNotFoundException ex)
				{
					Logger.getLogger(DataWriter.class.getName()).log(Level.SEVERE, null, ex);
					Dialogs.create()
							.title("Writing to File Error")
							.masthead("The system cannot find the path specified")
							.message("Please check output directory exists, if not, create it and try again")
							.showError();
				}
			}
		}
		else
		{

		}
	}

	public void writeFileCSV(String directory, String outFileName, DataFile spreadsheet)
	{
		if (initialCheck(directory, outFileName) == true)
		{
			outFileName += ".csv";

			File outputFile = new File(directory, outFileName);

			if (spreadsheet.hasData())
			{
				PrintWriter writer = null;
				try
				{
					writer = new PrintWriter(outputFile);

					int amountRows = spreadsheet.getRowCount();

					for (int i = 0; i < amountRows; i++)
					{
						ArrayList<String> row = spreadsheet.getRow(i);

						for (int j = 0; j < row.size(); j++)
						{
							String word = row.get(j);

							writer.write(word);
							writer.write(",");
						}
						writer.write("\n");

					}

					writer.close();

					Dialogs.create()
							.title("Complete")
							.masthead("Writing to File Complete")
							.message("You can find the file at:\n" + outputFile.getPath())
							.showInformation();

				}
				catch (FileNotFoundException ex)
				{
					Logger.getLogger(DataWriter.class.getName()).log(Level.SEVERE, null, ex);

					Dialogs.create()
							.title("Writing to File Error")
							.masthead("The system cannot find the path specified")
							.message("Please check output directory exists, if not, create it and try again")
							.showError();
				}
				finally
				{

				}
			}
		}
		else
		{

		}
	}

	public void writeFileXLS(String directory, String outFileName, DataFile spreadsheet)
	{
		Workbook wb = new HSSFWorkbook();
		FileOutputStream fileOut;

		outFileName += ".xls";

		try
		{
			File outputFile = new File(directory, outFileName);
			fileOut = new FileOutputStream(outputFile);
			Sheet sheet = wb.createSheet("Sheet1");
			CreationHelper createHelper = wb.getCreationHelper();

			// Create rows and cells. Rows are 0 based.
			for (int currentRow = 0; currentRow < spreadsheet.getRowCount(); currentRow++)
			{
				Row row = sheet.createRow(currentRow);

				ArrayList<String> rowList = spreadsheet.getRow(currentRow);
				for (int currentCell = 0; currentCell < rowList.size(); currentCell++)
				{
					Cell cell = row.createCell(currentCell);
					cell.setCellValue(rowList.get(currentCell));

				}

			}

			// Write the output to a file
			wb.write(fileOut);
			fileOut.close();

			Dialogs.create()
					.title("Complete")
					.masthead("Writing to File Complete")
					.message("You can find the file at:\n" + outputFile.getPath())
					.showInformation();
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

	public void writeFileXLSX(String directory, String outFileName, DataFile spreadsheet)
	{
		Workbook wb = new XSSFWorkbook();
		FileOutputStream fileOut;

		outFileName += ".xlsx";
		try
		{
			File outputFile = new File(directory, outFileName);
			fileOut = new FileOutputStream(outputFile);
			Sheet sheet = wb.createSheet("Sheet1");
			CreationHelper createHelper = wb.getCreationHelper();

			// Create rows and cells. Rows are 0 based.
			for (int currentRow = 0; currentRow < spreadsheet.getRowCount(); currentRow++)
			{
				Row row = sheet.createRow(currentRow);

				ArrayList<String> rowList = spreadsheet.getRow(currentRow);
				for (int currentCell = 0; currentCell < rowList.size(); currentCell++)
				{
					Cell cell = row.createCell(currentCell);
					cell.setCellValue(rowList.get(currentCell));
				}

			}

			// Write the output to a file
			wb.write(fileOut);
			fileOut.close();

			Dialogs.create()
					.title("Complete")
					.masthead("Writing to File Complete")
					.message("You can find the file at:\n" + outputFile.getPath())
					.showInformation();
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
			if(outputFolder.exists())
			{
				
			}
			else
			{
				isValidFields=false;
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
