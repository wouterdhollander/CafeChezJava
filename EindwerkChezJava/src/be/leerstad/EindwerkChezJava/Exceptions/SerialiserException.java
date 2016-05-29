package be.leerstad.EindwerkChezJava.Exceptions;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class SerialiserException extends InternalException {
    private static final long serialVersionUID = 19192L;

	/**
	 * Constructor with basic message
	 */
	public SerialiserException(){
		super("Error With Serialiser");
	}
    
	/**
	 * @param arg0 errormessage
	 */
	public SerialiserException(String arg0) {
		super(arg0);
	}
}
