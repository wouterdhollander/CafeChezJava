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

//import be.leerstad.Exceptions.TableWithOber;


public class Table implements Serializable{
	private static Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private int id;
	private OrderSet orders = new OrderSet();

	private Ober activeOber;
	
	/**
	 * @param id
	 */
	public Table(int id) {
		this.setId(id);
		//activeOber = new o
	}
	
	public Ober getActiveOber() {
		return activeOber;
	}


	public void setActiveOber(Ober activeOber) {
		this.activeOber = activeOber;
	}


	public OrderSet getOrders() {
		return orders;
	}
	
	public void setOrders(OrderSet orders) {
		this.orders = orders;
	}
	
	
	public String printOutPayment()
	{
		StringBuilder b = new StringBuilder();
		b.append("tableID: ").append(this.getId()).append("\n Besteld door ").append(this.getActiveOber()).append("\n");

		Set<Order> orders = this.getOrders();
		orders.stream()
		.forEach(e -> b.append(e).append("\n"));
		b.append("totaal (€) = " + this.orders.calcutateOrders());
		logger.info(b);
		
		return b.toString();
	}
	
	public void payOrders()
	{
		activeOber.getPayedOrders().addAll(orders);
		this.orders.clear();
		this.setActiveOber(null);
	}
	
	@Override
	public String toString() {
		return "Table [id=" + id + ", orders=" + orders + ", activeOber=" + activeOber + "]";
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
		Table other = (Table) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
