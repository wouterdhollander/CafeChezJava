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

public class Cafe {
	private static Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private ChezJavaDAO ChezJavaDAOimpl;
	private Ober activeOber;
	private Table activeTable = DUMMYTABLE;//in plaats van null
	private Set<Ober> obers = new HashSet<>();
	private Set<Liquid> liquids =  new HashSet<>();
	private List<Table> tables =  new ArrayList<>();
	private static final Table DUMMYTABLE = new Table(-5);
	public  Cafe() throws DAOException 
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
	public List<Table> getTables() {
		if (!oberAllowed())
		{
			List<Table> tablesDummy = new ArrayList<>();
			tablesDummy.add(DUMMYTABLE);
			return tablesDummy;
		}
		tables.sort((t1,t2) -> t1.getId()-t2.getId());
		return tables;
	}
	//getest
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
			System.out.println("FOUT");
		}
	}

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
	public Ober getActiveOber() 
	{
		return activeOber;
	}
	//getest
	public double calculateUnpayedOrders(Ober ober)
	{	
		return tables.stream().filter(t -> t.getActiveOber().equals(ober)).mapToDouble(t -> t.getOrders().calcutateOrders()).sum();
	}

	//getest
	public double calculateUnpayedOrders()
	{
		if (!oberAllowed())
		{
			return -100;
		}
		double totaal = 0;
		for (Table table : tables)
		{
			totaal += table.getOrders().calcutateOrders();
		}
		return totaal;
	}
	
	//getest
	public OrderSet getUnpayedOrders(Ober ober)
	{
		OrderSet orderset = new OrderSet();
		for(Table table: tables)
		{
			if (table.getActiveOber().equals(ober))
			{
				orderset.addAll(table.getOrders());
			}
		}
		return orderset ;
	}
	//getest
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
	public double calculatePayedOrders()
	{	
		double totaal = 0;
		if (oberAllowed()){
			for (Ober ober : obers)
			{
				totaal += ober.getPayedOrders().calcutateOrders();
			}
		}
		return totaal;
	}
	//getest
	public String createPDF(Collection<Order> orders, boolean open) throws FileNotFoundException, DocumentException
	{
		LocalDate date = LocalDate.now();
		PDFgenerator pdfgen = new PDFgenerator("CafeOverview-" + date, activeOber.toString());
	  	pdfgen.addTitlePage("Cafe Overview");
	  	pdfgen.addContent("Overzicht Order", orders);
	  	pdfgen.Create();
	  	if (open)
	  	{
	  		pdfgen.Open();
	  	}
	  return pdfgen.getFileLocation();
	}
	
	public boolean SendMail(String attachment)
	{
		if (oberAllowed())
		{
			//attachment = "src/be/leerstad/03_-_Java_Basics_-_Classes_and_objects.pdf";
			String message = "Beste, \n Hier vindt U het overzicht van de inkomsten van: \n Groeten \n" + activeOber;
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
	
	public OrderSet getIncome(Ober ober) throws DAOException
	{
		OrderSet orders = new OrderSet();
		if (oberAllowed())
		{
			orders = ChezJavaDAOimpl.getOrder(ober);
		}
		return orders;
	}
	
	public OrderSet getIncome(LocalDate lc) throws DAOException
	{
		return ChezJavaDAOimpl.getOrder(lc);
	}
	
	public LinkedHashMap<Ober, Double> topDrieObers() throws DAOException
	{
		LinkedHashMap<Ober, Double> mapTopDrie = new LinkedHashMap<>();
		if (oberAllowed())
		{
			mapTopDrie = ChezJavaDAOimpl.topDrieOber();
		}
		return mapTopDrie;
	}

	//getest
	public void login(String lastName, String firstName, String password) throws DAOException, DAOloginNotAllowed{
		try {
			Ober ober =	ChezJavaDAOimpl.Login(lastName, firstName, password);
			activeOber = ober;
			obers.add(ober);
			activeTable = DUMMYTABLE;
		} catch (DAOException | DAOloginNotAllowed e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}
	
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
	
	public void close() throws DAOException
	{
		logOut();
		ChezJavaDAOimpl = ChezJavaDAOImpl.getInstance();
		ChezJavaDAOimpl.insertOrders(getPayedOrders());

		removePayedOrders();
		
		Serializer ser = new Serializer();
		ser.serialise(tables);	
	}

	public Set<Liquid> getLiquids()  {
		//oberAllowed();
		return liquids;
	}

	private void setLiquids(Set<Liquid> setLiquid) {
		this.liquids = setLiquid;
	}
	
}
