package be.leerstad.EindwerkChezJava.model;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
public class Liquid implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private int id;
	private String liquidName;
	private Double Price;
	/**
	 * @param id
	 * @param liquidName
	 * @param price
	 */
	public Liquid(int id, String liquidName, Double price) {
		this.setId(id);
		this.setLiquidName(liquidName);
		this.setPrice(price); 
		logger.info(this.toString() + " created" );
	}

	public int getId() {
		return id;
	}
	private void setId(int id) {
		this.id = id;
	}
	public String getLiquidName() {
		return liquidName;
	}
	private void setLiquidName(String liquidName) {
		this.liquidName = liquidName;
	}
	public Double getPrice() {
		return Price;
	}
	private void setPrice(Double price) {
		Price = price;
	}
	
	@Override
	public String toString() {
		DecimalFormat f = new DecimalFormat("##.00");
		f.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		return  liquidName + "(" + f.format(Price) + "€)";
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
		Liquid other = (Liquid) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
