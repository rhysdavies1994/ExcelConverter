/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelconverter.view;

import excelconverter.MainApp;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

public class PDFToolsViewController
{

	@FXML
	private ListView<String> inputList;

	private ObservableList<String> inputFiles;

	// Reference to the main application
	private MainApp mainApp;

	@FXML
	public void initialize()
	{
		//Initialise list for listview
		inputFiles = FXCollections.observableArrayList();

		//Update listView when an item is added or removed
		inputFiles.addListener(new ListChangeListener()
		{
			@Override
			public void onChanged(ListChangeListener.Change change)
			{
				inputList.setItems(inputFiles);

			}
		});

	}

	public void setMainApp(MainApp mainApp)
	{
		this.mainApp = mainApp;
	}

	public void initializeDragAndDrop()
	{
		//Drag and Drop Files to add to input list
		mainApp.getPrimaryStage().getScene().setOnDragOver(new EventHandler<DragEvent>()
		{
			@Override
			public void handle(DragEvent event)
			{
				Dragboard db = event.getDragboard();
				if (db.hasFiles())
				{
					event.acceptTransferModes(TransferMode.COPY);
				}
				else
				{
					event.consume();
				}
			}
		});

		// Dropping over surface
		mainApp.getPrimaryStage().getScene().setOnDragDropped(new EventHandler<DragEvent>()
		{
			@Override
			public void handle(DragEvent event)
			{
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasFiles())
				{
					success = true;
					String filePath = null;
					for (File file : db.getFiles())
					{
						filePath = file.getAbsolutePath();
						addListItem(filePath);
					}
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
	}

	//Function called when browse button is clicked for input files
	@FXML
	public void handleInputBrowse()
	{
		try
		{
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
					"Data files", "*.txt", "*.csv", "*.xls", "*.xlsx");
			fileChooser.getExtensionFilters().add(extFilter);

			//Select multiple files for import
			List<File> files = fileChooser.showOpenMultipleDialog(mainApp.getPrimaryStage());

			for (ListIterator<File> iter = files.listIterator(); iter.hasNext();)
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
		catch (NullPointerException ex)
		{
			System.out.println("No files added");
		}
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

	//Function for removing current selected item from list view
	@FXML
	public void removeSelectedItem()
	{
		int index = inputList.getSelectionModel().getSelectedIndex();
		inputFiles.remove(index);
	}

	@FXML
	public void handleBegin()
	{

	}

	@FXML
	public void handleRemoveAll()
	{
		inputFiles.clear();

	}

	@FXML
	public void handleClose()
	{
		inputFiles.clear();
		System.exit(0);
	}
}
