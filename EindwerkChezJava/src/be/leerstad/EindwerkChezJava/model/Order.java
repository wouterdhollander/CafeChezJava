/**
 * 
 */
package be.leerstad.EindwerkChezJava.model;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;

import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class Order implements Serializable{
	//private static Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private Liquid liquid;
	private int quantity;
	private LocalDate date;
	private Ober ober;

	/**
	 * the date is set to this day
	 * @param liquid the liquid object of the order
	 * @param quantity the quantity of the order
	 * @param ober the ober who make the order
	 * @throws QuantityToLowException when the quantity is to low
	 * @throws QuantityZeroException whe the quantity is zero
	 */
	public Order(Liquid liquid, int quantity, Ober ober) throws QuantityToLowException, QuantityZeroException {
		this.setLiquid(liquid);
		this.setQuantity(quantity);
		LocalDate now = LocalDate.now();
		this.date = now;
		this.setOber(ober);
		//logger.info(this.toString() + " created" );
	}
	
	/**
	 * @param liquid the liquid object of the order
	 * @param quantity the quantity of the order
	 * @param ober the ober who make the order
	 * @param date the day the order was made
	 * @throws QuantityToLowException when the quantity is to low
	 * @throws QuantityZeroException whe the quantity is zero
	 */
	public Order(Liquid liquid, int quantity, Ober ober, LocalDate date) throws QuantityToLowException, QuantityZeroException {
		this.setLiquid(liquid);
		this.setQuantity(quantity);
		this.date = date;
		this.setOber(ober);
		//logger.info(this.toString() + " created" );
	}
	
	/**
	 * @return the ober who make the order
	 */
	public Ober getOber() {
		return ober;
	}


	/**
	 * @param ober the ober who make the order
	 */
	public void setOber(Ober ober) {
		this.ober = ober;
	}
	
	/**
	 * @return the total price of the orders
	 */
	public double getPrice(){
		return liquid.getPrice() * this.getQuantity();		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		DecimalFormat f = new DecimalFormat("##.00");
		f.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		return this.getQuantity() + " x " + this.getLiquid() + " = " + f.format(this.getPrice()) + "€";
	}

	/**
	 * @return the liquid of the order
	 */
	public Liquid getLiquid() {
		return liquid;
	}
	/**
	 * @param liquid the liquid of the order 
	 */
	public void setLiquid(Liquid liquid) {
		this.liquid = liquid;
	}
	/**
	 * @return the quantity of the order
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity quantity order
	 * @throws QuantityToLowException when the quantity is to low
	 * @throws QuantityZeroException when the quantity is zero
	 */
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
	
	/**
	 * @return the date the order is made
	 */
	public LocalDate getDate() {
		return date;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 31 * liquid.hashCode() + date.hashCode() + ober.hashCode();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Order) {
			Order o = (Order) obj;
			return (liquid.equals(o.liquid) && date.equals(o.date) && ober.equals(o.ober));
			//return (liquid.equals(o.liquid));
		}
		return false;

	}

	/**
	 * @return a printout of the order
	 */
	public StringProperty printout() {
		return new SimpleStringProperty( this.toString());
	}


}
