/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelconverter.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.apache.pdfbox.util.Splitter;

/**
 *
 * @author Rhys
 */
public class PDFTools
{

	public PDFTools()
	{

	}

	public int count(String inFileName)
	{
		String extension = inFileName.substring(inFileName.lastIndexOf(".") + 1, inFileName.length()).toLowerCase();
		int amountPages = 0;
		if (extension.equals("pdf"))
		{
			PDDocument pdf;
			try
			{
				pdf = PDDocument.load(new File(inFileName));
				amountPages = pdf.getNumberOfPages();
			}
			catch (IOException ex)
			{
				Logger.getLogger(PDFTools.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		return amountPages;
	}

	public int countMultiple(ObservableList<String> fileNames)
	{
		int totalCount = 0;

		for (String currentFileName : fileNames)
		{
			totalCount += count(currentFileName);
		}

		return totalCount;
	}

	public void combine(String outputFolderName, String outputFileName, ObservableList<String> fileNames)
	{
		PDFMergerUtility pdfMerger = new PDFMergerUtility();

		for (String currentFileName : fileNames)
		{
			pdfMerger.addSource(currentFileName);
		}

		pdfMerger.setDestinationFileName(outputFolderName + "\\" + outputFileName + ".pdf");

		try
		{
			pdfMerger.mergeDocuments();
		}
		catch (IOException ex)
		{
			Logger.getLogger(PDFTools.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch (COSVisitorException ex)
		{
			Logger.getLogger(PDFTools.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public void splitLastPage(String inFileName)
	{

		String extension = inFileName.substring(inFileName.lastIndexOf(".") + 1, inFileName.length()).toLowerCase();
		int amountPages = 0;
		if (extension.equals("pdf"))
		{
			PDDocument pdf;
			try
			{
				pdf = PDDocument.load(new File(inFileName));
				amountPages = pdf.getNumberOfPages();

				if (amountPages % 2 == 0)
				{
					// even
				}
				else
				{
					// odd
					Splitter pdfSplitter = new Splitter();
					int splitterPage=amountPages-1;
					
					pdfSplitter.setSplitAtPage(splitterPage);
					
					List<PDDocument> splitPDFs = pdfSplitter.split(pdf);
					
					for(PDDocument splitPDF: splitPDFs)
					{
						
					}
					
				}
				

			}
			catch (IOException ex)
			{
				Logger.getLogger(PDFTools.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}
}
