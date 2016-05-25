package be.leerstad.EindwerkChezJava.model;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import be.leerstad.EindwerkChezJava.Exceptions.ActiveOberNotSetException;

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

	public OrderSet getPayedOrders() {
		return payedOrders;
	}

	public void setPayedOrders(OrderSet payedOrders) {
		this.payedOrders = payedOrders;
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
