package be.leerstad.EindwerkChezJava.model;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import be.leerstad.EindwerkChezJava.Exceptions.*;

/**
 * @author wouter
 * @version 0.1
 * @since 30/05/2016
 */
public class Table implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private int id;
	private OrderSet orders = new OrderSet();

	/**
	 * @param id the id of the table
	 */
	public Table(int id) {
		this.setId(id);
	}

	/**
	 * Return an OrdersSet object containing all the orders of the table
	 * @return the Orders of the Table Object
	 */
	public OrderSet getOrders() {
		return orders;
	}
	
	private void setOrders(OrderSet orders) {
		this.orders = orders;
	}
	
	/**
	 * Return an Ober object representing the Active Ober
	 * if No ober is active a default Ober is givin.
	 * @return the active Ober of the Table Object
	 */
	public Ober getActiveOber()
	{
		if (orders.size() != 0)
		{
			return orders.iterator().next().getOber();
		}
		else
		{
			return new Ober();
		}
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Table [id=" + id + ", orders=" + orders + ", activeOber=" + getActiveOber() + "]";
	}


	/**
	 * @return id passed to the constructor
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
		Table other = (Table) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
