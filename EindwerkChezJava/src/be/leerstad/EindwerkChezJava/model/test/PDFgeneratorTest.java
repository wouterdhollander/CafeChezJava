package be.leerstad.EindwerkChezJava.model.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.DocumentException;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
import be.leerstad.EindwerkChezJava.model.Cafe;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.Ober;
import be.leerstad.EindwerkChezJava.model.Order;
import be.leerstad.EindwerkChezJava.model.PDFgenerator;

public class PDFgeneratorTest {
	private Cafe cafe;
	private Liquid l1;
	private Liquid l2;
	private Order o1;
	private Order o2;
	private Order o3;
	private Ober ober1;
	private Ober ober2;
	ArrayList<Order> orders = new ArrayList<>();
	@Before
	public void setUp(){
		 ober1 = new Ober(1, "Peters", "Wout","password");
		ober2 = new Ober(2, "Segers", "Nathalie", "password");
			Liquid l1 = new Liquid(1, "Cola", 2.0);
			Liquid l2 = new Liquid(2, "Bier", 3.0); 
			 
			try {
				o1 = new Order(l1, 2, ober1);
				o2 = new Order(l2, 2, ober1);
				o3 = new Order(l1, 1, ober2);	
				 
			  orders.add(o1);
			  orders.add(o2);
			  orders.add(o3);
			} catch (QuantityToLowException | QuantityZeroException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
	}
	
	@Test
	public void testPDFgenerator() throws DocumentException, FileNotFoundException 
	{
		PDFgenerator pdfgen = new PDFgenerator("CafeOverview", "Wouter Dhollander");
		pdfgen.addTitlePage("Cafe Overview");
		pdfgen.addContent("Overzicht Order", orders);
		pdfgen.Create();
		  
		String Filename = pdfgen.getFileLocation();
		  
		File f = new File(Filename);
		assertTrue(f.exists() && !f.isDirectory());
	}
	
	@Test
	public void testOpen() throws DocumentException, FileNotFoundException 
	{
		PDFgenerator pdfgen = new PDFgenerator("CafeOverview", "Wouter Dhollander");
		pdfgen.Open();
	}
}
