package be.leerstad.EindwerkChezJava.model;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
/**
 * @author wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 */
public class Ober implements Serializable{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private int id;
	private String lastName;
	private String firstName;
	private String password;
	private OrderSet payedOrders =  new OrderSet();
	
	/**
	 * @param id the ID of the Ober
	 * @param lastName the lastname of the ober
	 * @param firstName the firstname of the ober
	 * @param password the password of the ober
	 */
	public Ober(int id, String lastName, String firstName, String password) {
		this.setId(id);
		this.setLastName(lastName);
		this.setFirstName(firstName);
		this.setPassword(password);
		//logger.info(this.toString() + " created" );
	}
	
	/**
	 * Creates a dummy Ober with ID -5
	 */
	public Ober() {
		this.setId(-5);
		this.setLastName("no");
		this.setFirstName("one");
		this.setPassword("password");
	}
	
	/**
	 * Return if the table is active for this ober, not allowed or free
	 * @param table the table-object
	 * @return active if the table is active, not allowed or free for this ober,
	 */
	public ObersTableStatus tableStatus(Table table)
	{
		ObersTableStatus status = ObersTableStatus.NOTALLOWED;
		if ((table.equals(new Table(-5))))
		{
			status = ObersTableStatus.NOTALLOWED;
		}
		else if (table.getActiveOber().equals(new Ober())) 
		{
			status = ObersTableStatus.FREE;
		}
		else if (table.getActiveOber().equals(this))
		{
			status = ObersTableStatus.ACTIVE;
		}

		return status;
	}
	
	private boolean isTableAllowed(Table table)
	{
		
		//boolean allowed = (!table.equals(new Table(-5))) &&( table.getActiveOber().equals(new Ober()) || table.getActiveOber().equals(this));
		ObersTableStatus status = tableStatus(table);
		switch (status)
		{
			case NOTALLOWED:
				logger.info(this.toString() + " tried using table" + table);
				return false;
				//break;
			default:
				return true;
	
		}
		//return allowed;
	}
	
	/**
	 * make an order for a specific Table.
	 * returns true if the order is allowed.
	 * @param liquid the liquid that is ordered
	 * @param quantity the quantity that is ordered
	 * @param table the table who orders
	 * @return true if the order is done and allowed
	 * @throws QuantityToLowException when the quantity of the order is below zero. thats not possible
	 */
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
	
	/**
	 * deletes an order for a specific Table.
	 * returns true if the order is allowed to delete
	 * @param order the order we want to delete
	 * @param table the table we want to delete the order from
	 * @return true if allowed
	 */
	public boolean removeOrder(Order order, Table table)
	{
		if (isTableAllowed(table))
		{
			return table.getOrders().remove(order);
		}
		return false;
	}

	/**
	 * gives an orderset of all payed orders that are still in the possession of the ober
	 * the ober didn't bring the money to the cashdesk (DB)
	 * @return all the orders that are payed
	 */
	public OrderSet getPayedOrders() {
		return payedOrders;
	}

	private void setPayedOrders(OrderSet payedOrders) {
		this.payedOrders = payedOrders;
	}

	/**
	 * make a payment of a table and return the bill
	 * removes all orders from the table and bring the orders to this ober.
	 * @param table Table object who want to pay their orders
	 * @return the bill of the payment
	 */
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Ober: " + lastName + " " + firstName;
	}

	/**
	 * @return the id of the ober
	 */
	public int getId() {
		return id;
	}
	private void setId(int id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/**
	 * @return the lastname of the ober
	 */
	public String getLastName() {
		return lastName;
	}
	private void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the firstname of the ober
	 */
	public String getFirstName() {
		return firstName;
	}
	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	private String getPassword() {
		return password;
	}
	private void setPassword(String password) {
		this.password = password;
	}
}
