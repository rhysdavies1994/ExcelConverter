package excelconverter.model;

import java.util.ArrayList;
import java.util.Arrays;

/*
 Class: DataFile
 Description: Container class for representing a basic excel spreadsheet
 */
public class DataFile
{
	//Class Fields
	private String fileName;
	private String[] headers;
	private ArrayList<ArrayList<String>> data;
	private String type;
	private String delimiter;
	
	public DataFile()
	{
		fileName=new String();
		data=new ArrayList();
		type=new String();
		delimiter=new String();
	}
	
	public DataFile(String inFileName)
	{
		fileName=inFileName;
		data=new ArrayList();
		type=new String();
		delimiter=new String();
	}

	/**
	 * @return the filename
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * @return the data
	 */
	public ArrayList<ArrayList<String>> getData()
	{
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(ArrayList<ArrayList<String>> data)
	{
		this.data = data;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return the delimiter
	 */
	public String getDelimiter()
	{
		return delimiter;
	}

	/**
	 * @param delimiter the delimiter to set
	 */
	public void setDelimiter(String delimiter)
	{
		this.delimiter = delimiter;
	}

	/**
	 * @return the headers
	 */
	public String[] getHeaders()
	{
		return headers;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(String[] headers)
	{
		this.headers = headers;
	}
	
	public void addRow(String[] row)
	{
		ArrayList<String> currentRow = new ArrayList<String>(Arrays.asList(row));
		data.add(currentRow);
	}
	
	public void addRow(ArrayList<String> row)
	{
		data.add(row);
	}
	
	//Get list of strings (row) at certain index
	public ArrayList<String> getRow(int rowIndex)
	{
		return data.get(rowIndex);
	}
	
	//Get value of cell at row/column
	public String getCell(int rowIndex, int columnIndex)
	{
		return data.get(rowIndex).get(columnIndex);
	}
	
	//Function to set a row using an arraylist of strings
	public void setRow(int rowIndex, ArrayList<String> row)
	{
		data.set(rowIndex, row);
	}
	
	//Function to set a row using an array of strings
	public void setRow(int rowIndex, String[] row)
	{
		ArrayList<String> currentRow = new ArrayList<String>(Arrays.asList(row));
		data.set(rowIndex, currentRow);
	}
	
	//Function to set a value of a cell in data file
	public void setCell(int rowIndex, int columnIndex, String value)
	{
		data.get(rowIndex).set(columnIndex,value);
	}
	
	public boolean hasData()
	{
		boolean dataExists=false;
		
		if(data.isEmpty())
		{
			dataExists=false;
		}
		else
		{
			dataExists=true;
		}
		
		return dataExists;
	}
	
	public int getRowCount()
	{
		return data.size();
	}
	
}
