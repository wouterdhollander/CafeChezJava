package be.leerstad.EindwerkChezJava.Exceptions;

/**
 * @author wouter
 */
public class QuantityToLowException extends Exception{

	private static final long serialVersionUID = -4720915609777915211L;
	public QuantityToLowException(){
		this("Quantity too low!");
	}
	public QuantityToLowException(String arg0) {
		super(arg0);
		
	}
}
