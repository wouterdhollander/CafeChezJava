package be.leerstad.EindwerkChezJava.model;

import java.io.Serializable;

/**
 * I make this as an extra ;-) so tables can move!
 * @author wouter everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @version 0.1
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */

public class Position implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double X;
	private double Y;
	
	/**
	 * @param x the X value of the table in relative coordinates
	 * @param y the Y value of the table in relative coordinates
	 */
	public Position(double x, double y) {
		super();
		X = x;
		Y = y;
	}
	/**
	 * @return the x value;
	 */
	public double getX() {
		return X;
	}
	/**
	 * @param x the x value
	 */
	public void setX(double x) {
		X = x;
	}
	/**
	 * @return the y value
	 */
	public double getY() {
		return Y;
	}
	/**
	 * @param y the y value
	 */
	public void setY(double y) {
		Y = y;
	}	
}
