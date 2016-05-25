/**
 * 
 */
package be.leerstad.EindwerkChezJava.model;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.sql.Date;
import java.time.LocalDate;

import org.apache.log4j.Logger;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//import java.sql.Date;

/**
 * @author wouter
 *
 */
public class Order implements Serializable{
	private static Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private Liquid liquid;
	private int quantity;
	private LocalDate date;
	private Ober ober;
	
	private StringProperty printout;

	/**
	 * @param idOrder
	 * @param idLiquid
	 * @param quality
	 * @throws QuantityZeroException 
	 * @throws QuantityToLowException 
	 */
	public Order(Liquid liquid, int quantity, Ober ober) throws QuantityToLowException, QuantityZeroException {
		this.setIdLiquid(liquid);
		this.setQuantity(quantity);
		LocalDate now = LocalDate.now();
		this.date = now;
		this.setOber(ober);
		logger.info(this.toString() + " created" );
	}
	
	public Order(Liquid liquid, int quantity, Ober ober, LocalDate date) throws QuantityToLowException, QuantityZeroException {
		this.setIdLiquid(liquid);
		this.setQuantity(quantity);
		LocalDate now = LocalDate.now();
		this.date = date;
		this.setOber(ober);
		logger.info(this.toString() + " created" );
	}
	
	public Ober getOber() {
		return ober;
	}


	private void setOber(Ober ober) {
		this.ober = ober;
	}
	
	public double getPrice(){
		return liquid.getPrice() * this.getQuantity();		
	}

	@Override
	public String toString() {
		return this.getQuantity() + " x " + this.getLiquid() + " = " + this.getPrice() + "€";
	}

	public Liquid getLiquid() {
		return liquid;
	}
	private void setIdLiquid(Liquid liquid) {
		this.liquid = liquid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) throws QuantityToLowException, QuantityZeroException {
		
		if (quantity < 0)
		{
			throw new QuantityToLowException();
		}
		else if (quantity == 0 )
		{
			throw new QuantityZeroException();
		}
		this.quantity = quantity;
	}
	
	public LocalDate getDate() {
		return date;
	}



	@Override
	public int hashCode() {
		return 31 * liquid.hashCode() + date.hashCode() + ober.hashCode();
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Order) {
			Order o = (Order) obj;
			return (liquid.equals(o.liquid) && date.equals(o.date) && ober.equals(o.ober));
		}
		return false;

	}

	public StringProperty getPrintout() {
		return new SimpleStringProperty( this.toString());
	}

	private void setPrintout(StringProperty printout) {
		this.printout = printout;
	}

}
