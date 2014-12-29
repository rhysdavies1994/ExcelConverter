/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelconverter.view;

import javafx.fxml.FXML;

/**
 *
 * @author Rhys
 */
public class RootLayoutController
{
	private ConversionViewController conversionViewController;

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
		
	}
	
}
