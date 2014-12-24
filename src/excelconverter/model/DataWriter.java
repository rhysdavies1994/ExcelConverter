package excelconverter.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		File outputFile = new File(directory, outFileName);
		
		if(spreadsheet.hasData())
		{
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(outputFile);
				
				int amountRows = spreadsheet.getRowCount();
				
				for(int i=0;i<amountRows;i++)
				{
					ArrayList<String> row = spreadsheet.getRow(i);
					
					for(int j =0; j<row.size();j++)
					{
						String word = row.get(j);
						
						writer.write(word);
						writer.write("\t");
					}
					writer.write("\n");
					
				}
				
				writer.close();
				
			}
			catch (FileNotFoundException ex) {
				Logger.getLogger(DataWriter.class.getName()).log(Level.SEVERE, null, ex);
			}
			finally {
				writer.close();
			}
		}
		else
		{
			
		}
	}
	
	public void writeFileCSV(String directory, String outFileName, DataFile spreadsheet)
	{
		
	}
	
	public void writeFileXLS(String directory, String outFileName, DataFile spreadsheet)
	{
		
	}
	
	public void writeFileXLSX(String directory, String outFileName, DataFile spreadsheet)
	{
		
	}
	
}
