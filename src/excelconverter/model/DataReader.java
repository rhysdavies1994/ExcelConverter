package excelconverter.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	dataFile=new DataFile();
	

}
	
	
public void readFileTXT(String inFileName)
{
	
	try
	{
		Scanner read = new Scanner (new File(inFileName));
		
		while (read.hasNextLine())
		{
			//Read line and split into array of strings
			String line = read.nextLine();
			String delims = "[,|\t]";
			String[] currentRow = line.split(delims);
			
			dataFile.addRow(currentRow);
		}
		read.close();
	}
	catch(FileNotFoundException e)
	{
		
	}
}

public void readFileCSV(String inFileName)
{
	try
	{
		Scanner read = new Scanner (new File(inFileName));
		
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
	catch(FileNotFoundException e)
	{
		
	}
}

public void readFileXLS(String inFileName)
{
//	Workbook wb = new HSSFWorkbook();
//    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
//    wb.write(fileOut);
//    fileOut.close();

    
}

public void readFileXLSX(String inFileName)
{
//	Workbook wb = new XSSFWorkbook();
//    FileOutputStream fileOut = new FileOutputStream(inFileName);
//    wb.write(fileOut);
//    fileOut.close();
}

public void clearDataFile()
{
	dataFile=null;
}

public DataFile getDataFile()
{
	return dataFile;
}


}


