package be.leerstad.EindwerkChezJava.Exceptions;

/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class QuantityToLowException extends Exception{

	private static final long serialVersionUID = -4720915609777915211L;
	/**
	 * Constructor with basic message
	 */
	public QuantityToLowException(){
		this("Quantity too low!");
	}
	/**
	 * @param arg0 errormessage
	 */
	public QuantityToLowException(String arg0) {
		super(arg0);
		
	}
}
