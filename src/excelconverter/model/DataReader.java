package excelconverter.model;

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

}

public void readFileCSV(String inFileName)
{
	
}

public void readFileXLS(String inFileName)
{
	
}

public void readFileXLSX(String inFileName)
{
	
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


