package be.leerstad.EindwerkChezJava.model;


import java.io.*;
import java.lang.invoke.MethodHandles;
import java.rmi.activation.Activatable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;
import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;

import be.leerstad.EindwerkChezJava.database.ChezJavaDAO;
import be.leerstad.EindwerkChezJava.database.ChezJavaDAOImpl;
import be.leerstad.EindwerkChezJava.Exceptions.*;

public class Cafe {
	private static Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private ChezJavaDAO ChezJavaDAOimpl;
	private Ober activeOber;
	private Table activeTable;
	private Set<Ober> obers = new HashSet<>();
	private Set<Liquid> liquids =  new HashSet<>();
	private List<Table> tables =  new ArrayList<>();
	
	public  Cafe() throws DAOException {
		this.activeOber = new Ober();
		// TODO Auto-generated constructor stub
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
	
	public OrderSet getDailyIncome(LocalDate lc) throws DAOException
	{
		return ChezJavaDAOimpl.getOrder(lc);
	}
	//getest
	public List<Table> getTables() throws ActiveOberNotSetException {
		oberAllowed();
		tables.sort((t1,t2) -> t1.getId()-t2.getId());
		return tables;
	}
	//getest
	public void setActiveTable(Table t) throws ActiveOberNotSetException, TableNotAllowedException
	{
		oberAllowed();
		if (tables.contains(t))
		{
			if (t.getActiveOber().equals(new Ober()) || t.getActiveOber().equals(activeOber)){
				activeTable = tables.stream().filter(table -> table.equals(t)).findFirst().get();
			}
			else
				throw new TableNotAllowedException();
		}
		else
		{
			System.out.println("FOUT");
		}
	}

	public Table getActiveTable() throws ActiveOberNotSetException {
		oberAllowed();
		return activeTable;
	}
	
	private  void tableAllowed() throws TableNotAllowedException
	{
		if (activeTable == null )
		{
			throw new TableNotAllowedException("Active Table Not Set");
		}
		if(!(activeTable.getActiveOber() == null || activeTable.getActiveOber().equals(this.getActiveOber())))
		{
			throw new TableNotAllowedException();
		}
	}
	
	private  void oberAllowed() throws ActiveOberNotSetException
	{
		Ober nulober = new Ober();
		if (this.activeOber.equals(nulober))
		{
			throw new ActiveOberNotSetException();
		}
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
	public double calculateUnpayedOrders() throws ActiveOberNotSetException
	{
		oberAllowed();
		double totaal = 0;
		for (Table table : tables)
		{
			totaal += table.getOrders().calcutateOrders();
		}
		return totaal;
	}
	//getest
	public OrderSet getUnpayedOrders(Ober ober) //throws ActiveOberNotSetException 
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
	public OrderSet getUnpayedOrders() throws ActiveOberNotSetException
	{
		oberAllowed();
		OrderSet orders = new OrderSet();
		for (Table table : tables)
		{
			orders.addAll(table.getOrders());
		}
		return orders;
	}
		

	//getest
	public Set<Order> getPayedOrders() 
	{
		Set<Order> orders = new HashSet<>();
		for (Ober ober : obers)
		{
			orders.addAll(ober.getPayedOrders());
		}
		return orders;
	}
	//getest
	public double calculatePayedOrders() throws ActiveOberNotSetException
	{
		oberAllowed();
		double totaal = 0;
		for (Ober ober : obers)
		{
			totaal += ober.getPayedOrders().calcutateOrders();
		}
		return totaal;
	}
	
	public void SendMail(String attachment, Ober ober) throws MessagingException
	{
		//attachment = "src/be/leerstad/03_-_Java_Basics_-_Classes_and_objects.pdf";
		String message = "Beste, \n Hier vindt U het overzicht van de inkomsten van: \n Groeten \n" + ober;
		MultipartChezJava multipartChezJava= new MultipartChezJava();
		Multipart multipart = new MimeMultipart();
		try
		{
			multipart.addBodyPart(multipartChezJava.addSimpleBodyPart(message, "text/html; charset=utf-8"));
			multipart.addBodyPart(multipartChezJava.addAttachment(attachment));
			multipartChezJava.sendMail(multipart, "cvoleerstadB1@gmail.com", attachment);
		}
		catch (MessagingException e)
		{
			throw e;
		}
	}
	
	
	public LinkedHashMap<Ober, Double> topDrieObers() throws ActiveOberNotSetException
	{
		oberAllowed();
		LinkedHashMap<Ober, Double> mapTopDrie = new LinkedHashMap<>();
		try {
			mapTopDrie = ChezJavaDAOimpl.topDrieOber();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapTopDrie;
	}

	
	public void login(String lastName, String firstName, String password) throws DAOException, DAOloginNotAllowed{
		try {
			Ober ober =	ChezJavaDAOimpl.Login(lastName, firstName, password);
			activeOber = ober;
			obers.add(ober);
			activeTable = null;
		} catch (DAOException | DAOloginNotAllowed e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}
	
	public void logOut() {
		activeTable = null;
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
