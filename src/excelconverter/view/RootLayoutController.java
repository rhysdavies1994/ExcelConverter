/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelconverter.view;

import excelconverter.MainApp;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author Rhys
 */
public class RootLayoutController
{

	private ConversionViewController conversionViewController;

	// Reference to the main application
	private MainApp mainApp;
	private BorderPane rootLayout;

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	@FXML
	public void showConversionView()
	{
		try
		{
			// Load conversion view into root panel.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ConversionView.fxml"));
			AnchorPane conversionView = (AnchorPane) loader.load();

			// Set excel overview into the center of root layout.
			rootLayout.setCenter(conversionView);
			

			// Give the controller access to the main app.
			ConversionViewController controller = loader.getController();
			controller.setMainApp(mainApp);

			//Set up scene to allow dropping files in
			controller.initializeDragAndDrop();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	public void showPDFToolsView()
	{
		try
		{
			// Load conversion view into root panel.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PDFToolsView.fxml"));
			AnchorPane PDFToolsView = (AnchorPane) loader.load();

			// Set excel overview into the center of root layout.
			rootLayout.setCenter(PDFToolsView);
			

			// Give the controller access to the main app.
			PDFToolsViewController controller = loader.getController();
			controller.setMainApp(mainApp);
			
			//Set up scene to allow dropping files in
			controller.initializeDragAndDrop();


		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setMainApp(MainApp mainApp)
	{
		this.mainApp = mainApp;
	}
	
	public void setRootLayout(BorderPane rootLayout)
	{
		this.rootLayout = rootLayout;
	}

	public RootLayoutController()
	{

	}

	/**
	 * @return the conversionViewController
	 */
	public ConversionViewController getConversionViewController()
	{
		return conversionViewController;
	}

	/**
	 * @param conversionViewController the conversionViewController to set
	 */
	public void setConversionViewController(ConversionViewController conversionViewController)
	{
		this.conversionViewController = conversionViewController;
	}

	@FXML
	public void handleClose()
	{
		System.exit(0);
	}

	@FXML
	public void handleAbout()
	{
		Dialogs.create()
				.title("About")
				.masthead("Excel Converter")
				.message("Created by Rhys Davies\nDecember 2014")
				.showInformation();
	}

	@FXML
	public void handleHelp()
	{
		Dialogs.create()
				.title("Instructions")
				.masthead("How to use this Program")
				.message("1. Click Add Files, or Drag 'n Drop into Input Files\n2. Choose Output Directory\n3. Choose Filename\n4. Click Begin")
				.showInformation();
	}

}
