/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelconverter.model;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
					int splitterPage = amountPages - 1;

					pdfSplitter.setSplitAtPage(splitterPage);

					List<PDDocument> splitPDFs = pdfSplitter.split(pdf);

					int currentDoc = 1;

					for (PDDocument splitPDF : splitPDFs)
					{
						currentDoc++;
						String outputFileName = inFileName.substring(0, inFileName.length() - 4) + currentDoc + ".pdf";
						splitPDF.save(outputFileName);
					}

				}

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

	}

	public int count_itext(String inFileName)
	{
		String extension = inFileName.substring(inFileName.lastIndexOf(".") + 1, inFileName.length()).toLowerCase();
		int amountPages = 0;
		if (extension.equals("pdf"))
		{
			FileInputStream inputStream = null;
			try
			{
				inputStream = new FileInputStream(inFileName);
				PdfReader inputPDF = new PdfReader(inputStream);
				amountPages = inputPDF.getNumberOfPages();
				inputStream.close();
			}
			catch (FileNotFoundException ex)
			{
				Logger.getLogger(PDFTools.class.getName()).log(Level.SEVERE, null, ex);
			}
			catch (IOException ex)
			{
				Logger.getLogger(PDFTools.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		return amountPages;
	}

	public static void split_itext(InputStream inputStream,
								OutputStream outputStream, int fromPage, int toPage)
	{
		Document document = new Document();
		try
		{
			PdfReader inputPDF = new PdfReader(inputStream);

			int totalPages = inputPDF.getNumberOfPages();

			//make fromPage equals to toPage if it is greater
			if (fromPage > toPage)
			{
				fromPage = toPage;
			}
			if (toPage > totalPages)
			{
				toPage = totalPages;
			}

			// Create a writer for the outputstream
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);

			document.open();
			PdfContentByte cb = writer.getDirectContent(); // Holds the PDF data
			PdfImportedPage page;

			while (fromPage <= toPage)
			{
				document.newPage();
				page = writer.getImportedPage(inputPDF, fromPage);
				cb.addTemplate(page, 0, 0);
				fromPage++;
			}
			outputStream.flush();
			document.close();
			outputStream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (document.isOpen())
			{
				document.close();
			}
			try
			{
				if (outputStream != null)
				{
					outputStream.close();
				}
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}
	
	
}
