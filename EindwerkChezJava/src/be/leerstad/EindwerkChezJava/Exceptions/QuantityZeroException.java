package be.leerstad.EindwerkChezJava.Exceptions;

/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class QuantityZeroException extends Exception{

	private static final long serialVersionUID = -4720915609777915211L;
	/**
	 * Constructor with basic message
	 */
	public QuantityZeroException(){
		this("Quantity is zero!");
	}
	/**
	 * @param arg0 errormessage
	 */
	public QuantityZeroException(String arg0) {
		super(arg0);
	}
}