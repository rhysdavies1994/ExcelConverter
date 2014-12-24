package excelconverter.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
				}
			}
		}
		else
		{

		}
	}

	public void writeFileXLS(String directory, String outFileName, DataFile spreadsheet)
	{

	}

	public void writeFileXLSX(String directory, String outFileName, DataFile spreadsheet)
	{

	}

	public boolean initialCheck(String directory, String outFileName)
	{
		boolean isValidFields = true;
		String errorMessage = new String();

		if (directory.isEmpty())
		{
			isValidFields = false;
			errorMessage += "The output folder has not been chosen.\n";
		}

		if (outFileName.isEmpty())
		{
			isValidFields = false;
			errorMessage += "The file name for output has not been chosen.\n";
		}

		if(isValidFields==false)
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
