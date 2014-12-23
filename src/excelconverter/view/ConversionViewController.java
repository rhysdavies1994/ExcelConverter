package excelconverter.view;

import excelconverter.MainApp;
import java.io.File;
import java.util.List;
import java.util.ListIterator;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;


public class ConversionViewController
{

	//Class Fields
	@FXML
	private TextField outputFolderField;
	@FXML
	private TextField outputNameField;
	@FXML
	private ListView<String> inputList;
	private ObservableList<String> inputFiles;
	
	 // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	@FXML
	private void initialize()
	{
		//Initialise list for listview
		inputFiles = FXCollections.observableArrayList();
		
		//Update listView when an item is added or removed
		inputFiles.addListener(new ListChangeListener() 
		{
            @Override
            public void onChanged(ListChangeListener.Change change) {
            inputList.setItems(inputFiles);
			}
        });
		
	}
	
	//Function called when browse button is clicked for input files
	@FXML
	private void handleInputBrowse()
	{
		 FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Data files","*.txt","*.csv","*.xls","*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
		
		//Select multiple files for import
        List<File> files = fileChooser.showOpenMultipleDialog(mainApp.getPrimaryStage());

		for (ListIterator<File> iter = files.listIterator(); iter.hasNext(); ) 
		{
		File currentFile = iter.next();
		
		addListItem(currentFile.getPath());
		
		// 1 - can call methods of element
		// 2 - can use iter.remove() to remove the current element from the list
		// 3 - can use iter.add(...) to insert a new element into the list
		//     between element and iter->next()
		// 4 - can use iter.set(...) to replace the current element

		// ...
		}
	}
	
	//Function called when browse button is clicked for output
	@FXML
	private void handleOutputBrowse()
	{
		
	}
	
	//Function for retreiving name of file at certain index in list
	public String getListItem(int index)
	{
		ObservableList<String> items = inputList.getItems();
		String selectedItem = items.get(index);
		return selectedItem;
	}
	
	//Function for adding files to input list
	public void addListItem(String item)
	{
        inputFiles.add(item);
	}

	public void removeListItem(int index)
	{
		
	}
 
       
	

}
