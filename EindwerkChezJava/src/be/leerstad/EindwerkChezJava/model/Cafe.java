package be.leerstad.EindwerkChezJava.model;


import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;
import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;

import com.itextpdf.text.DocumentException;

import be.leerstad.EindwerkChezJava.database.ChezJavaDAO;
import be.leerstad.EindwerkChezJava.database.ChezJavaDAOImpl;
import be.leerstad.EindwerkChezJava.Exceptions.*;
/**
 * @author Wouter
 * @version 0.1
 * @since 30/05/2016
 */
public class Cafe {
	private static Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private ChezJavaDAO ChezJavaDAOimpl;
	private Ober activeOber;
	private Table activeTable = DUMMYTABLE;//in plaats van null
	private Set<Ober> obers = new HashSet<>();
	private Set<Liquid> liquids =  new HashSet<>();
	private List<Table> tables =  new ArrayList<>();
	private static final Table DUMMYTABLE = new Table(-5);
	/**
	 * @throws DAOException if the cafe can not be created due to DB errors
	 */
	public Cafe() throws InternalException 
	{
		this.activeOber = new Ober();
		ChezJavaDAOimpl = ChezJavaDAOImpl.getInstance();
		Serializer ser = new Serializer();
		List<Table> setDesialiseTables;
		try {
			setDesialiseTables = ser.deserialise();
			this.tables = setDesialiseTables;
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			for (int i = 0; i < 9; i++) {
				this.tables.add(new Table(i));
			}
		}
		this.setLiquids(ChezJavaDAOimpl.getLiquids());
	}
	

	//getest
	/**
	 * Returns a list-object of all Table objecten in this cafe
	 * when you are not logged in, you get a empty list in return. 
	 * @return a list of all tables
	 */
	public List<Table> getTables() {
		if (!oberAllowed())
		{
			List<Table> tablesDummy = new ArrayList<>();
			//tablesDummy.add(DUMMYTABLE);
			return tablesDummy;
		}
		tables.sort((t1,t2) -> t1.getId()-t2.getId());
		return tables;
	}
	//getest
	/**
	 * Makes a specific Table active in the cafe
	 * when you are not logged in,  a dummy table is set. 
	 * @param t the Table-object that will become the active table
	 */
	public void setActiveTable(Table t)
	{
		if (tables.contains(t))
		{
			if (t.getActiveOber().equals(new Ober()) || t.getActiveOber().equals(activeOber)){
				activeTable = tables.stream().filter(table -> table.equals(t)).findFirst().get();
			}
			else
			{
				activeTable = DUMMYTABLE;
			}
		}
		else
		{
			activeTable = DUMMYTABLE;
			//System.out.println("FOUT");
		}
	}

	/**
	 * Return the active table 
	 * or a Dummytable if you are not logged in
	 * @return a Table-object that is the active table
	 */
	public Table getActiveTable() {
		if (!oberAllowed())
		{
			return DUMMYTABLE;
		}
		return activeTable;
	}

	
	private  boolean oberAllowed()
	{
		Ober nulober = new Ober();
		return !this.activeOber.equals(nulober);

	}
//getest
	/**
	 * Return the active Ober of the Cafe
	 * @return the Active logged in Ober (Ober-object).
	 */
	public Ober getActiveOber() 
	{
		return activeOber;
	}
	//getest
	/**
	 * Return a value of all unpayed orders of all tables of an ober
	 * @param the Ober-object
	 * @return the sum of all unpayed orders of a the specific ober
	 */
	public double calculateUnpayedOrders(Ober ober)
	{	
		return tables.stream().filter(t -> t.getActiveOber().equals(ober)).mapToDouble(t -> t.getOrders().calcutateOrders()).sum();
	}

	//getest
	/**
	 * Return a value of all unpayed orders of all tables
	 * if you are not logged in -10000 is givin
	 * @return the sum of all unpayed orders.
	 */
	public double calculateUnpayedOrders()
	{
		if (!oberAllowed())
		{
			return -100000;
		}
		double totaal = 0;
		for (Table table : tables)
		{
			totaal += table.getOrders().calcutateOrders();
		}
		return totaal;
	}
	
	//getest
	/**
	 * Return all unpayed orders of a specific ober
	 * if you are not logged in an empty set is givin
	 * @param ober the Ober-object
	 * @return an OrdersSet object with all unpayed orders  a the specific ober
	 */
	public OrderSet getUnpayedOrders(Ober ober)
	{
		OrderSet orderset = new OrderSet();
		if (oberAllowed())
		{
			for(Table table: tables)
			{
				if (table.getActiveOber().equals(ober))
				{
					orderset.addAll(table.getOrders());
				}
			}
		}
		return orderset ;
	}
	//getest
	/**
	 * Return all unpayed orders of all tables, Obers
	 * if you are not logged in an empty set is givin
	 * @return an OrdersSet object with all unpayed orders
	 */
	public OrderSet getUnpayedOrders()
	{
		OrderSet orders = new OrderSet();
		if (oberAllowed())
		{
			for (Table table : tables)
			{
				orders.addAll(table.getOrders());
			}
		}
		return orders;
	}

	//getest
	/**
	 * Return all payed orders of all Obers. 
	 * These are the orders that still are in the possesion of the obers.
	 * So the orders that are not givin to the cashdesk 
	 * if you are not logged in an empty set is givin
	 * @return an OrdersSet object with all payed orders of the obers
	 */
	public Set<Order> getPayedOrders() 
	{
		Set<Order> orders = new HashSet<>();
		if (oberAllowed())
		{
			for (Ober ober : obers)
			{
				orders.addAll(ober.getPayedOrders());
			}
		}
		return orders;
	}
	
	//getest
	/**
	 * Return the sum of all payed orders of all tables
	 * These are the orders that still are in the possesion of the obers.
	 * So the orders that are not givin to the cashdesk 
	 * if you are not logged in -10000 is givin
	 * @return the sum of all payed orders in the possession of the obers.
	 */
	public double calculatePayedOrders()
	{	
		if (!oberAllowed())
		{
			return -100000;
		}
		double totaal = 0;
		for (Ober ober : obers)
		{
			totaal += ober.getPayedOrders().calcutateOrders();
		}
		return totaal;
	}
	//getest
	/**
	 * @param orders the collection of orders you want to have in your PDF
	 * @param openAfterCreation true if you want to open the createdPDF
	 * @return A string that is the path of the file
	 * @throws InternalException 
	 * @throws InternalException  if an internal error occurred (File is open, document not found,...)
	 */
	public String createPDF(Collection<Order> orders, boolean openAfterCreation) throws InternalException
	{
		String datestring = "";
		if (orders.iterator().hasNext())
		{
			LocalDate date = orders.iterator().next().getDate();
			datestring = date.toString();	
		}
		PDFgenerator pdfgen;
		try {
			pdfgen = new PDFgenerator("CafeOverview-" + datestring, activeOber.toString());

	  	pdfgen.addTitlePage("Cafe Overview");
	  	pdfgen.addContent("Overzicht Order", orders);
	  	pdfgen.Create();
	  	if (openAfterCreation)
	  	{
	  		pdfgen.Open();
	  	}
		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			throw new InternalException();
		}
		
		return pdfgen.getFileLocation();
	}
	
	/**
	 * Sends a standard mail to the standard mail adress from a standard mail adress
	 * @param attachment a string that represents the path of the attachment
	 * @return true if the mail is succesfully send. false if it was not possible to send the mail. (ober not logged in, error sending mail)
	 */
	public boolean SendMail(String attachment)
	{
		if (oberAllowed())
		{
			//attachment = "src/be/leerstad/03_-_Java_Basics_-_Classes_and_objects.pdf";
			String message = "Beste, \n Hier vindt U het overzicht van de inkomsten van: \n Groeten \n";
			MultipartChezJava multipartChezJava= new MultipartChezJava();
			Multipart multipart = new MimeMultipart();
			try
			{
				multipart.addBodyPart(multipartChezJava.addSimpleBodyPart(message, "text/html; charset=utf-8"));
				multipart.addBodyPart(multipartChezJava.addAttachment(attachment));
				multipartChezJava.sendMail(multipart, "cvoleerstadB1@gmail.com", attachment);
				return true;
			}
			catch (MessagingException e)
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Return all payed orders of a specific ober.
	 * These are the orders that are NOT in the possesion of the obers.
	 * So the orders that are givin to the cashdesk 
	 * if you are not logged in an empty set is givin
	 * @param ober the ober-object where we want all the payed orders from
	 * @return an OrderSet-object with all the payed orders in the cashdek of an ober
	 * @throws InternalException  if an internal error occurred (DB errors,...)
	 */
	public OrderSet getIncome(Ober ober) throws InternalException
	{
		OrderSet orders = new OrderSet();
		if (oberAllowed())
		{
			orders = ChezJavaDAOimpl.getOrder(ober);
		}
		return orders;
	}
	
	/**
	 * Return all payed orders of a specific date.
	 * These are the orders that are NOT in the possesion of the obers.
	 * So the orders that are givin to the cashdesk 
	 * if you are not logged in an empty set is givin
	 * @param localDate the specific date we want the ordersset
	 * @return an OrderSet-object with all the payed orders in the cashdek of the specific date
	 * @throws InternalException  if an internal error occurred (DB errors,...)
	 */
	public OrderSet getIncome(LocalDate localDate) throws InternalException
	{
		OrderSet orders = new OrderSet();
		if (oberAllowed())
		{
			orders = ChezJavaDAOimpl.getOrder(localDate);
		}
		return orders;
	}
	
	/**
	 * Return a linkedHashmap the three best obers order by highest income
	 * the keys are the representing Obers
	 * the values are the representing sums of the orders in the cashdesk
	 * @return the three best obers
	 * @throws InternalException  if an internal error occurred (DB errors,...)
	 */
	public LinkedHashMap<Ober, Double> topDrieObers() throws InternalException
	{
		LinkedHashMap<Ober, Double> mapTopDrie = new LinkedHashMap<>();
		if (oberAllowed())
		{
			mapTopDrie = ChezJavaDAOimpl.topObers(3);
		}
		return mapTopDrie;
	}

	//getest
	/**
	 * Login system in the Cafe Applicatie
	 * @param lastName the last name
	 * @param firstName the first name
	 * @param password the password
	 * @return true if you're logged in, false if you are not logged in.
	 * @throws InternalException if an internal error occurred (DB errors,...)
	 */
	public boolean login(String lastName, String firstName, String password) throws InternalException
	{
		try {
			Ober ober =	ChezJavaDAOimpl.Login(lastName, firstName, password);
			if (ober.equals(new Ober()))//dummyober
			{
				return false;
			}
			activeOber = ober;
			obers.add(ober);
			activeTable = DUMMYTABLE;
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			throw new InternalException();
		}
		return true;
	}
	
	/**
	 * Logging out of the cafe
	 * the active table and the active ober are set to dummy objects.
	 */
	public void logOut() {
		activeTable = DUMMYTABLE;
		this.activeOber = new Ober();
	}
	
	private void removePayedOrders()
	{
		for (Ober ober : obers) {
			ober.getPayedOrders().clear();
		}
	}
	
	/**
	 * closes the cafe. exports the payed orders to the DB (= the cashdesk)
	 * serialise the unpayed order 
	 * @throws InternalException occures when the serialisetion or the export to the DB isn't fullfilled
	 */
	public void close() throws InternalException
	{
		logOut();
		ChezJavaDAOimpl = ChezJavaDAOImpl.getInstance();
		ChezJavaDAOimpl.insertOrders(getPayedOrders());

		removePayedOrders();
		
		Serializer ser = new Serializer();
		ser.serialise(tables);	
	}

	/**
	 * Return all the liquids in the cafe
	 * @return a set of liquid objects
	 */
	public Set<Liquid> getLiquids()  {
		//oberAllowed();
		return liquids;
	}

	private void setLiquids(Set<Liquid> setLiquid) {
		this.liquids = setLiquid;
	}
	
}
