/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelconverter.view;

import com.smartxls.WorkBook;
import excelconverter.MainApp;
import excelconverter.model.DataFile;
import excelconverter.model.DataReader;
import excelconverter.model.DataWriter;
import excelconverter.model.PDFTools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.controlsfx.dialog.Dialogs;

public class PDFToolsViewController
{

	@FXML
	private ListView<String> inputList;

	private ObservableList<String> inputFiles;

	// Reference to the main application
	private MainApp mainApp;

	private PDFTools pdfTools;

	private double completedProgressItems;
	private double totalProgressItems;
	
	@FXML
	private Accordion pdfPrograms;
	
	@FXML
	private TitledPane countPane;
	@FXML
	private TitledPane combinePane;
	@FXML
	private TitledPane splitOddPagesPane;

	//Class Fields for Count
	private int totalCount = 0;
	@FXML
	private TextField countOutputFolder;
	@FXML
	private TextField countOutputName;

	//Class Fields for Combine
	@FXML
	private TextField combineOutputFolder;
	@FXML
	private TextField combineOutputName;

	//Class Fields for Split Odd Pages
	@FXML
	private TextField splitOddPageOutputFolder;
	@FXML
	private CheckBox outputOriginalCheckbox;
	@FXML
	private CheckBox deleteOriginalCheckbox;
	
	private int processedFiles;
	private int totalFiles;

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

		pdfTools = new PDFTools();

		//Set defaults for output textfields
		countOutputName.setText("PDFCount");
		countOutputFolder.setText(System.getProperty("user.home") + "\\Desktop");

		//Set defaults for output textfields
		combineOutputName.setText("CombinedPDFs");
		combineOutputFolder.setText(System.getProperty("user.home") + "\\Desktop");

		//Set defaults for output textfields
		splitOddPageOutputFolder.setText(System.getProperty("user.home") + "\\Desktop");
		outputOriginalCheckbox.setSelected(true);
		deleteOriginalCheckbox.setSelected(true);
		processedFiles=0;
		totalFiles=0;

	}

	public void setMainApp(MainApp mainApp)
	{
		this.mainApp = mainApp;
	}

	//Function called when browse button is clicked for count output folder
	@FXML
	public void handleCountBrowse()
	{
		DirectoryChooser directoryChooser = new DirectoryChooser();

		File selectedDirectory = directoryChooser.showDialog(mainApp.getPrimaryStage());

		countOutputFolder.setText(selectedDirectory.getAbsolutePath());
	}

	//Function called when browse button is clicked for combine output folder
	@FXML
	public void handleCombineBrowse()
	{
		DirectoryChooser directoryChooser = new DirectoryChooser();

		File selectedDirectory = directoryChooser.showDialog(mainApp.getPrimaryStage());

		combineOutputFolder.setText(selectedDirectory.getAbsolutePath());
	}

	//Function called when browse button is clicked for split odd page output folder
	@FXML
	public void handleSplitOddPageBrowse()
	{
		DirectoryChooser directoryChooser = new DirectoryChooser();

		File selectedDirectory = directoryChooser.showDialog(mainApp.getPrimaryStage());

		splitOddPageOutputFolder.setText(selectedDirectory.getAbsolutePath());
	}

	public void handleCount()
	{
		completedProgressItems = 0;
		totalProgressItems = 1;
		ObservableList<String> items = inputList.getItems();

		//Initialise Data Writer
		DataWriter dataWriter = new DataWriter();

		//Check if output fields are not empty and file can be created at location
		boolean canCreateFile = true;
		canCreateFile = dataWriter.initialCheck(countOutputFolder.getText(), countOutputName.getText());

		//If atleast 1 input item and can create file, process it
		if (items.size() > 0 && canCreateFile == true)
		{
			Service<Void> service = new Service<Void>()
			{

				@Override
				protected Task<Void> createTask()
				{
					return new Task<Void>()
					{

						@Override
						protected Void call()
								throws InterruptedException
						{
							try
							{
								updateProgress(completedProgressItems, totalProgressItems);

								updateMessage("Preparing to Count..");

								totalProgressItems += inputFiles.size();

								//Create workbook for storing data
								DataFile spreadsheet = new DataFile();

								//Start at first column and row and set headers
								totalCount = 0;
								int row = 0;
								int column = 0;

								ArrayList<String> headerRow = new ArrayList<String>();

								headerRow.add("PDF Files");
								headerRow.add("Page Count");

								spreadsheet.addRow(headerRow);

								for (String currentFileName : inputFiles)
								{
									ArrayList<String> currentRow = new ArrayList<String>();

									updateMessage("Counting File " + spreadsheet.getRowCount() + " of " + inputFiles.size() + "\nTotal Pages: " + totalCount);
									String directFileName = currentFileName.substring(currentFileName.lastIndexOf("\\") + 1, currentFileName.length());
									System.out.println(directFileName);

									//Start at first column in each row
									column = 0;

									//Add file name to first cell in row
									currentRow.add(directFileName);
									column++;

									//Add amount of pages in pdf to second cell in row
									int amountPages = pdfTools.count_itext(currentFileName);
									totalCount += amountPages;
									String amountPagesString = String.valueOf(amountPages);
									currentRow.add(amountPagesString);

									//Increment the row count
									spreadsheet.addRow(currentRow);
									row++;

									completedProgressItems++;
									updateProgress(completedProgressItems, totalProgressItems);
								}

								//Add total to end of files
								ArrayList<String> totalRow = new ArrayList<String>();
								column = 0;
								totalRow.add("Total");
								column++;

								//Add total count to end of files
								String totalPages = String.valueOf(totalCount);
								totalRow.add(totalPages);

								spreadsheet.addRow(totalRow);

								System.out.println("Finished Reading Files");

								//Write workbook to output file
								updateMessage("Writing Data to File");

								dataWriter.writeFileTXT(countOutputFolder.getText(), countOutputName.getText(), spreadsheet);

								completedProgressItems++;
								updateProgress(completedProgressItems, totalProgressItems);

							}
							catch (Exception ex)
							{
								Logger.getLogger(PDFToolsViewController.class.getName()).log(Level.SEVERE, null, ex);
							}

							return null;
						}
					};
				}
			};
			Dialogs.create()
					.owner(mainApp.getPrimaryStage())
					.title("Progress Dialog")
					.masthead("Counting Files")
					.showWorkerProgress(service);

			service.setOnSucceeded(new EventHandler<WorkerStateEvent>()
			{
				public void handle(WorkerStateEvent event)
				{
					Platform.runLater(new Runnable()
					{
						public void run()
						{
							Dialogs.create()
									.owner(mainApp.getPrimaryStage())
									.title("Completed")
									.masthead("PDF Files have been counted")
									.message("Total Pages: " + totalCount + "\n\nYou can find the breakdown at :\n" + countOutputFolder.getText() + "\\" + countOutputName.getText() + ".txt")
									.showInformation();
						}
					});
				}
			});

			service.start();
		}
		else if (canCreateFile && items.size() <= 0)
		{

			Dialogs.create()
					.title("No Input Files")
					.masthead("There were no files selected")
					.message("1. Click Add Files, or Drag 'n Drop into Input Files\n2. Click Count PDF's")
					.showError();

		}
	}

	public void handleCombine()
	{
		
		completedProgressItems = 0;
		totalProgressItems = 1;
		ObservableList<String> items = inputList.getItems();

		//Initialise Data Writer
		DataWriter dataWriter = new DataWriter();

		//Check if output fields are not empty and file can be created at location
		boolean canCreateFile = true;
		canCreateFile = dataWriter.initialCheck(combineOutputFolder.getText(), combineOutputName.getText());

		//If atleast 1 input item and can create file, process it
		if (items.size() > 0 && canCreateFile == true)
		{
			Service<Void> service = new Service<Void>()
			{

				@Override
				protected Task<Void> createTask()
				{
					return new Task<Void>()
					{

						@Override
						protected Void call()
								throws InterruptedException
						{

							updateMessage("Combining PDF Files.. This may take a minute");

							pdfTools.combine(combineOutputFolder.getText(), combineOutputName.getText(), items);

							completedProgressItems++;
							updateProgress(completedProgressItems, totalProgressItems);

							return null;
						}
					};
				}
			};
			Dialogs.create()
					.owner(mainApp.getPrimaryStage())
					.title("Progress Dialog")
					.masthead("Combining Files")
					.showWorkerProgress(service);

			service.setOnSucceeded(new EventHandler<WorkerStateEvent>()
			{
				public void handle(WorkerStateEvent event)
				{
					Platform.runLater(new Runnable()
					{
						public void run()
						{
							Dialogs.create()
									.owner(mainApp.getPrimaryStage())
									.title("Completed")
									.masthead("PDF Files have been Combined")
									.message("You can find the combined file at: \n" + combineOutputFolder.getText() + "\\" + combineOutputName.getText() + ".pdf")
									.showInformation();
						}
					});
				}
			});

			service.start();
		}
		else if (canCreateFile && items.size() <= 0)
		{

			Dialogs.create()
					.title("No Input Files")
					.masthead("There were no files selected")
					.message("1. Click Add Files, or Drag 'n Drop into Input Files\n2. Click Count PDF's")
					.showError();

		}
	}

	public void handleSplitOddPages()
	{
		processedFiles=0;
		totalFiles=0;
		completedProgressItems = 0;
		totalProgressItems = 1;
		ObservableList<String> items = inputList.getItems();

		//Initialise Data Writer
		DataWriter dataWriter = new DataWriter();

		//Check if output fields are not empty and file can be created at location
		boolean canCreateFile = true;
		canCreateFile = dataWriter.initialCheck(combineOutputFolder.getText(), combineOutputName.getText());

		//If atleast 1 input item and can create file, process it
		if (items.size() > 0 && canCreateFile == true)
		{
			Service<Void> service = new Service<Void>()
			{

				@Override
				protected Task<Void> createTask()
				{
					return new Task<Void>()
					{

						@Override
						protected Void call()
								throws InterruptedException
						{
							int currentFileIndex = 0;
							totalProgressItems = inputFiles.size();
							totalFiles=(int)totalProgressItems;
							for (String currentFileName : inputFiles)
							{
								updateMessage("Processing File "+((int)completedProgressItems+1)+" of "+(int)totalProgressItems);
								updateProgress(completedProgressItems, totalProgressItems);
								
								String extension = currentFileName.substring(currentFileName.lastIndexOf(".") + 1, currentFileName.length()).toLowerCase();
								if (extension.equals("pdf"))
								{
									FileInputStream inputStreamOrig = null;
									FileInputStream inputStreamNew = null;
									int amountPages = amountPages = pdfTools.count_itext(currentFileName);
									
									String actualFileName = currentFileName.substring(currentFileName.lastIndexOf("\\") + 1, currentFileName.length());
									System.out.println(actualFileName);
									String outputFolderName;
									
									if(outputOriginalCheckbox.isSelected())
									{
										
										outputFolderName=currentFileName.substring(0,currentFileName.lastIndexOf("\\"));
										System.out.println(outputFolderName);
									}
									else
									{
										
										outputFolderName=splitOddPageOutputFolder.getText();
									}
									
									

									if (amountPages > 1 && amountPages % 2 == 0)
									{
										//even
									}
									else if(amountPages > 1)
									{
										//odd

										try
										{
											inputStreamOrig = new FileInputStream(currentFileName);

											String outputNameOrig = outputFolderName+"\\"+actualFileName.substring(0, actualFileName.length() - 4) + "_Original.pdf";
											System.out.println(outputNameOrig);
											FileOutputStream outputStreamOrig = new FileOutputStream(outputNameOrig);

											pdfTools.split_itext(inputStreamOrig, outputStreamOrig, 1, amountPages - 1);

											inputStreamNew = new FileInputStream(currentFileName);

											String outputNameNew = outputFolderName+"\\"+actualFileName.substring(0, actualFileName.length() - 4) + "_LastPage.pdf";
											FileOutputStream outputStreamNew = new FileOutputStream(outputNameNew);

											pdfTools.split_itext(inputStreamNew, outputStreamNew, amountPages, amountPages);

											inputStreamOrig.close();
											inputStreamNew.close();
											
											
											processedFiles++;
											
											if(deleteOriginalCheckbox.isSelected())
											{
												File deleteFile = new File(currentFileName);
												deleteFile.delete();
											}
											
											
											
										}
										catch (FileNotFoundException ex)
										{
											Logger.getLogger(PDFToolsViewController.class.getName()).log(Level.SEVERE, null, ex);
										}
										catch (IOException ex)
										{
											Logger.getLogger(PDFToolsViewController.class.getName()).log(Level.SEVERE, null, ex);
										}
									}

								}
								
								completedProgressItems++;
								
							}
							
							return null;
						}
					};
				}
			};
			Dialogs.create()
					.owner(mainApp.getPrimaryStage())
					.title("Progress Dialog")
					.masthead("Splitting Last Page for Odd Files")
					.showWorkerProgress(service);

			service.setOnSucceeded(new EventHandler<WorkerStateEvent>()
			{
				public void handle(WorkerStateEvent event)
				{
					Platform.runLater(new Runnable()
					{
						public void run()
						{
							Dialogs.create()
									.owner(mainApp.getPrimaryStage())
									.title("Completed")
									.masthead("Splitting Finished")
									.message(processedFiles+" of "+totalFiles+"  files have been split, You can find them in original folder")
									.showInformation();
						}
					});
				}
			});

			service.start();
		}
		else if (canCreateFile && items.size() <= 0)
		{

			Dialogs.create()
					.title("No Input Files")
					.masthead("There were no files selected")
					.message("1. Click Add Files, or Drag 'n Drop into Input Files\n2. Click Count PDF's")
					.showError();

		}
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
					"PDF Files", "*.pdf");
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
