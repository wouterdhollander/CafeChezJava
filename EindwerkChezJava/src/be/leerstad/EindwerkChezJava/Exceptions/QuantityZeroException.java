package be.leerstad.EindwerkChezJava.Exceptions;

/**
 * @author wouter
 *Exception for internal error
 */
public class QuantityZeroException extends Exception{

	private static final long serialVersionUID = -4720915609777915211L;
	public QuantityZeroException(){
		this("Quantity is zero!");
	}
	public QuantityZeroException(String arg0) {
		super(arg0);
	}
}