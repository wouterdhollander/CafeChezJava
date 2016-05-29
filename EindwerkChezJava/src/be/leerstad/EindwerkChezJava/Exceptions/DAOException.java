package be.leerstad.EindwerkChezJava.Exceptions;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class DAOException extends InternalException {
    private static final long serialVersionUID = 19192L;

    /**
     * Constructor with standard error
     */
    public DAOException() {
    	super("Error With Database");
    }
    
	/**
	 * @param arg0 errormessage
	 */
	public DAOException(String arg0) {
		super(arg0);
	}
}