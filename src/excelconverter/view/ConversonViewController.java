package excelconverter.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ConversonViewController
{

	//Class Fields
	@FXML
	private TextField outputFolderField;
	@FXML
	private TextField outputNameField;
	@FXML
	private ListView<String> inputList;
	
	@FXML
	private void initialize()
	{
		ObservableList<String> items = FXCollections.observableArrayList(
				"C:/Desktop/testData/test1.csv", 
				"C:/Desktop/testData/test2.txt", 
				"C:/Desktop/testData/test3.xls", 
				"C:/Desktop/testData/test4.xlsx");
		
		inputList.setItems(items);
	}
	
	@FXML
	private void handleInputBrowse()
	{
		
	}
	
	@FXML
	private void handleOutputBrowse()
	{
		
	}

}
