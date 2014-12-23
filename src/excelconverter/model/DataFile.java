package excelconverter.model;

/*
 Class: DataFile
 Description: Container class for representing a basic excel spreadsheet
 */
public class DataFile
{
	//Class Fields
	private String fileName;
	private String[][] data;
	private String type;
	private String delimiter;
	
	public DataFile()
	{
		fileName=null;
		data=null;
		type=null;
		delimiter=null;
	}
	
	public DataFile(String inFileName)
	{
		fileName=inFileName;
		data=null;
		type=null;
		delimiter=null;
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
	public String[][] getData()
	{
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String[][] data)
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
	
	
}
