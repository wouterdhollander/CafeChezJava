package be.leerstad.EindwerkChezJava.model;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
/**
 * @author wouter
 * @version 0.1
 * @since 30/05/2016
 */
public class Liquid implements Serializable{
	private static final long serialVersionUID = 1L;
	//private static Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private int id;
	private String liquidName;
	Double Price;
	/**
	 * @param id the id of the liquid
	 * @param liquidName the name of the liquid
	 * @param price the price of the liquid
	 */
	public Liquid(int id, String liquidName, Double price) {
		this.setId(id);
		this.setLiquidName(liquidName);
		this.setPrice(price); 
		//logger.info(this.toString() + " created" );
	}

	/**
	 * Returns an integer primitive that represents the id of Liquid object
	 * @return the id of Liquid object
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id of the Liquid object
	 */
	private void setId(int id) {
		this.id = id;
	}
	/**
	 * Returns an string that represents the name of the Liquid object
	 * @return the name of the Liquid object
	 */
	public String getLiquidName() {
		return liquidName;
	}
	/**
	 * @param liquidName the Name of the Liquid object
	 */
	private void setLiquidName(String liquidName) {
		this.liquidName = liquidName;
	}
	/**
	 * Returns a double primitive that represents the price of the Liquid object
	 * @return the price of the Liquid object
	 */
	public Double getPrice() {
		return Price;
	}
	/**
	 * @param price the price of the Liquid object
	 */
	private void setPrice(Double price) {
		Price = price;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		DecimalFormat f = new DecimalFormat("##.00");
		f.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		return  liquidName + "(" + f.format(Price) + "€)";
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
		Liquid other = (Liquid) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
