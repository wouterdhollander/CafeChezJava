package be.leerstad.EindwerkChezJava.model;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import be.leerstad.EindwerkChezJava.Exceptions.ActiveOberNotSetException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;

public class Ober implements Serializable{
	private static Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private int id;
	private String lastName;
	private String firstName;
	private String password;
	private OrderSet payedOrders =  new OrderSet();
	
	public Ober(int id, String lastName, String firstName, String password) {
		this.setId(id);
		this.setLastName(lastName);
		this.setFirstName(firstName);
		this.setPassword(password);
		logger.info(this.toString() + " created" );
	}
	
	public Ober() {
		this.setId(-5);
		this.setLastName("no");
		this.setFirstName("one");
		this.setPassword("password");
		logger.info(this.toString() + " created" );
	}
	
	
	private boolean isTableAllowed(Table table)
	{
		return table.getActiveOber().equals(new Ober()) || table.getActiveOber().equals(this);
	}
	
	public boolean makeOrder(Liquid liquid, int quantity, Table table) throws QuantityToLowException
	{
		
		if (isTableAllowed(table))
		{
			Order order;
			try {
				order = new Order(liquid, quantity,this);

				return table.getOrders().add(order);
			} catch (QuantityZeroException e) {
				// TODO Auto-generated catch block
				return false;
			}
		}
		return false;
	}
	
	public boolean removeOrder(Order order, Table table)
	{
		if (isTableAllowed(table))
		{
			return table.getOrders().remove(order);
		}
		return false;
	}

	public OrderSet getPayedOrders() {
		return payedOrders;
	}

	private void setPayedOrders(OrderSet payedOrders) {
		this.payedOrders = payedOrders;
	}

	public String payOrders(Table table)
	{
		if (!isTableAllowed(table))
		{
			return this + " mag deze tafel niet bestellen";
		}
		OrderSet ordersTable = table.getOrders();
		String printout = ordersTable.printOutPayment();
		payedOrders.addAll(ordersTable);
		ordersTable.clear();
		
		return printout;
	}
	@Override
	public String toString() {
		return "Ober: " + lastName + " " + firstName;
	}

	public int getId() {
		return id;
	}
	private void setId(int id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ober other = (Ober) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getLastName() {
		return lastName;
	}
	private void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPassword() {
		return password;
	}
	private void setPassword(String password) {
		this.password = password;
	}







}
