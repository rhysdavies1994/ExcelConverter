package excelconverter;

import excelconverter.view.ConversionViewController;
import excelconverter.view.RootLayoutController;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application
{

	//Class Fields
	private Stage primaryStage;
	private BorderPane rootLayout;

	public MainApp()
	{

	}

	//Main Function - ran at start
	public static void main(String[] args)
	{
		launch(args);
	}

	public Stage getPrimaryStage()
	{
		return primaryStage;
	}

	@Override
	public void start(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Excel Converter");

		//Set Application Icon
		//this.primaryStage.getIcons().add(new Image("file:Resources/Images/Address_Book.png"));
		initRootLayout();

	}

	public void initRootLayout()
	{
		try
		{
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class
					.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();

			//Set up root layout controller
			RootLayoutController rootController = loader.getController();
			rootController.setMainApp(this);
			rootController.setRootLayout(rootLayout);
			
			rootController.showConversionView();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	

}
